package com.DataSave;

/**
 * Output source：控制台
 * function:动态规划 (递归求解)
 *  defect:时间复杂度高
 * Created by FH on 2019/10/9.
 */
public class Dynamic_initial_version {

    public static void main(String[] args) {

        int a =getClimbingWays(10);

        System.out.println(a);
    }

    static int getClimbingWays(int n ){

        if(n<1){
            return  0;
        }

        if(n==1){
            return  1;
        }

        if(n==2){

            return 2;
        }

        return  getClimbingWays(n-1)+getClimbingWays(n-2);

    }

    /**
     * 画图结构与二叉树相同，树的节点数就是递归所需计算的次数
     *
     * 由此不难看出这颗二叉树的高度是N-1，节点个数接近2的N-1次方，所以时间复杂度可以近似地看作是O（2^N）
     */
}
