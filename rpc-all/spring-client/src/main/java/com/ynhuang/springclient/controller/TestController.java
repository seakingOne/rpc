package com.ynhuang.springclient.controller;

import com.ynhuang.annotation.RPCReference;
import com.ynhuang.springapi.domain.User;
import com.ynhuang.springapi.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 018399
 * @Date: 2019/4/11 16:04
 * @Description:
 */
@RestController
public class TestController {

    //@Autowired(required = false)
    @RPCReference
    private HelloService helloService;

    @GetMapping(value = "/zk1")
    public String test() throws Exception {

        String hello = helloService.hello(new User("1"));

        return hello;
    }

}
