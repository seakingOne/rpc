package com.ynhuang.netty.interfaces.impl;

import com.ynhuang.netty.annotation.MyRpcService;
import com.ynhuang.netty.bean.UserInfo;
import com.ynhuang.netty.interfaces.QueryUserById;
import org.springframework.stereotype.Service;

/**
 * @Auther: 018399
 * @Date: 2018/11/29 16:00
 * @Description:
 */
@Service
@MyRpcService
public class QueryUserByIdImpl implements QueryUserById {

    @Override
    public UserInfo queryUserById(int id) {
        UserInfo user = new UserInfo();
        user.setUserId(1);
        user.setUserName("ynhuang");
        if(user.getUserId() != 1){
            user.setUserId(0);
            user.setUserName(null);
        }
        return user;
    }
}
