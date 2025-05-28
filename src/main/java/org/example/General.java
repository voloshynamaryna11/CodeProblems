package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

public class General {

    // You will be given a square chess board with one queen and a number of obstacles placed on it. Determine how many squares the queen can attack.
    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
        int sumOfColumnAndRaw = 2 * (n - 1);

        int north = n - r_q;
        int south = r_q - 1;
        int east = n - c_q;
        int west = c_q - 1;

        int northEast = Math.min(north, east);
        int northWest = Math.min(north, west);
        int southEast = Math.min(south, east);
        int southWest = Math.min(south, west);

        for (int i = 0; i < obstacles.size(); i++) {
            List<Integer> obstackle = obstacles.get(i);
            Integer o_row = obstackle.get(0);
            Integer o_column = obstackle.get(1);

            int rawDiff = o_row - r_q;
            int columnDiff = o_column - c_q;

            if (o_row == r_q) {
                if (columnDiff > 0) {
                    east = Math.min(east, columnDiff - 1);
                } else {
                   west = Math.min(west, -columnDiff - 1);
                }
            } else if (o_column == c_q) {
                if (rawDiff > 0) {
                    north = Math.min(north, rawDiff - 1);
                } else {
                    south = Math.min(south, -rawDiff - 1);
                }

            } else if (Math.abs(rawDiff) == Math.abs(columnDiff)) {
                if (rawDiff > 0 && columnDiff > 0) {
                    //north_east
                    northEast = Math.min(northEast, rawDiff - 1);
                }

                if (rawDiff < 0 && columnDiff < 0) {
                    //south_west
                    southWest = Math.min(southWest, -rawDiff - 1);
                }

                if (rawDiff > 0 && columnDiff < 0) {
                    //north_west
                    northWest = Math.min(northWest, rawDiff - 1);
                }

                if (rawDiff < 0 && columnDiff > 0) {
                    //south_east
                    southEast = Math.min(southEast, -rawDiff - 1);
                }
            }
        }

