package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class BreadthFirstSearch {

    // Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
    //
    //There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.
    // Internally, pos is used to denote the index of the node that tail's next pointer is connected to (0-indexed). It is -1 if there is no cycle.
    // Note that pos is not passed as a parameter.
    public ListNode detectCycle(ListNode head) {
        List<ListNode> listNodes = new ArrayList<>();
        while(!listNodes.contains(head) || head != null) {
            listNodes.add(head);
            head = head.next;
            if(head != null) {
                if(listNodes.contains(head)) {
                    return head;
                }
            }
        }

        return null;
    }

    //Just simple go through with this method
    static void breadthSearch(TreeNode treeNode) {
        Queue<TreeNode> treeNodes = new PriorityQueue<>();
        treeNodes.add(treeNode);

        while (!treeNodes.isEmpty()) {
            TreeNode peek = treeNodes.poll();
            System.out.println(peek.val);
            if (peek.right != null) {
                treeNodes.add(peek.right);
            }

            if (peek.left != null) {
                treeNodes.add(peek.left);
            }
        }
    }


    //A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.
    //
    //The path sum of a path is the sum of the node's values in the path.
    //
    //Given the root of a binary tree, return the maximum path sum of any non-empty path.
    public static int maxPathSum(TreeNode root) {
        List<Integer> maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes = new ArrayList<>();
        int maxPathSum = getMaxPathSum(root, maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes);
        maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes.add(maxPathSum);
        return maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes.stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    private static int getMaxPathSum(TreeNode treeNode, List<Integer> maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes) {
        if(treeNode == null) {
            return 0;
        }

        int leftPathMaxSum = getMaxPathSum(treeNode.left, maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes);
        int rightPathMaxSum = getMaxPathSum(treeNode.right, maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes);
        int maxLeftRightOrFullLeftRightAndCurrentNode = (treeNode.left == null && treeNode.right == null) ? treeNode.val : (treeNode.left == null) ? Math.max(
            rightPathMaxSum,
            treeNode.val + rightPathMaxSum) : (treeNode.right == null) ? Math.max(leftPathMaxSum,
                                                                                  treeNode.val + leftPathMaxSum) : Math.max(
            treeNode.val + leftPathMaxSum + rightPathMaxSum, Math.max(leftPathMaxSum, rightPathMaxSum));
        maxLeftOrRightWithoutParentAndWithFullParentAndBothChildNodes.add(maxLeftRightOrFullLeftRightAndCurrentNode);

        return (treeNode.left == null && treeNode.right == null) ? treeNode.val : (treeNode.left == null) ? Math.max(treeNode.val, treeNode.val + rightPathMaxSum) :
            (treeNode.right == null) ? Math.max(treeNode.val, treeNode.val + leftPathMaxSum) : Math.max(treeNode.val, Math.max(treeNode.val + leftPathMaxSum, treeNode.val + rightPathMaxSum));
    }

    // Given a binary tree, return the zigzag level order traversal of its nodes’ values. (ie, from left to right, then right to left for the next level and alternate between).
    //
    //Example :
    //
    //Given binary tree
    //
    //    3
    //   / \
    //  9  20
    //    /  \
    //   15   7
    //return
    //
    //[
    //         [3],
    //         [20, 9],
    //         [15, 7]
    //]
    public static int[][] zigzagLevelOrder(TreeNode A) {
        List<List<Integer>> result = new ArrayList<>();

        zigZag(result, false, A, new ArrayList<>());

        return  result.stream()
                      .map(innerList -> innerList.stream().mapToInt(Integer::intValue).toArray())
                      .toArray(int[][]::new);
    }

    private static void zigZag(List<List<Integer>> result,  boolean forward, TreeNode currentNode, List<Integer> currentList) {
        if(currentNode == null) {
            return;
        }

        currentList.add(currentNode.val);
        if(result.isEmpty()) {
            result.add(currentList);
        }

        List<Integer> list = new ArrayList<>();
        if(forward) {
            zigZag(result, !forward, currentNode.left, list);
            zigZag(result, !forward, currentNode.right, list);
        } else {
            zigZag(result, !forward, currentNode.right, list);
            zigZag(result, !forward, currentNode.left, list);
        }

        if(!list.isEmpty()) {
            result.add(list);
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

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                                     new TreeNode(9, null, null),
                                     new TreeNode(20,
                                                  new TreeNode(15, null, null),
                                                  new TreeNode(7, null, null)
                                     )
        );

        Arrays.stream(zigzagLevelOrder(root))
            .forEach(System.out::println);
    }
}
