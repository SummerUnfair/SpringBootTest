package com.DataSortTest;

import java.util.Arrays;

public class SelectionSort_advance_version {

    public static void main(String[] args) {
        int[] array = new int[]{3,4,2,1,5,6,7,8,30,50,1,33,24,5,-4,7,0};
        selectionSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void selectionSort(int[] array){

        for(int i=0; i<array.length-1; i++){
            int minIndex = i;

            for(int j = i+1;j<array.length;j++){

                minIndex = array[minIndex]<array[j] ? minIndex : j;
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }


}
