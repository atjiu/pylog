package co.yiiu.pydeploy.config;

import co.yiiu.pydeploy.handler.PingHandler;
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
    public static List<MyWebSocket> webSockets = new CopyOnWriteArrayList<MyWebSocket>();

    /**
     * 会话
     */
    private Session session;

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

        System.out.println("有新连接加入！ 当前在线人数" + onlineNumber);
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        webSockets.remove(this);
        System.out.println("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param text    消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String text, Session session) {
        Message message = JSON.parseObject(text, Message.class);
        log.info("来自客户端消息：" + message.toString());
        switch (message.getType()) {
            case "ping":
                new PingHandler(session).sendMessage(message);
        }
    }
}
