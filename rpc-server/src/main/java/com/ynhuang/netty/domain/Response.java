package com.ynhuang.netty.domain;

import com.ynhuang.netty.bean.UserInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 018399
 * @Date: 2018/12/3 14:47
 * @Description:
 */
@Data
public class Response implements Serializable {

    private Long requestId;
    private Throwable error;
    private UserInfo result;

}
