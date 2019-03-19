package com.ynhuang.netty.interfaces;

import com.ynhuang.netty.annotation.MyRpcClient;
import com.ynhuang.netty.bean.UserInfo;

/**
 * @Auther: 018399
 * @Date: 2018/11/29 15:58
 * @Description:
 */
@MyRpcClient
public interface QueryUserById {

    public UserInfo queryUserById(int id);
}
