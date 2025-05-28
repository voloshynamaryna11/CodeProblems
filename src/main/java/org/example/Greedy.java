package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Greedy {
    public int majorityElement(final List<Integer> A) {
        double majorityOccurrenceTimes = Math.floor(A.size() / 2);
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < A.size(); i++) {
            frequencyMap.merge(A.get(i), 1, (vOld, vNew) -> vOld + 1);
            if(frequencyMap.get(A.get(i)) > majorityOccurrenceTimes) {
                return A.get(i);
            }
        }

        return 0;
    }

    public int maxp3(List<Integer> A) {
        if(A.size() < 3) {
            return 0;
        }

        List<Integer> sortedList = A.stream()
           .sorted(Comparator.reverseOrder())
           .limit(3)
            .collect(Collectors.toList());

        int multiplicationOf3HighestValues = sortedList.stream().reduce(1, (a, b) -> a * b).intValue();
        Integer highestPositive = sortedList.stream().limit(1).findFirst().get();
        int multiplicationWithNegative = highestPositive * find2MaxNegative(A).get().intValue();
        return Math.max(multiplicationOf3HighestValues, multiplicationWithNegative);
    }

    Optional<Integer> find2MaxNegative(List<Integer> integers) {
        return integers
            .stream()
            .filter(i -> i < 0)
            .sorted(Integer::compareTo).limit(2)
            .reduce((a, b) -> a * b);
    }

    public static int mice(List<Integer> A, List<Integer> B) {
        int result = 0;
        List<Integer> copyB = new ArrayList<>(B);
        for (int i =0; i < A.size(); i++) {
            Integer integer = A.get(i);
            Integer theClosestInteger = findTheClosestInteger(integer, copyB);
            result = Math.max(result, Math.abs(integer - theClosestInteger));
            copyB.remove(theClosestInteger);
        }

        return result;
    }

    private static int findTheClosestInteger(Integer integer, List<Integer> holes) {
        int result = holes.get(0);
        for (int i = 0; i < holes.size(); i++) {
            if(Math.abs(holes.get(i) - integer) < Math.abs(result - integer)) {
                result = holes.get(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> integers = List.of(4, -4, 2);
        List<Integer> integers1 = List.of(4, 0, 5);

        System.out.println(mice(integers, integers1));
    }
}
