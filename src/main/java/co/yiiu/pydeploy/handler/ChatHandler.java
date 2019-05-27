package co.yiiu.pydeploy.handler;

import co.yiiu.pydeploy.config.MyWebSocket;
import co.yiiu.pydeploy.util.Message;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by tomoya at 2019/5/27
 */
public class ChatHandler {

    public void sendMessage(Message message) {
        MyWebSocket.webSockets.forEach(myWebSocket -> {
            try {
                String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
                String id = ((Map) message.getPayload()).get("id").toString();
                String text = ((Map) message.getPayload()).get("text").toString();
                myWebSocket.getSession().getBasicRemote().sendObject(new Message("chat", id + " " + now + "<br>" + text));
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }
}
