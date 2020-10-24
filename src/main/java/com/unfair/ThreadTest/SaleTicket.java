package com.unfair.ThreadTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicket implements Runnable {

private int tickets=1000;

Lock L = new ReentrantLock();


    //线程任务
    @Override
    public void run() {

        while(true){

            //在可能出现安全问题的代码前调用Lock接口中的方法Lock获取锁
            L.lock();

            if (tickets>0){

                //使线程问题出现几率提高
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"卖出"+tickets+"张");
                tickets--;

            }else{
                L.unlock();
                break;
            }
            //在可能出现安全问题的代码后调用Lock接口中的方法unlock释放锁
            L.unlock();

        }

    }

}
