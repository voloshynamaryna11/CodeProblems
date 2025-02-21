package org.example;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SlidingWindowExamples {

    public static void main(String[] args) {
//        int[] array = {1, 3, -5, 0, 1, 3, 4};
//        int[] ints = max_sum_subsequence(array);
//
//        IntStream.of(ints).boxed()
//            .forEach(System.out::println);

        int[] array = {1, 12, -5, -6, 50, 3};
        System.out.println(findMaxAverage(array, 4));
    }

    //Given array of positive/negative numbers. Need to return subsequence of this array with the biggest sum (the length of array could be whatever)

    public static int[] max_sum_subsequence(int[] sequence) {
        int startIndex = 0;
        int length = 1;
        int sum = sequence[0];

        int bestStart = 0;
        int bestLength = 1;
        int maxSum = sum;

        for (int i = 1; i < sequence.length; i++) {
            if(sum < 0) {
                startIndex = i;
                length = 1;
                sum = sequence[i];
            } else {
                length++;
                sum += sequence[i];
            }

            if(sum > maxSum) {
                maxSum = sum;
                bestStart = startIndex;
                bestLength = length;
            }
        }
        return IntStream.of(sequence)
            .skip(bestStart)
            .limit(bestLength)
            .toArray();
    }

    //Для массива, состоящего из n целых чисел, найдите непрерывный подмассив заданной длины k, который имеет максимальное среднее значение. Нужно вывести максимальное среднее значение.

    public static double findMaxAverage(int[] array, int k) {
        int currentSum = IntStream.of(array).limit(k)
            .sum();

        int maxSum = currentSum;

        for (int i = k; i < array.length; i++) {
                currentSum = currentSum + array[i] - array[i - k];
            maxSum = Math.max(currentSum, maxSum);
        }

        return (double) maxSum / k;
    }

    //    You are given a
//    binary array
//    nums.
//
//    You can do the following operation on the array any number of times (possibly zero):
//
//    Choose any 3 consecutive elements from the array and flip all of them.
//    Flipping an element means changing its value from 0 to 1, and from 1 to 0.
//
//    Return the minimum number of operations required to make all elements in nums equal to 1. If it is impossible, return -1.
//
//
//
//    Example 1:
//
//    Input: nums = [0,1,1,1,0,0]
//
//    Output: 3
//
//    Explanation:
//    We can do the following operations:
//
//    Choose the elements at indices 0, 1 and 2. The resulting array is nums = [1,0,0,1,0,0].
//    Choose the elements at indices 1, 2 and 3. The resulting array is nums = [1,1,1,0,0,0].
//    Choose the elements at indices 3, 4 and 5. The resulting array is nums = [1,1,1,1,1,1].
//

    public static int minOperations(int[] nums) {
        int result = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0 && nums.length - i >= 3) {
                nums[i] = Math.abs(nums[i] - 1);
                nums[i+1] = Math.abs(nums[i+1] - 1);
                nums[i+2] = Math.abs(nums[i+2] - 1);
                result++;
            }
        }

        return Arrays.stream(nums).anyMatch(v -> v == 0) ? -1 : result;
    }
}