        return north + south + east + west + northEast + northWest + southEast + southWest;
    }

    // Given an array of strings words and an integer k, return the k most frequent strings.
    //
    //Return the answer sorted by the frequency from highest to lowest. Sort the words with the same frequency by their lexicographical order.
    public static List<String> topKFrequent(String[] words, int k) {
        HashMap<String, Integer> frequentMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            frequentMap.merge(words[i], 1, (oldValue, newValue) -> oldValue + 1);
        }

        return frequentMap.entrySet()
                          .stream()
                          .sorted(new Comparator<Map.Entry<String, Integer>>() {
                              @Override
                              public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                  if (Objects.equals(o1.getValue(), o2.getValue())) {
                                      return o1.getKey().compareTo(o2.getKey());
                                  }
                                  return Integer.compare(o2.getValue(), o1.getValue());
                              }
                          })
                          .limit(k)
                          .map(Map.Entry::getKey)
                          .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(queensAttack(4, 0, 4, 4, List.of(List.of())));
    }

    // We can use run-length encoding (i.e., RLE) to encode a sequence of integers. In a run-length encoded array of even length encoding (0-indexed), for all even i, encoding[i] tells us the number of times that the non-negative integer value encoding[i + 1] is repeated in the sequence.
    //
    //For example, the sequence arr = [8,8,8,5,5] can be encoded to be encoding = [3,8,2,5]. encoding = [3,8,0,9,2,5] and encoding = [2,8,1,8,2,5] are also valid RLE of arr.
    //Given a run-length encoded array, design an iterator that iterates through it.
    //
    //Implement the RLEIterator class:
    //
    //RLEIterator(int[] encoded) Initializes the object with the encoded array encoded.
    //int next(int n) Exhausts the next n elements and returns the last element exhausted in this way. If there is no element left to exhaust, return -1 instead.
    class RLEIterator {
        private List<Integer> encoded;

        public RLEIterator(int[] encoding) {
            List<Integer> integers = new ArrayList<>();
            for (int i = 0; i < encoding.length; i = i + 2) {
                int amountOfRepeats = encoding[i];
                while (amountOfRepeats > 0) {
                    integers.add(encoding[i + 1]);
                    amountOfRepeats--;
                }
            }
            this.encoded = integers;
        }

        public int next(int n) {
            int removedInteger = -1;
            while (n != 0) {
                if (encoded.size() == 0) {
                    return -1;
                }
                removedInteger = encoded.remove(0);
                n--;
            }
            return removedInteger;
        }
    }

    // An n-bit gray code sequence is a sequence of 2n integers where:
    //
    //Every integer is in the inclusive range [0, 2n - 1],
    //The first integer is 0,
    //An integer appears no more than once in the sequence,
    //The binary representation of every pair of adjacent integers differs by exactly one bit, and
    //The binary representation of the first and last integers differs by exactly one bit.
    //Given an integer n, return any valid n-bit gray code sequence.
    //
    //
    //
    //Example 1:
    //
    //Input: n = 2
    //Output: [0,1,3,2]
    //Explanation:
    //The binary representation of [0,1,3,2] is [00,01,11,10].
    //- 00 and 01 differ by one bit
    //- 01 and 11 differ by one bit
    //- 11 and 10 differ by one bit
    //- 10 and 00 differ by one bit
    //[0,2,3,1] is also a valid gray code sequence, whose binary representation is [00,10,11,01].
    //- 00 and 10 differ by one bit
    //- 10 and 11 differ by one bit
    //- 11 and 01 differ by one bit
    //- 01 and 00 differ by one bit
    public static List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(0); // start with 0

        for (int i = 0; i < n; i++) {
            int size = result.size();
            int addNumber = 1 << i; // 2^i
            for (int j = size - 1; j >= 0; j--) {
                result.add(result.get(j) + addNumber);
            }
        }

        return result;
    }

    class Solution {
        Map<List<Integer>, List<Integer>> map = new HashMap<>();

        public Solution(int[][] rects) {
            Arrays.stream(rects).forEach(array -> {
                List<Integer> x = new ArrayList<>();
                List<Integer> y = new ArrayList<>();

                x.add(array[0]);
                x.add(array[2]);
                y.add(array[1]);
                y.add(array[3]);

                map.put(x, y);
            });
        }

        public int[] pick() {
            List<Map.Entry<List<Integer>, List<Integer>>> entries = new ArrayList<>(map.entrySet());
            Random random = new Random();
            int randomIndex = random.nextInt(map.size());
            List<Integer> x = entries.get(randomIndex).getKey();
            List<Integer> y = entries.get(randomIndex).getValue();

            int randomX = random.nextInt(x.get(0), x.get(1));
            int randomY = random.nextInt(y.get(0), y.get(1));
            return new int[]{randomX, randomY};
        }
    }

    // Given an array, find the nearest smaller element G[i] for every element A[i] in the array such that the element has an index smaller than i.
    //
    //More formally,
    //
    //    G[i] for an element A[i] = an element A[j] such that
    //    j is maximum possible AND
    //    j < i AND
    //    A[j] < A[i]
    //Elements for which no smaller element exist, consider next smaller element as -1.
    //
    //Input Format
    //
    //The only argument given is integer array A.
    //Output Format
    //
    //Return the integar array G such that G[i] contains nearest smaller number than A[i].If no such element occurs G[i] should be -1.
    //For Example
    //
    //Input 1:
    //    A = [4, 5, 2, 10, 8]
    //Output 1:
    //    G = [-1, 4, -1, 2, 2]
    //Explaination 1:
    //    index 1: No element less than 4 in left of 4, G[1] = -1
    //    index 2: A[1] is only element less than A[2], G[2] = A[1]
    //    index 3: No element less than 2 in left of 2, G[3] = -1
    //    index 4: A[3] is nearest element which is less than A[4], G[4] = A[3]
    //    index 4: A[3] is nearest element which is less than A[5], G[5] = A[3]
    public int[] prevSmaller(int[] A) {
        int n = A.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Pop elements greater than or equal to A[i]
            while (!stack.isEmpty() && stack.peek() >= A[i]) {
                stack.pop();
            }

            // If stack is empty, no smaller element
            result[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current element onto stack
            stack.push(A[i]);
        }

        return result;
    }

    // Given a string A denoting an expression. It contains the following operators '+', '-', '*', '/'.
    //
    //Chech whether A has redundant braces or not.
    //
    //NOTE: A will be always a valid expression.
    public static int braces(String A) {
        Stack<String> strings = new Stack<>();

        int lastOpenBraceIndex = -1;
        String[] split = A.split("");
        for (int i = 0; i < split.length; i++) {
            if (Objects.equals(split[i], ")") && i - lastOpenBraceIndex == 2) {
                return 1;
            }
            if (Objects.equals(split[i], "(")) {
                lastOpenBraceIndex = i;
                if (!strings.isEmpty() && strings.peek().equals(split[i]) && !checkForCloseBrace(split, i)) {
                    return 1;
                }
                strings.push(split[i]);
            }
        }

        return 0;
    }

    private static boolean checkForCloseBrace(String[] split, int currentElementIndex) {
        int j = currentElementIndex;
        while (split[j] != null && !split[j].equals(")")) {
            j++;
        }

        return (j + 1 < split.length && (isOperation(split[j + 1]))) || (currentElementIndex - 1 >= 0 && (isOperation(
            split[currentElementIndex - 1])));
    }

    private static boolean isOperation(String str) {
        if (str.equals("*") ||
            str.equals("+") ||
            str.equals("-") ||
            str.equals("/")) {
            return true;
        }
        return false;
    }

    public static TreeNode sortedListToBST(ListNode a) {
        TreeNode root = new TreeNode(a.val);
        a = a.next;

        fillNode(root, a);

        return root;
    }

    private static void fillNode(final TreeNode treeNode, ListNode partTobeAdded) {
        if (partTobeAdded != null) {
            TreeNode leftNode = new TreeNode(partTobeAdded.val);
            treeNode.left = leftNode;
        }

        if (partTobeAdded.next != null) {
            TreeNode right = new TreeNode(partTobeAdded.next.val);
            treeNode.right = right;
            partTobeAdded = partTobeAdded.next;
        }

        partTobeAdded = partTobeAdded.next;

        if (partTobeAdded != null) {
            ListNode halfOfListNode = getHalfOfListNode(partTobeAdded);
            fillNode(treeNode.left, halfOfListNode);
            if (partTobeAdded.next != null) {
                ListNode listNode = deleteAllAfterCurrentNode(halfOfListNode, partTobeAdded);
                fillNode(treeNode.right, listNode);
            }
        }
    }

    private static ListNode getHalfOfListNode(final ListNode listNode) {
        ListNode usualIterator = listNode;
        ListNode fastIterator = listNode;
        while (fastIterator != null && fastIterator.next != null) {
            usualIterator = usualIterator.next;
            fastIterator = fastIterator.next.next;
        }

        return usualIterator;
    }

    private static ListNode deleteAllAfterCurrentNode(ListNode listNode, ListNode sequence) {
        ListNode temp = sequence;
        ListNode tempCopy = temp;
        while (temp.next != listNode && temp != null) {
            temp = temp.next;
        }

        if (temp != null && temp.next != null) {
            temp.next = null;
        }

        return tempCopy;
    }

    public static void printTree(TreeNode root) {
        int height = getHeight(root);
        int width = (int) Math.pow(2, height) - 1;

        String[][] res = new String[height][width];
        for (String[] row : res) {
            Arrays.fill(row, " ");
        }

        fill(res, root, 0, 0, width - 1);

        for (String[] row : res) {
            System.out.println(String.join("", row));
        }
    }

    private static void fill(String[][] res, TreeNode node, int row, int left, int right) {
        if (node == null) {
            return;
        }
        int mid = (left + right) / 2;
        res[row][mid] = String.valueOf(node.val);
        fill(res, node.left, row + 1, left, mid - 1);
        fill(res, node.right, row + 1, mid + 1, right);
    }

    private static int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }

    //Extra classes section
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}

