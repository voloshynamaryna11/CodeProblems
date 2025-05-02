package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

            if (incrementedColumns >= mat.length || incrementedRaws >= mat[0].length) {
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

            if (zeroOccurs) {
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

    public static int numIslands(char[][] grid) {
        int numOfIslands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    grid[i][j] = 0;
                    numOfIslands++;
                    // check right neighbors
                    checkNeighbor(grid, i, j + 1);
                    // check down neighbors
                    checkNeighbor(grid, i + 1, j);
                }
            }
        }
        return numOfIslands;
    }

    private static void checkNeighbor(char[][] grid, int i, int j) {
        if (grid[i][j] == '1') {
            grid[i][j] = 0;
            // check right neighbors
            if (j + 1 < grid[0].length) {
                checkNeighbor(grid, i, j + 1);
            }
            // check down neighbors
            if (i + 1 < grid.length) {
                checkNeighbor(grid, i + 1, j);
            }
            // check left neighbors
            if (j - 1 > 0) {
                checkNeighbor(grid, i, j - 1);
            }
            // check down neighbors
            if (i - 1 > 0) {
                checkNeighbor(grid, i - 1, j);
            }
        }
    }

    // The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
    //
    //P   A   H   N
    //A P L S I I G
    //Y   I   R
    //And then read line by line: "PAHNAPLSIIGYIR"
    //
    //Write the code that will take a string and make this conversion given a number of rows:
    //
    //string convert(string s, int numRows);
    public static String convert(String s, int numRows) {
        if (numRows <= 1) {
            return s;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String[] letters = s.split("");
        List<Integer> listOfDifferences1 = new ArrayList<>();
        List<Integer> listOfDifferences2 = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            calculateRaw(i, letters, numRows, stringBuilder, listOfDifferences1, listOfDifferences2);
        }

        return stringBuilder.toString();
    }

    static void calculateRaw(int startingLetterIndex, String[] letters, int numberOfRaws, StringBuilder changedString,
                             List<Integer> listOfDifferences1, List<Integer> listOfDifferences2) {
        if (startingLetterIndex == 0 || startingLetterIndex == numberOfRaws - 1) {
            int differenceBetween2Indexes = numberOfRaws + (numberOfRaws - 3);
            listOfDifferences1.add(differenceBetween2Indexes);
            int currentSearchIndex = startingLetterIndex;
            while (currentSearchIndex < letters.length) {
                changedString.append(letters[currentSearchIndex]);
                currentSearchIndex += differenceBetween2Indexes + 1;
            }
            return;
        }
        int differenceBetween2Indexes1 = listOfDifferences1.get(listOfDifferences1.size() - 1) - 2;
        int differenceBetween2Indexes2 = listOfDifferences2.isEmpty() ? 1 : listOfDifferences2.get(listOfDifferences2.size() - 1) + 2;

        int currentSearchIndex = startingLetterIndex;
        boolean isUsedFirstDifference = false;
        while (currentSearchIndex < letters.length) {
            changedString.append(letters[currentSearchIndex]);
            if (!isUsedFirstDifference) {
                currentSearchIndex += differenceBetween2Indexes1 + 1;
            } else {
                currentSearchIndex += differenceBetween2Indexes2 + 1;
            }
            isUsedFirstDifference = !isUsedFirstDifference;
        }
        listOfDifferences1.add(differenceBetween2Indexes1);
        listOfDifferences2.add(differenceBetween2Indexes2);
    }

    // Given N x N matrix filled with 0, 1, 2, 3.
    //
    //Find whether there is a path possible from source to destination, traversing through blank cells only.
    //
    //You can traverse up, down, right, and left. Return a single integer 1 if a path exists, otherwise 0.
    //
    //A value of cell 1 means Source.
    //A value of cell 2 means Destination.
    //A value of cell 3 means Blank cell.
    //A value of cell 0 means Blank Wall.
    //Note: there are an only a single source and single destination(sink).

    public static int checkPath(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] == 1) {
                    if (isPossibleToReachDestination(A, i, j, new ArrayList<>())) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    private static boolean isPossibleToReachDestination(int[][] A, int currentI, int currentJ, List<Pair> visitedCells) {
        visitedCells.add(new Pair(currentI, currentJ));
        boolean leftCellIsPresent = currentJ - 1 >= 0;
        boolean rightCellIsPresent = currentJ + 1 < A[0].length;
        boolean upCellIsPresent = currentI - 1 >= 0;
        boolean downCellIsPresent = currentI + 1 < A.length;

        Pair leftCell = new Pair(currentI, currentJ - 1);
        Pair rightCell = new Pair(currentI, currentJ + 1);
        Pair upCell = new Pair(currentI - 1, currentJ);
        Pair downCell = new Pair(currentI + 1, currentJ);

        if ((rightCellIsPresent && A[rightCell.raw][rightCell.column] == 2)
            || (leftCellIsPresent && A[leftCell.raw][leftCell.column] == 2)
            || (downCellIsPresent && A[downCell.raw][downCell.column] == 2)
            || (upCellIsPresent && A[upCell.raw][upCell.column] == 2)) {
            return true;
        }

        if (rightCellIsPresent && A[rightCell.raw][rightCell.column] == 3 && !visitedCells.contains(rightCell)) {
            if (isPossibleToReachDestination(A, currentI, currentJ + 1, visitedCells)) {
                return true;
            }
            return isPossibleToReachDestination(A, currentI, currentJ, visitedCells);
        }
        if (leftCellIsPresent && A[leftCell.raw][leftCell.column] == 3 && !visitedCells.contains(leftCell)) {
            if (isPossibleToReachDestination(A, currentI, currentJ - 1, visitedCells)) {
                return true;
            }
            return isPossibleToReachDestination(A, currentI, currentJ, visitedCells);
        }
        if (downCellIsPresent && A[downCell.raw][downCell.column] == 3 && !visitedCells.contains(downCell)) {
            if (isPossibleToReachDestination(A, currentI + 1, currentJ, visitedCells)) {
                return true;
            }
            return isPossibleToReachDestination(A, currentI, currentJ, visitedCells);
        }
        if (upCellIsPresent && A[upCell.raw][upCell.column] == 3 && !visitedCells.contains(upCell)) {
            if (isPossibleToReachDestination(A, currentI - 1, currentJ, visitedCells)) {
                return true;
            }
            return isPossibleToReachDestination(A, currentI, currentJ, visitedCells);
        }

        return false;
    }

    static class Pair {
        int raw;
        int column;

        public Pair(int raw, int column) {
            this.raw = raw;
            this.column = column;
        }

        public int getRaw() {
            return raw;
        }

        public int getColumn() {
            return column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pair pair)) {
                return false;
            }
            return raw == pair.raw && column == pair.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(raw, column);
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {3, 3, 0, 0},
            {0, 3, 2, 0},
            {1, 3, 0, 0},
            {3, 3, 3, 3}
        };

        System.out.println(checkPath(matrix));
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
