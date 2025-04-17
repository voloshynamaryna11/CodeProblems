package org.example;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("123");
        strings.add("13");
        strings.add("12");
        strings.add("1");
        strings.add("23");
        strings.add("3");
        System.out.println(getConcatenation2FirstStringsWithEventLength(strings));
    }
    static String getConcatenation2FirstStringsWithEventLength(List<String> strings) {
        return strings.stream()
            .filter(str -> str.length() % 2 != 0)
            .limit(2)
            .collect(Collectors.joining());
    }

    static Map<Integer, List<String>> getGroupedWordsByTheirLength(List<String> strings) {
        return strings.stream()
            .collect(Collectors.groupingBy(String::length));
    }

    static Map<String, Double> getTotalOrderAmountByCustomer(List<Order> orders) {
        return orders.stream()
                     .collect(Collectors.groupingBy(o -> o.customerId, Collectors.summingDouble(o -> o.orderAmount)));
    }

    static List<String> getTopNFrequentItems(List<String> strings) {
        return strings.stream()
            .collect(Collectors.groupingBy(str -> str, Collectors.counting()))
            .entrySet()
            .stream()
                      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Getter
    class Order {
        private String orderId;
        private String customerId;
        private double orderAmount;
        // getters, setters, constructor
    }
}
