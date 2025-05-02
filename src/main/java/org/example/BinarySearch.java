package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearch {
    public static int[] searchRange(final int[] A, int B) {
        int start = 0;
        int end = A.length - 1;

        int bIndex = findB(start, end, A, B, new ArrayList<>());
        if(A[bIndex] != B) {
            return new int[]{-1, -1};
        }
        int startB = bIndex;
        int endB = bIndex;
        while(startB - 1 >= start && A[startB - 1] == B) {
            startB--;
        }

        while(endB + 1 <= end && A[endB + 1] == B) {
            endB++;
        }

        return new int[]{startB, endB};
    }

    static int findB(int start, int end, int[] A, int B, List<Integer> usedMid) {
        int mid = start + (end - start) / 2;
        if(usedMid.contains(mid)) {
            return 0;
        }

        if (A[mid] < B) {
            usedMid.add(mid);
            return mid == end ? 0 : findB(mid, end, A, B, usedMid);
        } else if (A[mid] > B) {
            usedMid.add(mid);
            return mid == start ? 0 : findB(start, mid, A, B, usedMid);
        } else {
            return mid;
        }
    }

    public static void main(String[] args) {
        int[] A = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 10};
        int B = 4;
        Arrays.stream(searchRange(A, B))
            .forEach(System.out::println);
    }
}
