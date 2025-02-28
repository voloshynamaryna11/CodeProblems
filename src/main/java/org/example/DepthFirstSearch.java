package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch {

    //Given the root of a binary tree and an integer targetSum, return the number of paths where the sum of the values along the path equals targetSum.
    //
    //The path does not need to start or end at the root or a leaf but must go downwards.
    public static int pathSum(TreeNode root, int targetSum) {
        Stack<TreeNode> nodes = new Stack<>();
        nodes.add(root);
        int resultNum = 0;

        while (!nodes.isEmpty()) {
            TreeNode pop = nodes.pop();
            resultNum += helper(pop, targetSum);

            if (pop.left != null) {
                nodes.push(pop.left);
            }

            if (pop.right != null) {
                nodes.push(pop.right);
            }
        }

        return resultNum;
    }

    static int helper(TreeNode node, long targetSum) {
        if (node == null) {
            return 0;
        }
        if (node.val == targetSum) {
            return 1 + helper(node.left, 0) + helper(node.right, 0);
        }

        long newTargetSum = targetSum - node.val;
        return helper(node.left, newTargetSum) + helper(node.right, newTargetSum);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
//        Node node4 = new Node(0, null, null);
//        Node node5 = new Node(0, null, null);
//        Node node6 = new Node(0, null, null);
//        Node node7 = new Node(0, null, null);
//
//        Node node2 = new Node(0, node4, node5);
//        Node node3 = new Node(0, node6, node7);
//
//        Node node = new Node(0, node2, node3);

        TreeNode root = new TreeNode(3,
                                     new TreeNode(9, null, null),
                                     new TreeNode(20,
                                                  new TreeNode(15, null, null),
                                                  new TreeNode(7, null, null)
                                     )
        );


        System.out.println(minDepth(root));
    }

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int depth = 0;

        List<Integer> integers = new ArrayList<>();
        helpFunction(root, depth, integers);

        return integers.stream().min(Integer::compareTo).get() + 1;
    }

    private static void helpFunction(TreeNode root, int depth, List<Integer> depths) {
        if(root == null) {
            return;
        }

        if (root.right == null && root.left == null) {
            depths.add(depth);
            return;
        }

        helpFunction(root.left, depth+ 1, depths);
        helpFunction(root.right, depth + 1, depths);
    }
}
