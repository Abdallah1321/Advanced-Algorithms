package com.abdallah;

import java.util.concurrent.*;

public class MergeSort extends RecursiveAction{

    private final int[] arr;

    public MergeSort(int[] arr){
        this.arr = arr;
    }

    public static void concurrentMergeSort(int[] arr){
        MergeSort mainTask = new MergeSort(arr);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    @Override
    public void compute(){
        if(arr.length > 1) {
            int mid = arr.length / 2;
            int low = 0;
            int high = arr.length-1;
            int[] firstHalf = new int[mid];
            System.arraycopy(arr, 0, firstHalf,0,mid);

            int[] secondHalf = new int[arr.length - mid];
            System.arraycopy(arr, mid, secondHalf, 0, arr.length - mid);

            MergeSort firstHalfThread = new MergeSort(firstHalf);
            MergeSort secondHalfThread = new MergeSort(secondHalf);

            invokeAll(firstHalfThread, secondHalfThread);

            merge(firstHalf, secondHalf, low, high);
        }
    }

    public void merge(int[] leftArray,int[] rightArray, int low, int high){

        int leftIndex = 0;
        int rightIndex = 0;

        for(int i = low; i<high + 1; i++){
            if(leftIndex < leftArray.length && rightIndex < rightArray.length) {
                if(leftArray[leftIndex] < rightArray[rightIndex]){
                    arr[i] = leftArray[leftIndex];
                    leftIndex++;
                } else{
                    arr[i] = rightArray[rightIndex];
                    rightIndex++;
                    }
            } else if (leftIndex < leftArray.length){
                arr[i] = leftArray[leftIndex];
                leftIndex++;
            } else if (rightIndex < rightArray.length) {
                arr[i] = rightArray[rightIndex];
                rightIndex++;
            }
        }
    }
}

