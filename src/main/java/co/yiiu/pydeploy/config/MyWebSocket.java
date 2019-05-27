package co.yiiu.pydeploy.config;

import co.yiiu.pydeploy.util.Message;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value = "/websocket")
@Component
@Slf4j
public class MyWebSocket {
    /**
     * 在线人数
     */
    public static int onlineNumber = 0;

    /**
     * 所有的对象
     */
    public static List<MyWebSocket> webSockets = new CopyOnWriteArrayList<>();

    /**
     * 会话
     */
    private Session session;

    public Session getSession() {
        return session;
    }

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineNumber++;
        webSockets.add(this);
        this.session = session;
        String msg = "有新连接加入！ 当前在线人数" + onlineNumber;
//        webSockets.forEach(self -> {
//            try {
//                self.session.getBasicRemote().sendText(JSON.toJSONString(new Message("text", msg)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        String msg = "有连接关闭！ 当前在线人数" + onlineNumber;
        webSockets.remove(this);
//        webSockets.forEach(self -> {
//            try {
//                self.session.getBasicRemote().sendText(JSON.toJSONString(new Message("text", msg)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    /**
     * 收到客户端的消息
     *
     * @param text    消息
     * @param session 会话
     */
    @OnMessage
    public String onMessage(String text, Session session) {
        System.out.println("client message: " + text);
//        Message message = JSON.parseObject(text, Message.class);
//        log.info("来自客户端消息：" + message.toString());
//        switch (message.getType()) {
//            case "ping":
//                new PingHandler(session).sendMessage(message);
//            case "test":
//                new TestHandler(session).sendMessage(message);
//        }
        return JSON.toJSONString(new Message("text", "hello session: " + session.getId()));
    }
}
