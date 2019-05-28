package co.yiiu.pylog.config;

import co.yiiu.pylog.util.Message;
import com.alibaba.fastjson.JSON;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by tomoya at 2019/5/27
 */
public class MessageDecoder implements Decoder.Text<Message> {
  @Override
  public Message decode(String s) {
    return JSON.parseObject(s, Message.class);
  }

  @Override
  public boolean willDecode(String s) {
    return JSON.isValid(s);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {

  }

  @Override
  public void destroy() {

  }
}
