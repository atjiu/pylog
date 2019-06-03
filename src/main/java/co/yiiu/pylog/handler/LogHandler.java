package co.yiiu.pylog.handler;

import co.yiiu.pylog.config.SiteConfig;
import co.yiiu.pylog.config.SpringContextUtil;
import co.yiiu.pylog.util.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tomoya at 2019/5/28
 */
@Slf4j
public class LogHandler implements Runnable {

  private Session session;
  private Message message;
  private CountDownLatch countDownLatch;

  public LogHandler(Session session, Message message, CountDownLatch countDownLatch) {
    this.session = session;
    this.message = message;
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    SiteConfig siteConfig = SpringContextUtil.getBean(SiteConfig.class);
    Map<String, String> logs = siteConfig.getLogs();
    if (logs != null) {
      String logFilePath = logs.get(message.getType());
      Process process = null;
      BufferedReader br = null;
      try {
        String command = "tail -200f " + logFilePath;
        process = Runtime.getRuntime().exec(command);
        br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));
        // 这里编码如果是mac或者linux可以使用utf-8
        String line;
        while ((line = br.readLine()) != null) {
          try {
            session.getBasicRemote().sendObject(new Message(message.getType(), line));
          } catch (Exception e) {
            log.error("type: {}, thread: {}, channel close error: {}", message.getType(), this.getClass().getName(),
                e.getMessage());
            break;
          }
        }
      } catch (Exception e) {
        log.error("sendMessage error: {}", e.getMessage());
      } finally {
        try {
          if (br != null) br.close();
          if (process != null) {
            process.getOutputStream().close();
            process.getInputStream().close();
            process.getErrorStream().close();
            process.waitFor();
            Thread.sleep(1000);
            process.destroy();
          }
          if (countDownLatch != null) countDownLatch.countDown();
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
