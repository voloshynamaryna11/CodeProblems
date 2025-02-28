package org.example;

public class HashMap {

    //Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
    public static int[] twoSum(int[] nums, int target) {
        java.util.HashMap<Integer, Integer> integerIntegerHashMap = new java.util.HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (integerIntegerHashMap.containsKey(diff)) {
                return new int[]{i, integerIntegerHashMap.get(diff)};
            }

            integerIntegerHashMap.put(nums[i], i);
        }

        return new int[2];
    }

    public static void main(String[] args) {
        int[] arr = {3, 3, 3, 4};

        twoSum(arr, 9);
    }
}
