package co.yiiu.pydeploy.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "text")
    private String command;

    private Date inTime;

    // 最后一次部署时间
    private Date lastDeployTime;

}
