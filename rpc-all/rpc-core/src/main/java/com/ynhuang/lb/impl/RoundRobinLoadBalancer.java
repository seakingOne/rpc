package com.ynhuang.lb.impl;

import com.ynhuang.lb.LoadBalancer;

import java.util.List;

/**
 * @author synhuang
 * @date 2018/3/11
 */
public class RoundRobinLoadBalancer implements LoadBalancer {
    private List<String> addresses;
    private int index = 0;

    @Override
    public String get(String clientAddress) {
        if(addresses.size() == 0) {
            return null;
        }
        String result = addresses.get(index);
        index = (index + 1) % addresses.size();
        return result;
    }

    @Override
    public void update(List<String> addresses) {
        this.addresses = addresses;
        this.index = this.index % addresses.size();
    }
}
