package com.abdallah;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class RBTree {

    public Node root;
    static final boolean RED = false;
    static final boolean BLACK = true;

    public RBTree() {
        root = null;
    }

    public void rotateRight(Node node){
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null){
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        swap(parent, node, leftChild);
    }

    public void rotateLeft(Node node){
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null){
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        swap(parent, node, rightChild);
    }

    public void swap(Node parent, Node oldChild, Node newChild){
        if (parent == null){
            root = newChild;
        }
        else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild){
            parent.right = newChild;
        }else{
            return;
        }

        if (newChild != null){
            newChild.parent = parent;
        }
    }

    public void insert(int key){
        Node node = root;
        Node parent = null;

        // traverse tree

        while (node != null){
            parent = node;
            if(key < node.data){
                node = node.left;
            } else if (key > node.data){
                node = node.right;
            }
            else {
                return;
            }
        }

        // insert node

        Node newNode = new Node(key);
        newNode.color = RED;
        if(parent == null){
            root = newNode;
        }
        else if (key < parent.data){
            parent.left = newNode;
        }
        else {
            parent.right = newNode;
        }
        newNode.parent = parent;

        fixColorsInsert(newNode);
    }

    /*

    Cases to fix color
    x
    Case 1: New node is the root
    Case 2: Parent node is red and the root
    Case 3: Parent and uncle nodes are red
    Case 4: Parent node is red, uncle node is black, inserted node is "inner grandchild"
    Case 5: Parent node is red, uncle node is black, inserted node is "outer grandchild"
     */

    public void fixColorsInsert(Node node){
        Node parent = node.parent;

        if (parent == null){
            node.color = BLACK;
            return;
        }

        if (parent.color == BLACK) {
            return;
        }

        Node grandparent= parent.parent;

        if (grandparent == null){
            parent.color = BLACK;
            return;
        }

        Node uncle = getUncle(parent);

        if (uncle != null && uncle.color == RED){
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;

            fixColorsInsert(grandparent);
        }

        else if (parent == grandparent.left){
            if (node == parent.right){
                rotateLeft(parent);

                parent = node;
            }

            rotateRight(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }

        else {
            if (node == parent.left){
                rotateRight(parent);

                parent = node;
            }

            rotateLeft(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }

    }

    public Node getUncle(Node parent){
        Node grandparent = parent.parent;
        if(grandparent.left == parent){
            return grandparent.right;
        }
        else if(grandparent.right == parent) {
            return grandparent.left;
        } else{
            return null;
        }
    }

    public void delete(int key){
        Node node = root;

        while(node != null && node.data != key){
            if(key < node.data){
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == null){
            return;
        }

        Node next;
        boolean deletedColor;

        if (node.left == null || node.right == null){
            next = deleteWithChild(node);
            deletedColor = node.color;
        }

        else {
            Node successor = findMin(node.right);

            node.data = successor.data;

            next = deleteWithChild(successor);
            deletedColor = successor.color;
        }

        if (deletedColor == BLACK){
            fixColorsDelete(next);

            if(next.getClass() == Nil.class){
                swap(next.parent, next, null);
            }
        }
    }

    public Node deleteWithChild(Node node){
        if(node.left !=null){
            swap(node.parent, node, node.left);
            return node.left;
        }

        else if(node.right != null){
            swap(node.parent, node, node.right);
            return node.right;
        }

        else {
            Node newChild = node.color == BLACK ? new Nil() : null;
            swap(node.parent, node, newChild);
            return newChild;
        }
    }

    private static class Nil extends Node {
        private Nil() {
            super(0);
            this.color = BLACK;
        }
    }

    public Node findMin(Node node){
        while(node.left != null){
            node = node.left;
        }
        return node;
    }

    public void fixColorsDelete(Node node){
        if(node == root){
            return;
        }

        Node sibling = getSibling(node);

        if (sibling.color == RED){
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }

        if (isBlack(sibling.left) && isBlack(sibling.right)){
            sibling.color = RED;

            if (node.parent.color == RED){
                node.parent.color = BLACK;
            }
            else{
                fixColorsDelete(node.parent);
            }
        }
        else {
            handleBlackSiblings(node, sibling);
        }
    }

    public Node getSibling(Node node){
        Node parent = node.parent;
        if (node == parent.left){
            return parent.right;
        } else if (node == parent.right) {
            return parent.left;
        } else {
            return null;
        }
    }

    public boolean isBlack(Node node){
        return node == null || node.color == BLACK;
    }

    public void handleRedSibling(Node node, Node sibling){
        sibling.color = BLACK;
        node.parent.color = RED;

        if (node == node.parent.left){
            rotateLeft(node.parent);
        } else{
            rotateRight(node.parent);
        }
    }

    public void handleBlackSiblings(Node node, Node sibling){
        boolean nodeIsLeftChild = node == node.parent.left;

        if(nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = BLACK;
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)){
            sibling.right.color = BLACK;
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
        }

        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (nodeIsLeftChild){
            sibling.right.color = BLACK;
            rotateLeft(node.parent);
        } else {
            sibling.left.color = BLACK;
            rotateRight(node.parent);
        }
    }

    public void displayRBTree() {
        java.util.Stack<Node> globalStack = new java.util.Stack<Node>();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println("......................................................");
        while (isRowEmpty == false) {
            java.util.Stack<Node> localStack = new java.util.Stack<Node>();
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) {
                Node temp = globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.data);
                    if (temp.color == true)
                        System.out.print(" BLACK");
                    else
                        System.out.print(" RED");
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if (temp.left != null || temp.right != null) isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++)
                    System.out.print(' ');
            } // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        } // end while isRowEmpty is false
        System.out.println("......................................................");
    }


    private void printHelper(Node root, String indent, boolean last) {
        if (root != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            String sColor = root.color == RED ? "RED" : "BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }

    public void printTree() {
        printHelper(this.root, "", true);
    }
}


