package co.yiiu.pylog.handler;

import co.yiiu.pylog.config.SiteConfig;
import co.yiiu.pylog.config.SpringContextUtil;
import co.yiiu.pylog.util.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by tomoya at 2019/5/28
 */
@Slf4j
public class LogHandler {

  private Session session;
  private Message message;

  public LogHandler(Session session, Message message) {
    this.session = session;
    this.message = message;
  }

  public void sendMessage() {
    SiteConfig siteConfig = SpringContextUtil.getBean(SiteConfig.class);
    Map<String, String> logs = siteConfig.getLogs();
    if (logs != null) {
      String logFilePath = logs.get(message.getType());
      try {
        String command = "tail -200f " + logFilePath;
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName
            ("UTF-8")));
        // 这里编码如果是mac或者linux可以使用utf-8
        String line;
        while ((line = br.readLine()) != null) {
          try {
            session.getBasicRemote().sendObject(new Message(message.getType(), line));
          } catch (Exception e) {
            log.error("channel close error: {}", e.getMessage());
            br.close();
            break;
          }
        }
      } catch (Exception e) {
        log.error("sendMessage error: {}", e.getMessage());
      }
    }
  }
}
