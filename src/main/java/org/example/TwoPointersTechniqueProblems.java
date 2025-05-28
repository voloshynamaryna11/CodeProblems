package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TwoPointersTechniqueProblems {

    // Given a sorted array arr[] and a number target, find a pair in an array whose sum is closest to target.
    //
    //Note: Return the pair in sorted order and if there are multiple such pairs return the pair with maximum absolute difference. If no such pair exists return an empty array.
    public static int[] findClosestSumToTarget(int[] input, int target) {
        int start = 0;
        int end = input.length - 1;
        int closestSum = 0;
        int[] resultArray = {};

        while (start < end) {
            int currentSum = input[start] + input[end];
            if (findDiff(currentSum, target) < findDiff(closestSum, target)) {
                closestSum = currentSum;
                resultArray = new int[]{input[start], input[end]};
                start++;
            } else if (currentSum > closestSum) {
                end--;
            } else if (currentSum < closestSum) {
                start++;
            }
        }
        return resultArray;
    }

    public static int findDiff(int currentSum, int targetSum) {
        return Math.abs(targetSum - currentSum);
    }

    //Given an array arr[], the task is to find all possible indices {i, j, k} of triplet {arr[i], arr[j], arr[k]} such that their sum is equal to zero
    //
    // and all indices in a triplet should be distinct (i != j, j != k, k != i). We need to return indices of a triplet in sorted order, i.e., i < j < k.
    public static List<List<Integer>> findTriplets(int[] arr) {
        Map<Integer, List<Integer>> sums = new HashMap<>();
        List<List<Integer>> finalList = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                List<Integer> currentListOfIndexes = new ArrayList<>();
                currentListOfIndexes.add(i);
                currentListOfIndexes.add(j);
                sums.put(arr[i] + arr[j], currentListOfIndexes);
            }
        }

        for (int i = 0; i < arr.length; i++) {
            int finalI = i;
            List<List<Integer>> currentCombinations = sums.entrySet().stream()
                                                          .filter(entry -> {
                                                              if (entry.getValue().stream().filter(index -> index == finalI).findFirst()
                                                                       .isPresent()) {
                                                                  return false;
                                                              }
                                                              return true;
                                                          }).filter(entry -> {
                    if (entry.getKey() + arr[finalI] == 0) {
                        entry.getValue().add(finalI);
                        return true;
                    }
                    return false;
                })
                                                          .map(Map.Entry::getValue)
                                                          .collect(Collectors.toList());
            finalList.addAll(currentCombinations);
        }

        Set<List<Integer>> sortedCollection = finalList.stream()
                                                       .map(list -> list.stream().sorted().toList())
                                                       .collect(Collectors.toSet());

        return sortedCollection.stream().collect(Collectors.toList());
    }

    //Given a string s, reverse only all the vowels in the string and return it.
    //
    //The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both lower and upper cases, more than once.
    // "IceCreAm" => "AceCreIm"
    public static String reverseVowels(String s) {
        String[] array = s.chars()
                          .mapToObj(c -> String.valueOf((char) c))
                          .toArray(String[]::new);

        int start = 0;
        int end = array.length - 1;

        while (start < end) {
            if (!isVowels(array[start]) && isVowels(array[end])) {
                start++;
            } else if (isVowels(array[start]) && !isVowels(array[end])) {
                end--;
            } else if (isVowels(array[start]) && isVowels(array[end])) {
                String temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                start++;
                end--;
            } else {
                start++;
                end--;
            }
        }

        return String.join("", array);
    }

    public static boolean isVowels(String letter) {
        String lowerCaseLetter = letter.toLowerCase();
        return lowerCaseLetter.equals("a") ||
            lowerCaseLetter.equals("e") ||
            lowerCaseLetter.equals("i") ||
            lowerCaseLetter.equals("o") ||
            lowerCaseLetter.equals("u");
    }

    //Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at most one cookie.
    //
    //Each child i has a greed factor g[i], which is the minimum size of a cookie that the child will be content with;
    // and each cookie j has a size s[j]. If s[j] >= g[i], we can assign the cookie j to the child i, and the child i will be content.
    // Your goal is to maximize the number of your content children and output the maximum number.
    public int findContentChildren(int[] g, int[] s) {
        List<Integer> listChild = Arrays.stream(g).boxed().sorted().toList();
        List<Integer> listCokie = Arrays.stream(s).boxed().sorted().toList();
        int sum = 0;

        int currentChild = 0;
        int currentCookie = 0;
        int cookiesEnd = listCokie.size();
        int childrenEnd = listChild.size();

        while (currentCookie < cookiesEnd && currentChild < childrenEnd) {
            Integer currentChildValue = listChild.get(currentChild);
            Integer currentCookieValue = listCokie.get(currentCookie);

            if (currentCookieValue >= currentChildValue) {
                sum++;
                currentChild++;
                currentCookie++;
            } else {
                currentCookie++;
            }
        }

        return sum;
    }

    public static int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        return nums1.length > nums2.length ? calculate(nums2, nums1) : calculate(nums1, nums2);
    }

    private static int[] calculate(int[] lessSizeArray, int[] biggerSizeArray) {
        ArrayList<Integer> list = new ArrayList<>();

        int lessIndex = 0;
        int biggerIndex = 0;

        while (lessIndex < lessSizeArray.length && biggerIndex < biggerSizeArray.length) {
            if (lessSizeArray[lessIndex] < biggerSizeArray[biggerIndex]) {
                lessIndex++;
            } else if (lessSizeArray[lessIndex] == biggerSizeArray[biggerIndex]) {
                list.add(lessSizeArray[lessIndex]);
                lessIndex++;
                biggerIndex++;
            } else {
                biggerIndex++;
            }
        }

        return list.stream().mapToInt(Integer::intValue).distinct().toArray();
    }

    public static List<Integer> partitionLabels(String s) {
        char[] chars = s.toCharArray();
        int start = 0;
        int end = chars.length - 1;
        ArrayList<Integer> partitionSizes = new ArrayList<>();
        int currentPartitionEnd = -1;

        while (start <= end) {
            if (chars[end] == chars[start]) {
                if (start <= currentPartitionEnd) {
                    Integer lastPartitionLength = partitionSizes.get(partitionSizes.size() - 1);
                    partitionSizes.remove(partitionSizes.size() - 1);

                    lastPartitionLength = lastPartitionLength + (end - currentPartitionEnd);
                    partitionSizes.add(lastPartitionLength);

                    currentPartitionEnd = end;
                    start++;
                    end = chars.length - 1;
                } else {
                    partitionSizes.add(end - start + 1);
                    currentPartitionEnd = end;
                    start++;
                    end = chars.length - 1;
                }
            } else if (start == end) {
                partitionSizes.add(1);
                start++;
                end = chars.length - 1;
            } else {
                if (end <= currentPartitionEnd) {
                    start++;
                    end = chars.length - 1;
                } else {
                    end--;
                }
            }
        }
        return partitionSizes;
    }

    // Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
    //
    // We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
    public static void sortColors(int[] nums) {
        int redEnd = 0;
        int whiteStart = 0;
        int whiteEnd = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                change2Values(nums, i, redEnd);
                redEnd++;
                whiteStart = redEnd;
                whiteEnd = (whiteEnd == 0) ? whiteStart : whiteEnd + 1;
            }

            if (nums[i] == 1) {
                change2Values(nums, i, whiteEnd);
                whiteEnd++;
            }
        }
    }

    private static void change2Values(int[] nums, int currentIndex, int indexForExchange) {
        int tempValueFromWhiteEnd = nums[indexForExchange];
        nums[indexForExchange] = nums[currentIndex];
        nums[currentIndex] = tempValueFromWhiteEnd;
    }

    public static void main(String[] args) {
        int[] nums = {0, 0, 1};
        sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }
}