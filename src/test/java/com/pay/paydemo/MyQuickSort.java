package com.pay.paydemo;

/**
 * @author xinyan
 * @title: MyQuickSort
 * @projectName paydemo
 * @description: TODO
 * @date 2020/4/1 9:49
 */
public class MyQuickSort {

    public static void quickSort(int[] arr, int low, int high) {
        int i, j, temp;
        if (low > high) {
            return;
        }
        i = low;
        j = high;

        temp = arr[low];

        while (i < j) {
            while (temp <= arr[j] && i < j) {
                j--;
            }
            while (temp >= arr[i] && i < j) {
                i++;
            }
            if (i<j){
                int t=arr[j];
                arr[j]=arr[i];
                arr[i]=t;
            }
        }
        arr[low]=arr[i];
        arr[i]=temp;
        quickSort(arr,low,j-1);
        quickSort(arr,j+1,high);
    }


    public static void main(String[] args) {
        int[] arr = {0,4,3,1,6,9,7,8,9};
        quickSort(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
