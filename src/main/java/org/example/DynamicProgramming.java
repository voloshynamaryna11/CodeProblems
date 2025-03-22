package org.example;

import java.util.HashMap;
import java.util.Map;

public class DynamicProgramming {

    // Find the longest subsequence in array. Return its length
    public static int lengthOfLIS(int[] nums) {
        //start from the last index (more subsequences before = more chances to find the longest lis)
        //find the lis of sequences which ended with the number smaller than current number
        //find max of it and + 1
        //go next

        if (nums.length == 1) {
            return 1;
        }

        Map<Integer, Integer> map = new HashMap<>();
        int currentIndex = nums.length - 1;

        int theLis = findTheLis(currentIndex, nums, map);
        map.put(currentIndex, theLis);
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(i)) {
                findTheLis(i, nums, map);
            }
        }
        return map.entrySet().stream().map(Map.Entry::getValue).max(Integer::compareTo).get();
    }

    private static int findTheLis(int currentIndex, int[] nums, Map<Integer, Integer> map) {
        if (currentIndex == 0) {
            return 1;
        }

        if (map.containsKey(currentIndex)) {
            return map.get(currentIndex);
        }

        int max = 0;
        for (int i = currentIndex; i >= 0; i--) {
            if (nums[i] < nums[currentIndex]) {
                max = Math.max(max, findTheLis(i, nums, map));
            }
        }

        map.put(currentIndex, max + 1);
        return max + 1;
    }

//    You are climbing a staircase. It takes n steps to reach the top.
//
//    Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
//
//    Example 1:
//
//    Input: n = 2
//    Output: 2
//    Explanation: There are two ways to climb to the top.
//1. 1 step + 1 step
//2. 2 steps
    public static int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int prev1 = 1, prev2 = 2;
        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        return prev2;
    }

    // Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
    // A subsequence of a string is a new string that is formed from the original string by deleting
    // some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
    public static boolean isSubsequence(String s, String t) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        if (s.length() == 1 && t.contains(s)) {
            return true;
        }

        String currentString = s.substring(0, 1);
        int indexValueForT = t.indexOf(currentString) + 1;
        String substringOfT = indexValueForT >= t.length() ? "" : t.substring(indexValueForT);
        return t.contains(currentString) && isSubsequence(s.substring(1), substringOfT);
    }

    public static int minOperation(int n) {
        if (n == 0) {
            return 0;
        }

        if (isPowerOfTwo(n)) {
            return (int) (Math.log(n) / Math.log(2)) + 1;
        }

        if(n % 2 == 1) {
            return 1 + minOperation(n -1);
        }

        return minOperation(n/2) + 1;
    }

    public static boolean isPowerOfTwo(int num) {
        if (num <= 0) {
            return false;
        }
        double logBase2 = Math.log(num) / Math.log(2);
        return logBase2 == Math.floor(logBase2);
    }

    // There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]).
    // The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]).
    // The robot can only move either down or right at any point in time.
    //
    //Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.
    public static int uniquePaths(int m, int n) {
        if(m == 1 && n == 1) {
            return 1;
        }
        if(m <= 0 || n <= 0) {
            return 0;
        }

        return uniquePaths(m - 1, n) + uniquePaths(m, n - 1);
    }

    public static void main(String[] args) {
        System.out.println(uniquePaths(3, 7));
    }
}
