package org.example;

import java.util.ArrayList;
import java.util.List;

public class BackTracing {
    public static void main(String[] args) {
//        ArrayList<Integer> integers = new ArrayList<>();
//        integers.addAll(List.of(1, 2, 3));
//
//        printNestedList(permute(integers));

        System.out.println(combine(6, 3));
    }

    public static List<List<Integer>> permute(List<Integer> A) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] isPresent = new boolean[A.size()];
        backTrace(result, new ArrayList<>(), isPresent, A);
        return result;
    }

    static void backTrace(List<List<Integer>> result, List<Integer> currentList, boolean[] isPresentArray, List<Integer> originalArray) {
        if (currentList.size() == originalArray.size()) {
            result.add(new ArrayList<>(currentList));
            return;
        }

        for (int i = 0; i < originalArray.size(); i++) {
            if (!isPresentArray[i]) {
                currentList.add(originalArray.get(i));
                isPresentArray[i] = true;

                backTrace(result, currentList, isPresentArray, originalArray);

                currentList.remove(currentList.size() - 1);
                isPresentArray[i] = false;
            }
        }
    }

    public static ArrayList<ArrayList<Integer>> combine(int A, int B) {
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();
        boolean[] isPresent = new boolean[A];
        backTraceForSubSet(results, new ArrayList<>(), isPresent, A, B);
        return results;
    }

    static void backTraceForSubSet(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> currentList, boolean[] isPresentArray, int n,
                                   int k) {
        if (currentList.size() == k) {
            result.add(new ArrayList<>(currentList));
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!isPresentArray[i] && currentList.size() < k && (currentList.size() == 0 || currentList.get(currentList.size() - 1) < i + 1)) {
                currentList.add(i + 1);
                isPresentArray[i] = true;

                backTraceForSubSet(result, currentList, isPresentArray, n, k);

                currentList.remove(currentList.size() - 1);
                isPresentArray[i] = false;
            }
        }
    }

    public static void printNestedList(List<List<Integer>> nestedList) {
        System.out.println("[");
        for (List<Integer> innerList : nestedList) {
            System.out.print("  [");
            for (int i = 0; i < innerList.size(); i++) {
                System.out.print(innerList.get(i));
                if (i < innerList.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("],");
        }
        System.out.println("]");
    }
}
