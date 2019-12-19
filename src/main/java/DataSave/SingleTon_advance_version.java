package DataSave;

/**
 * Output source：控制台
 * function:单例设计模式
 * Created by FH on 2019/10/9.
 */
public class SingleTon_advance_version {

    //私有构造函数
    private SingleTon_advance_version() {

    }

    //单例对象
    private static SingleTon_advance_version instance = null;

    //静态工厂方法
    public static SingleTon_advance_version getInstance() {

        //双重检测机制
        if (instance == null) {
            //同步锁
            synchronized (SingleTon_advance_version.class){
                //双重检测机制
                if (instance == null) {
                    instance = new SingleTon_advance_version();
                }
            }
        }
        return instance;
    }

}

/**
 *
 * 为了防止new Singleton被执行多次，因此在new操作之前加上Synchronized 同步锁，锁住整个类（注意，这里不能使用对象锁）。
 *
 * 进入Synchronized 临界区以后，还要再做一次判空。因为当两个线程同时访问的时候，线程A构建完对象，线程B也已经通过了最初的判空验证，不做第二次判空的话，线程B还是会再次构建instance对象
 *
 * 像这样两次判空的机制叫做双重检测机制
 */

