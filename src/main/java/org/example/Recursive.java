package org.example;

public class Recursive {

    // You are given two positive integers n and k. There are n children numbered from 0 to n - 1 standing in a queue in order from left to right.
    //
    //Initially, child 0 holds a ball and the direction of passing the ball is towards the right direction. After each second, the child holding the ball passes it to the child next to them. Once the ball reaches either end of the line, i.e. child 0 or child n - 1, the direction of passing is reversed.
    //
    //Return the number of the child who receives the ball after k seconds.
    //
    //
    //
    //Example 1:
    //
    //Input: n = 3, k = 5
    //
    //Output: 1
    //
    //Explanation:
    //
    //Time elapsed	Children
    //0	[0, 1, 2]
    //1	[0, 1, 2]
    //2	[0, 1, 2]
    //3	[0, 1, 2]
    //4	[0, 1, 2]
    //5	[0, 1, 2]
    public static int numberOfChild(int n, int k) {
       return help(n, 0, k, true);
    }

    static int help(int n, int m, int k, boolean flag) {
        if(k == 0) {
            return m;
        }

        if(m == n - 1) {
            flag = false;
        }

        if(m == 0)
        {
            flag = true;
        }

        return flag ? help(n, m+1, k-1, flag) : help(n, m-1, k-1, flag);
    }

    static void printArrayElements(int[] array) {
        if (array.length == 1) {
            System.out.println(array[0]);
            return;
        }

        System.out.println(array[0]);
        int[] newArray = new int[array.length - 1];

        // Copy elements before the 0 index
        System.arraycopy(array, 1, newArray, 0, array.length - 1);
        printArrayElements(newArray);
    }

    static boolean isPalindrome(String str) {
        if (str.length() == 1) {
            return true;
        }

        if (str.length() == 2) {
            if (str.substring(0, str.length() - 1).equals(str.substring(str.length() - 1))) {
                return true;
            }
            return false;
        }

        return str.substring(0, 1).equals(str.substring(str.length() - 1)) && isPalindrome(str.substring(1, str.length() - 1));
    }

    static int raisedInt(int a, int b) {
        if (b == 1) {
            return a;
        }

        return a * (raisedInt(a, b - 1));
    }

    static BinaryTreeNode fillBinaryTree(int value, BinaryTreeNode binaryTreeNode) {
        if(binaryTreeNode == null) {
            binaryTreeNode = new BinaryTreeNode(value);
            return binaryTreeNode;
        }

        if (binaryTreeNode.left == null && value < binaryTreeNode.value) {
            binaryTreeNode.left = new BinaryTreeNode(value);
            return binaryTreeNode;
        }

        if (binaryTreeNode.right == null && value > binaryTreeNode.value) {
            binaryTreeNode.right = new BinaryTreeNode(value);
            return binaryTreeNode;
        }

        return value < binaryTreeNode.value ? fillBinaryTree(value, binaryTreeNode.left) : fillBinaryTree(value, binaryTreeNode.right);
    }

    static class BinaryTreeNode {
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;

        public BinaryTreeNode(int value) {
            this.value = value;
            BinaryTreeNode left = null;
            BinaryTreeNode right = null;
        }

        public BinaryTreeNode(int value, BinaryTreeNode left, BinaryTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}

