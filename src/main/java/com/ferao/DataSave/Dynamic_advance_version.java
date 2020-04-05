package com.ferao.DataSave;

import java.util.HashMap;

/**
 * Output source：控制台
 * function:动态规划 (备忘录算法)
 *  defect:时间复杂度高
 * Created by FH on 2019/10/9.
 */
public class Dynamic_advance_version {

    public static HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();

    public static void main(String[] args) {

    long a =   (long) getClimbingWays(100);

    System.out.println(a);

    }

    static  int getClimbingWays(int n){

        if(n<1){
            return  0;
        }
        if (n==1){
            return  1;
        }
        if (n==2){
            return  2;
        }
        if (map.containsKey(n)){
            return map.get(n);
        }else {
            int value =getClimbingWays(n-1)+getClimbingWays(n-2);
            map.put(n,value);
            return  value;
        }

    }


}

/**
 * 集合map是一个备忘录。当每次需要计算F(N)的时候，会首先从map中寻找匹配元素。如果map中存在，就直接返回结果，如果map中不存在，就计算出结果，存入备忘录中。
 *
 * 现在程序的性能已经得到了明显的优化，但这样还不是真正的动态规划实现
 *
 * */
