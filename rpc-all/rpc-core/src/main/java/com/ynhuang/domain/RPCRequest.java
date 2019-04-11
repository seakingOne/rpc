package com.ynhuang.domain;

import lombok.Data;

/**
 * Created by ynhuang on 2017/7/30.
 */
@Data
public class RPCRequest  {
    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
