package co.yiiu.pylog;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PylogApplicationTests {

    @Test
    public void contextLoads() throws IOException {
        String command = "ping baidu.com -t";
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

}
