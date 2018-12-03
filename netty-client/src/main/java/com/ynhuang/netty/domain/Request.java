package com.ynhuang.netty.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 14:46
 * @Description: 远程调用入参
 */
@Data
public class Request implements Serializable {

    private String className;

    private String method;

    private Object[] params;

}
