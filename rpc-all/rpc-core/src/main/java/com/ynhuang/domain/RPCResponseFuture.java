package com.ynhuang.domain;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ynhuang
 *
 * @date 2019-4-12
 *
 * @desc 同步返回结果
 */
public class RPCResponseFuture {

    private RPCResponse response;

    //lock锁
    private final ReentrantLock reentrantLock = new ReentrantLock();
    //条件变量
    private final Condition condition = reentrantLock.newCondition();

    /**
     * 判断结果是否为空
     * @return
     */
    public boolean isDone(){
        return response != null;
    }

    /**
     * 获取结果
     * @return
     */
    public RPCResponse getResponse() {

        reentrantLock.lock();

        try {

            while (!isDone()) {
                condition.await();
                if(isDone()){
                    //获取到结果 直接终止循环
                    break;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }

        return response;
    }

    public void setResponse(RPCResponse result) {

        reentrantLock.lock();

        try {
            response = result;
            condition.signalAll();
        }finally {
            reentrantLock.unlock();
        }

    }
}
