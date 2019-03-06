package com.ynhuang.netty.annotation;

import java.lang.annotation.*;

/**
 * @Auther: 018399
 * @Date: 2018/11/29 16:20
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRpcClient {

}
