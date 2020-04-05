package com.ferao.DataSortTest;

/**
 * Output source：控制台
 * function:选择排序
 * 实现使用选择排序对数组元素进行排序，在选择排序中搜索最小的元素并将其排列到适当的位置
 * Created by FH on 2019/10/9.
 */
public class SelectionSort_initial_version {

    public static void main(String[] args) {

        int [] arr1={7,6,5,7,3,2,1};

        selectSort(arr1);

        for (int a :arr1) {
            System.out.print(a+"\t");
        }
    }

    public static void selectSort(int [] arr)
    {

        for (int i=0;i<arr.length-1;i++){
            //所有元素
            int index = i;

            for (int j = i + 1; j < arr.length; j++) {

                //如果第一个元素大于第二个元素，将第二个元素设置为数组第一位
                if (arr[j] < arr[index]) {
                    index = j;// searching for lowest index
                }
            }
            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;

        }


    }


}
