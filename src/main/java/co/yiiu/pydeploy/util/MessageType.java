package co.yiiu.pydeploy.util;

/**
 * Created by tomoya at 2019/5/22
 */
public enum MessageType {

    PING("ping"),
    PONG("pong");

    private String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
