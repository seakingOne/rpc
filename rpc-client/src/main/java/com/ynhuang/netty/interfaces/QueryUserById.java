package com.ynhuang.netty.interfaces;

import com.ynhuang.netty.bean.UserInfo;

/**
 * @Auther: 018399
 * @Date: 2018/11/29 15:58
 * @Description:
 */
public interface QueryUserById {

    UserInfo queryUserById(int id);
}
