package com.ynhuang.netty.controller;

import com.ynhuang.netty.NettyClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 14:02
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping(value = "/send")
    public String sendData(){
        NettyClient.getServerInstance().sendDtaToServer();
        return "";
    }

}
