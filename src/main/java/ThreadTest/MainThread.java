package ThreadTest;
/*
 *等待唤醒线程案例:线程之间的通信
 *      创建一个顾客线程(消费者)：告知老板老的包子的种类和数量，调用wait()方法，放弃cpu的执行权，进入到WAITING状态(无限等待)
 *      创建一个老板线程(生产者)：花 s 秒做包子，做好包子之后，调用notify()方法，唤醒顾客吃包子
 *
 *   注意：
 *      顾客和老板线程必须使用同步代码块包裹起来，保证等待和唤醒只能有一个在执行
 *      同步使用的锁对象必须保证唯一
 *      只有锁对象才能调用wait()和notify()方法
 *
 *   Object类中的方法
 *   void wait()
 *      在其它线程调用此对象的notify()方法或notifyAll()方法前，导致线程等待
 *   void notify()
 *      唤醒此对象监视器上等待的单个线程
 *      会继续执行wait()方法之后的代码
 * */
public class MainThread {

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

                        System.out.println("顾客1：告知老板要的包子的种类和数量");
                        //调用wait()方法，放弃cpu的执行权，进入到WAITTIME状态(无线等待)
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //唤醒之后执行的代码
                        System.out.println("顾客1：包子做的好——）开吃");
                        System.out.println("---------------------");
                    }
                }
            }
        }.start();

        //创建顾客线程（消费者）
        new Thread() {
            @Override
            public void run(){

                while(true){

                    //保证等待和唤醒的线程只能一个执行，需要使用同步技术
                    synchronized (obj){

                        System.out.println("顾客2：告知老板要的包子的种类和数量");
                        //调用wait()方法，放弃cpu的执行权，进入到WAITTIME状态(无线等待)
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //唤醒之后执行的代码
                        System.out.println("顾客2：包子做的好——）开吃");
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
                        //obj.notify();   //如果有多个等待线程，随机唤醒一个
                        obj.notifyAll();  //唤醒所有等待的线程
                    }

                }
            }
        }.start();

    }

}
