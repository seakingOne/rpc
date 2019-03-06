package com.ynhuang.netty.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 14:48
 * @Description:
 */
@Data
public class UserInfo implements Serializable {

    private int userId;

    private String userName;


}
