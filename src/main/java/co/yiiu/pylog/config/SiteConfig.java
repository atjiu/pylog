package co.yiiu.pylog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by tomoya at 2019/5/28
 */
@Configuration
@ConfigurationProperties
@Data
public class SiteConfig {

  private String host;
  private String name;
  private Map<String, String> logs;
}
