package com.ynhuang.netty.controller;

import com.ynhuang.netty.annotation.MyRpcClient;
import com.ynhuang.netty.bean.UserInfo;
import com.ynhuang.netty.interfaces.QueryUserById;
import com.ynhuang.netty.proxy.FactoryProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 14:02
 * @Description:
 */
@RestController
public class UserController {

//    @MyRpcClient
//    @Resource
//    private QueryUserById queryUserById;

    @GetMapping(value = "/getUser")
    public UserInfo sendData(@RequestParam int id){

        FactoryProxy factoryProxy = new FactoryProxy();

        QueryUserById queryUserById1 = (QueryUserById) factoryProxy.getProxy(QueryUserById.class);
        UserInfo userInfo = queryUserById1.queryUserById(id);

        return userInfo;
    }

}
