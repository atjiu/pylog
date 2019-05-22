package co.yiiu.pydeploy.handler;

import co.yiiu.pydeploy.util.Message;
import co.yiiu.pydeploy.util.MessageType;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by tomoya at 2019/5/22
 */
@Slf4j
public class PingHandler {

    private Session session;

    public PingHandler(Session session) {
        this.session = session;
    }

    public void sendMessage(Message message) {
        try {
            String command = "ping baidu.com";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(session == null);
                System.out.println(session.getId() + " " + line);
                message = new Message(MessageType.PONG.getMessage(), line);
                try {
                    session.getBasicRemote().sendText(JSON.toJSONString(message));
                } catch (IOException e) {
                    log.error(e.getMessage());
                    br.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
