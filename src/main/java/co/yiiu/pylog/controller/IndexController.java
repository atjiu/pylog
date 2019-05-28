package co.yiiu.pylog.controller;

import co.yiiu.pylog.config.SiteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomoya at 2019/5/28
 */
@Controller
public class IndexController {

  @Autowired
  private SiteConfig siteConfig;
  @Autowired
  private Environment env;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("port", env.getProperty("server.port"));
    model.addAttribute("name", siteConfig.getName());
    if (siteConfig.getLogs() != null) {
      List<String> names = new ArrayList<>();
      List<String> logFilePaths = new ArrayList<>();
      siteConfig.getLogs().forEach((key, value) -> {
        names.add(key);
        logFilePaths.add(value);
      });
      model.addAttribute("names", names);
      model.addAttribute("logFilePaths", logFilePaths);
    }
    return "index";
  }
}
