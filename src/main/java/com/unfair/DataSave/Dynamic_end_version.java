package com.unfair.DataSave;

/**
 * Output source：控制台
 * function:动态规划
 * 利用简洁的自底向上的递推方式，实现了时间和空间上的最优化
 * （不过这是动态规划领域最简单的问题，因为它只有一个变化维度）
 * Created by FH on 2019/10/9.
 */
public class Dynamic_end_version {

    public static void main(String[] args) {

      int a = getClimbingWags(10);

      System.out.println(a);

    }

    static  int getClimbingWags(int n){

        if (n<1){

            return  0;
        }

        if (n==1){

            return  1;
        }

        if (n==2){

            return  2;
        }

        int a =1;
        int b=2;
        int temp=0;

        for (int i=3; i<=n;i++){

            temp =a+b;

            a=b;
            b=temp;

        }

        return  temp;
    }


}
/**
 * 不保存子状态，直接自底向上的算，第三阶台阶只需要依赖第二阶和第一阶台阶。第四阶台阶只依赖于第三阶台阶和第二阶台阶
 **/