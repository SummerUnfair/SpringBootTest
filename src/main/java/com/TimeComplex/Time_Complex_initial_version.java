package com.TimeComplex;

/**
 * Output source：控制台
 * function:推导时间复杂度
 *原理：时间复杂度就是把时间规模函数T(n)简化为一个数量级，这个数量级可以是n,n^2,n^3等等
 * 推导的原则：
 * 如果运行时间是常数量级，用常数1表示
 * 只保留时间函数中的最高阶项
 * 如果最高阶项存在，则省去最高阶项前面的系数
 * Created by FH on 2019/10/22.
 */
public class Time_Complex_initial_version {


    //场景1， T（n） = 3n，执行次数是线性的。
    //最高阶项为3n，省去系数3，转化的时间复杂度为：
    //
    //T（n） =  O（n）
    void eat1(int n){

        for(int i=0; i<n; i++){;

            System.out.println("等待一天");

            System.out.println("等待一天");

            System.out.println("吃一寸面包");

        }

    }

    //场景2， T（n） = 5logn，执行次数是对数的。
    //最高阶项为5logn，省去系数5，转化的时间复杂度为：
    //
    //T（n） =  O（logn）
    void eat2(int n){

        for(int i=1; i<n; i*=2){

            System.out.println("等待一天");

            System.out.println("等待一天");

            System.out.println("等待一天");

            System.out.println("等待一天");

            System.out.println("吃一半面包");

        }

    }

    //场景3，T（n） = 2，执行次数是常量的。
    //只有常数量级，转化的时间复杂度为：
    //
    //T（n） =  O（1）
    void eat3(int n){

        System.out.println("等待一天");

        System.out.println("吃一个鸡腿");

    }

    //场景4，T（n） = 0.5n^2 + 0.5n，执行次数是一个多项式。
    //最高阶项为0.5n^2，省去系数0.5，转化的时间复杂度为：
    //
    //T（n） =  O（n^2）
    void eat4(int n){

        for(int i=0; i<n; i++){

            for(int j=0; j<i; j++){

                System.out.println("等待一天");

            }

            System.out.println("吃一寸面包");

        }

    }
    /**
     * 这四种时间复杂度究竟谁用时更长，谁节省时间呢？稍微思考一下就可以得出结论：
     *
     *
     * O（1）< O（logn）< O（n）< O（n^2）
     */

}
