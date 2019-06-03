package co.yiiu.pylog.config;

import co.yiiu.pylog.handler.LogHandler;
import co.yiiu.pylog.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/websocket", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
@Component
@Slf4j
public class MyWebSocket {

  //在线人数
  private static int online = 0;
  //所有的对象，用于群发
  private static List<Session> webSockets = new CopyOnWriteArrayList<>();
  private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
  private static CountDownLatch countDownLatch;

  //建立连接
  @OnOpen
  public void onOpen(Session session) {
    online++;
    webSockets.add(session);
    log.info("有用户打开连接，当前有{}个用户连接", online);
    // 启动一个时间线程
    cachedThreadPool.execute(() -> {
      while (true) {
        try {
          Thread.sleep(1000);
          session.getBasicRemote().sendObject(new Message("time", LocalDateTime.now().format(DateTimeFormatter
              .ofPattern("yyyy-MM-dd HH:mm:ss"))));
        } catch (Exception e) {
          log.error(e.getMessage());
          break;
        }
      }
    });
  }

  //连接关闭
  @OnClose
  public void onClose(Session session) {
    online--;
    webSockets.remove(session);
    log.info("有用户断开连接，当前有{}个用户连接", online);
  }

  //收到客户端的消息
  @OnMessage
  public void onMessage(Session session, Message message) {
    try {
      log.info("session.id: {}, message: {}", session.getId(), message);
      SiteConfig siteConfig = SpringContextUtil.getBean(SiteConfig.class);
      Map<String, String> logs = siteConfig.getLogs();
      if (message.getType().equals("fetchLogs")) {
        session.getBasicRemote().sendObject(new Message("fetchLogs", logs));
      } else {
        if (countDownLatch == null) countDownLatch = new CountDownLatch(logs.size());
        cachedThreadPool.execute(new LogHandler(session, message, countDownLatch));
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
