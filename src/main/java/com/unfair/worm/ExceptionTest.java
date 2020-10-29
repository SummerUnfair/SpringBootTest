package com.unfair.worm;/*
 * @author Ferao
 * @date
 * @discription
 */

public class ExceptionTest<T> {

    T name;

    public static void main (String[] args){

            int num1 = 0;
            if(num1<1){
//                throw new NewException("自定义");
                throw new RuntimeException(""); //非受检异常
            }
    }
}
class NewException extends Exception{

    NewException(String str){
        //此处传入的是抛出异常后显示的信息提示
        super(str);
    }
}