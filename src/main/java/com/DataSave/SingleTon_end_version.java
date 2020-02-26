package com.DataSave;

/**
 * Output source：控制台
 * function:单例设计模式
 * Created by FH on 2019/10/9.
 */
public class SingleTon_end_version {

    private SingleTon_end_version() {}  //私有构造函数
    private volatile static SingleTon_end_version instance = null;  //单例对象
    //静态工厂方法
    public static SingleTon_end_version getInstance() {
        if (instance == null) {      //双重检测机制
            synchronized (SingleTon_end_version.class){  //同步锁
                if (instance == null) {     //双重检测机制
                    instance = new SingleTon_end_version();
                }
            }
        }
        return instance;
    }
}

/*
这里涉及到JVM的指令重排

比如java中简单的一句 instance = new Singleton，会被编译器编译成如下JVM指令：


memory =allocate(); //1：分配对象的内存空间

ctorInstance(memory); //2：初始化对象

instance =memory; //3：设置instance指向刚分配的内存地址

但是这些指令顺序并非一成不变，有可能会经过JVM和CPU的优化，指令重排成下面的顺序：


memory =allocate(); //1：分配对象的内存空间

instance =memory; //3：设置instance指向刚分配的内存地址

ctorInstance(memory); //2：初始化对象

当线程A执行完1,3,时，instance对象还未完成初始化，但已经不再指向null。此时如果线程B抢占到CPU资源，执行 if（instance == null）的结果会是false，从而返回一个没有初始化完成的instance对象
 */