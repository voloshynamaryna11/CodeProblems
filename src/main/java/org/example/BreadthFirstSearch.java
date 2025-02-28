package org.example;

import java.util.PriorityQueue;
import java.util.Queue;

public class BreadthFirstSearch {

    //Just simple go through with this method
    static void breadthSearch(TreeNode treeNode) {
        Queue<TreeNode> treeNodes = new PriorityQueue<>();
        treeNodes.add(treeNode);

        while(!treeNodes.isEmpty()) {
            TreeNode peek = treeNodes.poll();
            System.out.println(peek.val);
            if(peek.right != null) {
                treeNodes.add(peek.right);
            }

            if(peek.left != null) {
                treeNodes.add(peek.left);
            }
        }
    }

    private static class TreeNode implements Comparable<TreeNode> {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(TreeNode o) {
            return Integer.compare(this.val, o.val);
        }
    }

    public static void main(String[] args) {
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node5 = new TreeNode(5, null, null);
        TreeNode node6 = new TreeNode(6, null, null);
        TreeNode node7 = new TreeNode(7, null, null);

        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode node3 = new TreeNode(3, node6, node7);

        TreeNode root = new TreeNode(1, node2, node3);
        breadthSearch(root);
    }
}
