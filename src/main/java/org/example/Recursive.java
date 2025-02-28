package org.example;

import java.util.ArrayList;
import java.util.List;

public class Recursive {
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

    public static void main(String[] args) {
//        BinaryTreeNode node9 = new BinaryTreeNode(9);
//        BinaryTreeNode node8 = new BinaryTreeNode(8);
//        BinaryTreeNode node15 = new BinaryTreeNode(15);
//        BinaryTreeNode node1 = new BinaryTreeNode(1);
//
//        BinaryTreeNode node5 = new BinaryTreeNode(5, node8, node9);
//        BinaryTreeNode node3 = new BinaryTreeNode(3, node1, node15);
//        BinaryTreeNode node11 = new BinaryTreeNode(1, node3, node5);

        BinaryTreeNode node5 = new BinaryTreeNode(5, null, null);
        BinaryTreeNode node3 = new BinaryTreeNode(3, null, null);
        BinaryTreeNode node11 = new BinaryTreeNode(1, node3, node5);

        BinaryTreeNode binaryTreeNode = fillBinaryTree(7, node11);

        printTree(node11);
    }

    public static void printTree(BinaryTreeNode root) {
        List<List<String>> lines = new ArrayList<>();
        List<BinaryTreeNode> level = new ArrayList<>();
        List<BinaryTreeNode> next = new ArrayList<>();

        level.add(root);
        int nodeCount = 1;
        int widest = 0;

        while (nodeCount > 0) {
            List<String> line = new ArrayList<>();
            nodeCount = 0;

            for (BinaryTreeNode node : level) {
                if (node == null) {
                    line.add(" ");
                    next.add(null);
                    next.add(null);
                } else {
                    String value = String.valueOf(node.value);
                    line.add(value);
                    if (value.length() > widest) {
                        widest = value.length();
                    }
                    next.add(node.left);
                    next.add(node.right);
                    if (node.left != null) nodeCount++;
                    if (node.right != null) nodeCount++;
                }
            }

            if (widest % 2 == 1) widest++;
            lines.add(line);
            List<BinaryTreeNode> temp = level;
            level = next;
            next = temp;
            next.clear();
        }

        int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int half = perPiece / 2 - 1;
            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f.equals(" ")) f = " ".repeat(widest);
                int gap = half - f.length() / 2;
                System.out.print(" ".repeat(gap) + f + " ".repeat(gap));
            }
            System.out.println();
            perPiece /= 2;
        }
    }
}

