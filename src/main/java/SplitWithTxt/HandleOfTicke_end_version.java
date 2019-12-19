package SplitWithTxt;

public class HandleOfTicke_end_version  implements Runnable{

    private int ticket = 10;

    @Override
    public void run() {
        for(int i =0;i<10;i++){
            //添加同步块
            synchronized (this){
                if(this.ticket>0){
                    try {
                        //通过睡眠线程来模拟出最后一张票的抢票场景
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName()+"卖票---->"+(this.ticket--));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] arg){
        HandleOfTicke_end_version t1 = new HandleOfTicke_end_version();
        new Thread(t1, "线程1").start();
        new Thread(t1, "线程2").start();
    }



}
