package co.yiiu.pydeploy.config;

import co.yiiu.pydeploy.util.Message;
import com.alibaba.fastjson.JSON;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by tomoya at 2019/5/27
 */
public class MessageEncoder implements Encoder.Text<Message> {
  @Override
  public String encode(Message o) {
    return JSON.toJSONString(o);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {

  }

  @Override
  public void destroy() {

  }
}
