package com.ynhuang.lb.impl;

import com.ynhuang.lb.LoadBalancer;

import java.util.List;

/**
 * @author synhuang
 * @date 2018/3/15
 */
public class LeastActiveLoadBalancer implements LoadBalancer {
    @Override
    public String get(String clientAddress) {
        return null;
    }

    @Override
    public void update(List<String> addresses) {

    }
}
