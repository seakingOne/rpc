package com.ynhuang.springserver.impl;

import com.ynhuang.annotation.RPCService;
import com.ynhuang.springapi.domain.User;
import com.ynhuang.springapi.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by ynhuang on 2017/7/30.
 */
@RPCService
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(User user) {
        return "Hello, " + user.getUsername();
    }
}
