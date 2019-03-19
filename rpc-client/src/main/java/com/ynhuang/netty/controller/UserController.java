package com.ynhuang.netty.controller;

import com.ynhuang.netty.bean.UserInfo;
import com.ynhuang.netty.interfaces.QueryUserById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 14:02
 * @Description:
 */
@RestController
public class UserController {

    @Autowired(required = false)
    private QueryUserById queryUserById;

    @GetMapping(value = "/getUser")
    public UserInfo sendData(@RequestParam int id){

        UserInfo userInfo = queryUserById.queryUserById(id);

        return userInfo;
    }

}
