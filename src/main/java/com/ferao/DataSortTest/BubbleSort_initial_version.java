package com.ferao.DataSortTest;

/**
 * Output source：控制台
 * function:冒泡排序
 * 对于实现数组排序，冒泡排序在最坏的情况下需要进行n-1趟排序
 *原理：比较两个相邻的元素，如需要从小到大排序，则将大的那个调到右边，同理，从大到小的排序，就将值小的数调到右边
 * Created by FH on 2019/10/9.
 */
public class BubbleSort_initial_version {

    public static void main(String[] args) {


    }

    public static void bubbleSort(int []arr ){

        if (arr==null||arr.length<2){

            return;

        }
        //第一个for循环是程序需要执行多少遍
        for (int i=arr.length-1;i>0;i--){

            //第二个for循环是每趟需要比较多少次
            for(int j=0;j<arr.length-1-i;j++){

                //此处是从大到小排列

                if(arr[j]<arr[j+1]){

                    //定义一个临时变量temp

                    int temp=arr[j];

                    arr[j]=arr[j+1];

                    arr[j+1]=temp;

                }

            }

        }
    }

    public int[] bubbleSort_advance_version(int[] arr){

        //第一个for循环是程序需要执行要走多少趟

        for(int i=0;i<arr.length-1;i++){

            //第二个for循环是每趟需要比较多少次

            for(int j=0;j<arr.length-1-i;j++){

                //此处是从大到小排列

                if(arr[j]<arr[j+1]){

                    //定义一个临时变量temp

                    int temp=arr[j];

                    arr[j]=arr[j+1];

                    arr[j+1]=temp;

                }

            }

        }

        return arr;

    }

}
