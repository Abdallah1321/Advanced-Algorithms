package com.abdallah;

public class BTree {

    public Node root;

    public BTree() {
        root = null;
    }

    public BTree duplicateTree(){
        BTree t2 = new BTree(); //declaring new tree
        return duplicateTree(root, t2); //call the function on the tree root and we pass new tree
    }
    public BTree duplicateTree(Node node, BTree tree){
        Node old; //parent

        if(node == null)
            return null;

        if (node == root){
            tree.root = new Node(node.data);
        }
        BTree t2 =duplicateTree(node.left, tree);
        duplicateTree(node.right, tree);

        old = node.left;

        node.left = new Node(node.data);
        node.left.left = old;

        return tree;
    }

    public void displayTree() {
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
}
