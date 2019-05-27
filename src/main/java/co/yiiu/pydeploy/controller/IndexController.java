package co.yiiu.pydeploy.controller;

import co.yiiu.pydeploy.config.MyWebSocket;
import co.yiiu.pydeploy.model.Task;
import co.yiiu.pydeploy.service.TaskService;
import co.yiiu.pydeploy.util.Message;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Date;

/**
 * Created by tomoya at 2019/5/22
 */
@Controller
public class IndexController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        for (MyWebSocket webSocket : MyWebSocket.webSockets) {
            webSocket.getSession().getBasicRemote().sendText(JSON.toJSONString(new Message("text", "from IndexController::index")));
        }
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/create")
    public String create(String name, String command) {
        Task task = new Task();
        task.setName(name);
        task.setCommand(command);
        task.setInTime(new Date());
        task.setLastDeployTime(new Date());
        taskService.save(task);
        return "redirect:/";
    }
}
