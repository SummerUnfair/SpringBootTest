package com.DataSortTest;

import java.util.Arrays;

/**
 * Output source：控制台
 * function:冒泡排序(优化版)
 * change:加入中断开关
 * 对于实现数组排序，冒泡排序在最坏的情况下需要进行n-1趟排序
 *原理：比较两个相邻的元素，如需要从小到大排序，则将大的那个调到右边，同理，从大到小的排序，就将值小的数调到右边
 * Created by FH on 2019/10/9.
 */
public class BubbleSort_advance_version {

    public static void main(String[] args){

        int[] array = new int[]{5,8,6,3,9,2,1,7};

        sort(array);

        System.out.println(Arrays.toString(array));

    }

    private static void sort(int array[]) {

        int tmp  = 0;

        for(int i = 0; i < array.length-1; i++) {

            //有序标记，每一轮的初始是true
            boolean isSorted = true;

            for(int j = 0; j < array.length - i - 1; j++) {

                if(array[j] > array[j+1]) {

                    tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    //有元素交换，所以不是有序，标记变为false
                    isSorted = false;

                }

            }

            if(isSorted){
                break;
            }
        }
    }

}
