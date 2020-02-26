package com.DataSave;

/**
 * Information sources：有序数组
 * Output source：控制台
 * function:二分查找，找到目标整数的下标，或者返回-1
 * Created by FH on 2019/10/9.
 */

public class Binary_Search {

    public static void main(String[] args) {
        int[] array = new int[1000];
        for(int i=0; i<1000;i++){
            array[i] = i;
        }
        System.out.println(binarySearch(array, 173));

    }

    public static int binarySearch(int []array,int target){
        //查找范围起点
        int start=0;
        //查找范围终点
        int end=array.length-1;
        //查找范围中位数
        int mid;
        while(start<=end){
            //mid=(start+end)/2 有可能溢出
            mid=start+(end-start)/2;
            if(array[mid]==target){
                return mid;
            }else if(array[mid]<target){
                start=mid+1;
            }else{
                end=mid-1;
            }
        }
        return -1;

    }

}
