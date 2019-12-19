package DataSortTest;

/**
 * Output source：控制台
 * function:插入排序
 * 实现使用选择排序对数组元素进行排序，在选择排序中搜索最小的元素并将其排列到适当的位置
 * Created by FH on 2019/10/9.
 */

public class InsertionSort_initial_version {

    public static void main(String[] args) {

        int [] arr1={7,6,5,7,3,2,1};

        insertSort(arr1);

        for (int a :arr1) {
            System.out.print(a+"\t");
        }

    }

    public static void insertSort(int [] arr){

        for (int i=1;i<arr.length;i++){

            int  insertVal=arr[i];

            int index=i-1;

            //第一个数大于第二个数进入while
            while (index>=0 && insertVal<arr[index]){

                arr[index+1]=arr[index];

                //退出循环
                index--;
            }

            arr[index+1]=insertVal;
        }

    }

}
