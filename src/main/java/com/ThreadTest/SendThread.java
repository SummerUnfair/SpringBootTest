package com.ThreadTest;
/*
*
* 进入到Timewaiting(计时等待)有两种方式
*   1.使用sleep(long m )方法，在毫秒值结束之后，线程唤醒进入到Runnable/Blocked状态
*   2.使用wait(long m ) 方法，wait()方法如果在毫秒值结束之后，还没有被notify()唤醒，就会自动醒来，线程睡醒进入到Runnable/Blocked状态
*
*   唤醒的方法：
*       void notify() 唤醒在此对象监视器上等待的单个线程
*       void notifyAll() 唤醒在此对象监视器上等待的所有线程
* */
public class SendThread {

    public static void main (String[] args)
    {
        //创建锁对象，保证唯一
        Object obj = new Object();
        //创建一个顾客线程(消费者)
        new Thread() {
            @Override
            public void run(){

                while(true){

                    //保证等待和唤醒的线程只能一个执行，需要使用同步技术
                    synchronized (obj){

                        System.out.println("顾客：告知老板要的包子的种类和数量");
                        //调用wait()方法，放弃cpu的执行权，进入到WAITTIME状态(无线等待)
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //唤醒之后执行的代码
                        System.out.println("顾客：包子做的好——）开吃");
                        System.out.println("---------------------");
                    }
                }
            }
        }.start();

        //创建一个老板线程(消费者)
        new Thread() {
            @Override
            public void run(){
                //保证等待和唤醒的线程只能一个执行，需要使用同步技术

               while(true){
                   try {
                       //花五秒做包子
                       Thread.sleep(2000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   synchronized (obj){

                       System.out.println("老板：包子做好，可以吃了");
                       //调用wait()方法，放弃cpu的执行权，进入到WAITTIME状态(无线等待)
                       obj.notify();
                   }

               }
            }
        }.start();

    }
}
