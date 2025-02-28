package org.example;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class DynamicProgramming {

    // Find the longest subsequence in array. Return its length
    public static int lengthOfLIS(int[] nums) {
        //start from the last index (more subsequences before = more chances to find the longest lis)
        //find the lis of sequences which ended with the number smaller than current number
        //find max of it and + 1
        //go next

        if(nums.length == 1) {
            return 1;
        }

        Map<Integer, Integer> map = new HashMap<>();
        int currentIndex = nums.length - 1;

        int theLis = findTheLis(currentIndex, nums, map);
        map.put(currentIndex, theLis);
        for (int i = 0; i < nums.length; i++) {
            if(!map.containsKey(i)) {
                findTheLis(i, nums, map);
            }
        }
        return map.entrySet().stream().map(Map.Entry::getValue).max(Integer::compareTo).get();
    }

    private static int findTheLis(int currentIndex, int[] nums, Map<Integer, Integer> map) {
        if(currentIndex == 0) {
            return 1;
        }

        if(map.containsKey(currentIndex)){
            return map.get(currentIndex);
        }

        int max = 0;
        for (int i = currentIndex; i >= 0; i--) {
            if(nums[i] < nums[currentIndex]) {
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
        if (n == 1) return 1;
        if (n == 2) return 2;

        int prev1 = 1, prev2 = 2;
        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev1 = prev2;
            prev2 = current;
        }
        return prev2;
    }

    public static boolean isSubsequence(String s, String t) {
        if(s == null || s.isEmpty()) {
            return true;
        }

        if(s.length() == 1 && t.contains(s)) {
            return true;
        }

        String currentString = s.substring(0, 1);
        int indexValueForT = t.indexOf(currentString) + 1;
        String substringOfT = indexValueForT >= t.length() ? "" : t.substring(indexValueForT);
        return t.contains(currentString) && isSubsequence(s.substring(1), substringOfT);
    }

    public static void main(String[] args) {
        out.println(isSubsequence("aaaaaa", "bbaaaa"));
    }
}
