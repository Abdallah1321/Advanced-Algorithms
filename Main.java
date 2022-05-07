package com.abdallah;

import java.util.*;

public class Main {

    public static void main(String[] args) {


		BTree t1 = new BTree();
		t1.root = new Node(2);
		t1.root.left = new Node(1);
		t1.root.right = new Node(3);
		t1.root.left.right = new  Node(4);

		int[] A = new int[]{ 30,15,45,35,60,55 };
		RBTree t2 = new RBTree();
		System.out.println("New Red Black Tree created!");

		for (int i = 0; i < A.length; i++){
			int n = i +1;
			System.out.println("Insert #" + n);
			t2.insert(A[i]);
			t2.displayRBTree();
		}

		t2.delete(45);
		t2.displayRBTree();


		System.out.println("Original tree: ");
		t1.displayTree();
		t1.duplicateTree();
		System.out.println("Duplicated tree: ");
		t1.displayTree();

		

		int size;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the size of the array: ");
		size = sc.nextInt();

		int[] arr = new int[size];
		System.out.println("Enter the elements of array: ");
		for(int i = 0; i < size; i++){
			arr[i] = sc.nextInt();
		}

		HeapSort heap = new HeapSort();
		heap.sort(arr);

		System.out.println("Sorted heap is");
		HeapSort.printHeap(arr);
	
		int vertices = 5;
		WeightedGraph.Graph graph = new WeightedGraph.Graph(vertices);
		graph.addEdge('A', 'B', 5);
		graph.addEdge('A', 'C', 2);
		graph.addEdge('B', 'D', 1);
		graph.addEdge('B', 'E', 7);
		graph.addEdge('C', 'D', 5);
		graph.addEdge('C', 'E', 8);
		graph.addEdge('D', 'E', 5);
		graph.printList();
		graph.printMatrix();

		graph.breadthFirst('A');
		graph.depthFirst('A');

		int primV = 6;

		Prim path = new Prim(primV);
		int[][] primG = new int[][]{
				{0,7,0,8,0,0},
				{7,0,6,3,0,0},
				{0,6,0,4,2,5},
				{8,3,4,0,3,0},
				{0,0,2,3,0,2},
				{0,0,5,0,2,0}
		};

		path.prim(primG);

		int[] arr2 = new int[]{90, 3, 70 ,4, 2, 10, 87, 65, 24, 19};

		System.out.println("\nArray before concurrent merge sort: \n" + Arrays.toString(arr));
		MergeSort.concurrentMergeSort(arr);

		System.out.println();
		System.out.println("Array after concurrent merge sort: \n"+Arrays.toString(arr));

    }
}
