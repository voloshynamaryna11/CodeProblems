package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matrix {

    // You are given a 0-indexed integer array arr, and an m x n integer matrix mat. arr and mat both contain all the integers in the range [1, m * n].
    //
    //Go through each index i in arr starting from index 0 and paint the cell in mat containing the integer arr[i].
    //
    //Return the smallest index i at which either a row or a column will be completely painted in mat.
    public static int firstCompleteIndex(int[] arr, int[][] mat) {
        Map<Integer, Integer> countRawsMap = new HashMap<>();
        Map<Integer, Integer> countColumnsMap = new HashMap<>();
        Map<Integer, List<Integer>> indexesMap = new HashMap<>();


        for (int i = 0; i < mat.length; i++) {
            countRawsMap.put(i, 0);
            for (int j = 0; j < mat[0].length; j++) {
              indexesMap.put(mat[i][j], List.of(i, j));
              countColumnsMap.put(j, 0);
            }
        }

        for (int i = 0; i < arr.length; i++) {
            List<Integer> integers = indexesMap.get(arr[i]);
            Integer rawIndex = integers.get(0);
            Integer columnIndex = integers.get(1);

            int incrementedColumns = countColumnsMap.get(columnIndex) + 1;
            int incrementedRaws = countRawsMap.get(rawIndex) + 1;

            if(incrementedColumns >= mat.length || incrementedRaws >= mat[0].length) {
                return i;
            }

            countRawsMap.put(rawIndex, incrementedRaws);
            countColumnsMap.put(columnIndex, incrementedColumns);
        }

        return 0;
    }

    // Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.
    //
    //You must do it in place.
    public static void setZeroes(int[][] matrix) {
        List<Integer> column = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            List<Integer> raw = new ArrayList<>();
            boolean zeroOccurs = false;
            for (int j = 0; j < matrix[i].length; j++) {
                raw.add(matrix[i][j]);
                if (matrix[i][j] == 0) {
                    column.add(j);
                    raw.add(0);
                    zeroOccurs = true;
                }
            }

            if(zeroOccurs) {
                raw = raw.stream().map(integer -> {
                    return 0;
                }).collect(Collectors.toList());
            }

            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = raw.get(j);
            }
        }

        column.stream().forEach(j -> {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][j] = 0;
            }
        });
    }

    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3},
                       {4, 5, 6},
                       {0, 1, 1},
                       {4, 0, 5}};
        setZeroes(arr);
        printMatrix(arr);

        String collect = Stream.of("1", "address")
                               .filter(Objects::nonNull)
                               .collect(Collectors.joining(" "));

        System.out.println(collect);
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println(); // Move to the next line after printing a row
        }
    }
}
