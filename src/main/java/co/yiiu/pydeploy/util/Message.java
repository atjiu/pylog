package co.yiiu.pydeploy.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = 7600555288728686383L;

    private String type;
    private Object payload;

}
