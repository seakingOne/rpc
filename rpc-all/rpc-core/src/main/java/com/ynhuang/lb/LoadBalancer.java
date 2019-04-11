package com.ynhuang.lb;

import java.util.List;

/**
 * @author synhuang
 * @date 2018/3/11
 */
public interface LoadBalancer {

    String get(String clientAddress);

    void update(List<String> addresses);
}
