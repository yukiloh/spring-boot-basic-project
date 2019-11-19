package boot.service.impl;

import boot.service.ITestService;
import org.springframework.stereotype.Component;

@Component
public class TestService {

    public String TestDoSomething(String msg) {
        System.out.println(msg);
        return msg;
    }
}
