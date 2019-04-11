package com.ynhuang.lb.impl;

import com.ynhuang.lb.LoadBalancer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author synhuang
 * @date 2018/3/11
 */

public class RandomLoadBalancer implements LoadBalancer {
    private List<String> addresses;

    @Override
    public String get(String clientAddress) {
        if(addresses.size() == 0) {
            return null;
        }
        return addresses.get(ThreadLocalRandom.current().nextInt(addresses.size()));
    }

    @Override
    public void update(List<String> addresses) {
        this.addresses = addresses;
    }
}
