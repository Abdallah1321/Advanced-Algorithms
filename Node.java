package com.abdallah;

public class Node {
    int data;
    Node left;
    Node right;
    Node parent;

    boolean color;

    public Node(int data)
    {
        this(data,null,null);
    }

    public Node(int data, Node left, Node right)
    {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}
