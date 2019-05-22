//package co.yiiu.pydeploy.handler;
//
//import co.yiiu.pydeploy.util.Message;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class SocketEventHandler {
//
//    private Map<String, Object> socketMap = new HashMap<>();
//    private Logger logger = LoggerFactory.getLogger(SocketEventHandler.class);
//
//    @Autowired
//    private SocketIOServer server;
//
//    @OnConnect
//    public void onConnect(SocketIOClient client) {
//        String username = client.getHandshakeData().getSingleUrlParam("username");
//        logger.info("用户{}上线了, sessionId: {}", username, client.getSessionId().toString());
//        socketMap.put(username, client);
//        // notification count
//        long count = 10; // 这一步可以通过调用service来查数据库拿到真实数据
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("count", count);
//        client.sendEvent("notification", map);
//    }
//
//    @OnDisconnect
//    public void onDisConnect(SocketIOClient client) {
//        String[] username = new String[1];
//        socketMap.forEach((key, value) -> {
//            if (value == client) username[0] = key;
//        });
//        logger.info("用户{}离开了", username[0]);
//        socketMap.remove(username[0]);
//    }
//
//    @OnEvent("test")
//    public void test(SocketIOClient client, AckRequest ackRequest, Message message) throws IOException {
//        String name = (String) message.getPayload().get("name");
//        System.out.println("传递的参数: " + name);
//        String command = "ping baidu.com -t";
//        Process process = Runtime.getRuntime().exec(command);
//        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
//        String line;
//        while ((line = br.readLine()) != null) {
//            System.out.println(client.getSessionId() + " " + line);
//            // TODO 没法停下, TBD
//            client.sendEvent("log", line);
//        }
//    }
//
//}
