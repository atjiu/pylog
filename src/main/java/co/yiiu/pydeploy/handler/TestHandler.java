package co.yiiu.pydeploy.handler;

import co.yiiu.pydeploy.util.Message;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by tomoya at 2019/5/22
 */
@Slf4j
public class TestHandler {

    private Session session;

    public TestHandler(Session session) {
        this.session = session;
    }

    public void sendMessage(Message message) {
        try {
            String payload = "hello session: " + session.getId();
            message.setType("text");
            message.setPayload(payload);
            this.session.getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
