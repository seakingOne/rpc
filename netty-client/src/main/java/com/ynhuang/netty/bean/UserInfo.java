package com.ynhuang.netty.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 16:15
 * @Description:
 */
@Data
public class UserInfo implements Serializable {

    private int userId;

    private String userName;
}
