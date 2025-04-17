package org.example;

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
}
