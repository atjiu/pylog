package co.yiiu.pydeploy.config;

import co.yiiu.pydeploy.handler.ChatHandler;
import co.yiiu.pydeploy.util.Message;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value = "/websocket", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
@Component
public class MyWebSocket {
  //在线人数
  private static int online = 0;
  //所有的对象，用于群发
  public static List<MyWebSocket> webSockets = new CopyOnWriteArrayList<>();
  //会话
  private Session session;

  public Session getSession() {
    return session;
  }

  //建立连接
  @OnOpen
  public void onOpen(Session session) {
    online++;
    webSockets.add(this);
    this.session = session;
    String msg = "有新用户连接！当前在线人数: " + online;
    webSockets.forEach(self -> {
      try {
        self.session.getBasicRemote().sendObject(new Message("text", msg));
      } catch (EncodeException | IOException e) {
        e.printStackTrace();
      }
    });
  }

  //连接关闭
  @OnClose
  public void onClose() {
    online--;
    String msg = "有用户离开，当前在线人数: " + online;
    webSockets.remove(this);
    webSockets.forEach(self -> {
      try {
        self.session.getBasicRemote().sendObject(new Message("text", msg));
      } catch (EncodeException | IOException e) {
        e.printStackTrace();
      }
    });
  }

  //收到客户端的消息
  @OnMessage
  public void onMessage(Message text, Session session) {
    System.out.println("client message: " + text.toString());
    if (text.getType().equals("chat")) new ChatHandler().sendMessage(text);
  }
}
