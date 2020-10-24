package com.unfair.SplitWithTxt;

public class HandleOfTicke_initial_version {

        public static void main(String[] args) {
        Ticket ticket=new Ticket();

        //创建四个线程对象
        Thread t1=new Thread(ticket,"窗口1");
        Thread t2=new Thread(ticket,"窗口2");
        Thread t3=new Thread(ticket,"窗口3");
        Thread t4=new Thread(ticket,"窗口4");

        //启动四个线程
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        /*
         * 四个线程访问的是同一个tikets变量，共享100张票，由此可知，实现Runnable接口相对于继承Thread类
         * 来说，有如下的优势
         *
         * 适合多个程序代码相同的线程处理同一个资源的情况
         * 可以避免由于java单继承特性带来的局限。
         *
         */
    }

    static  class Ticket implements Runnable{

        private int tickets=20000;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(true) {
                synchronized (this) {
                    if(tickets>0) {
                        //获取当前线程额名称
                        String name=Thread.currentThread().getName();
                        System.out.println(name+"正在发售第"+tickets--+"张票");

                    }else {
                        break;
                    }
                }
            }
        }

    }

}
