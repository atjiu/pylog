package co.yiiu.pydeploy.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public void test(HttpServletResponse response) throws IOException {
        String command = "ping baidu.com -t";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        PrintWriter writer = response.getWriter();
        while ((line = br.readLine()) != null) {
            writer.write(line);
        }
    }
}
