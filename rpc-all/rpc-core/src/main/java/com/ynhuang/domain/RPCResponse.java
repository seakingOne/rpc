package com.ynhuang.domain;

import lombok.Data;

/**
 * Created by ynhuang on 2019-4-12.
 */
@Data
public class RPCResponse {

    private String requestId;

    private Throwable cause;

    private Object result;

    public boolean hasError() {
        return cause != null;
    }
}
