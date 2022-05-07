package com.abdallah;

public class HeapSort {

    public void sort(int arr[]){
        int n = arr.length;
        // build max heap
        for (int i = n / 2 - 1; i >=0; i--){
            heapify(arr, n, i);
        }

        // Heap Sort
        for(int i = n - 1; i >=0; i--){
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            //Heapify root element
            heapify(arr,i,0);
        }
    }

    void heapify(int arr[], int n, int i){
        // Find largest among root, left child and right child

        int largest = i;
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;

        if (leftChild < n && arr[leftChild] > arr[largest]){
            largest = leftChild;
        }
        if (rightChild < n && arr[rightChild] > arr[largest]){
            largest = rightChild;
        }

        // swap and continue heapifying if root not largest
        if(largest != i){
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }

    static void printHeap(int arr[]){
        int n = arr.length;
        for(int i = 0; i<n; ++i){
            System.out.print(arr[i] + " ");
        }
    }
}
