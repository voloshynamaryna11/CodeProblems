package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BackTracing {
    public static void main(String[] args) {
//        ArrayList<Integer> integers = new ArrayList<>();
//        integers.addAll(List.of(1, 2, 3));
//
//        printNestedList(permute(integers));

        char[][] array = {{'A', 'B', 'C', 'E'},
                          {'S', 'F', 'E', 'S'},
                          {'A', 'D', 'E', 'E'}};
        String str = "ABCESEEEFS";
        System.out.println(exist(array, str));
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
            if (!isPresentArray[i] && currentList.size() < k && (currentList.size() == 0 || currentList.get(
                currentList.size() - 1) < i + 1)) {
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

    // Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
    //
    //
    //
    //Example 1:
    //
    //Input: n = 3
    //Output: ["((()))","(()())","(())()","()(())","()()()"]
    //Example 2:
    //
    //Input: n = 1
    //Output: ["()"]
    public static List<String> generateParenthesis(int n) {
        List<String> strings = new ArrayList<>();
        generateCombinations(strings, n, 0, 0, new StringBuilder());
        return strings;
    }

    private static void generateCombinations(List<String> combinations, int n, int amountOfOpenBraces, int amountOfCloseBraces,
                                             StringBuilder currentCombination) {
        if (currentCombination.length() == 2 * n) {
            combinations.add(currentCombination.toString());
        }

        if (amountOfOpenBraces < n) {
            currentCombination.append("(");
            generateCombinations(combinations, n, amountOfOpenBraces + 1, amountOfCloseBraces, currentCombination);
            currentCombination.deleteCharAt(currentCombination.length() - 1);
        }

        if (amountOfCloseBraces < amountOfOpenBraces) {
            currentCombination.append(")");
            generateCombinations(combinations, n, amountOfOpenBraces, amountOfCloseBraces + 1, currentCombination);
            currentCombination.deleteCharAt(currentCombination.length() - 1);
        }
    }

    public static boolean exist(char[][] board, String word) {
        char[] letters = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                List<Pair> currentPath = new ArrayList<>();
                char firstLetterOfWord = letters[0];
                if (firstLetterOfWord == board[i][j]) {
                    Pair currentPair = new Pair(i, j);
                    currentPath.add(currentPair);
                    if (findWord(letters, board, currentPath, 1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    static boolean findWord(char[] letters, char[][] board, List<Pair> currentPath, int positionOfNextTargetLetter) {
        if (positionOfNextTargetLetter >= letters.length) {
            return currentPath.size() == letters.length;
        }

        Pair currentCell = currentPath.get(currentPath.size() - 1);
        List<Pair> neighborsWhichConsistTargetLetter = findNeighborsWhichConsistTargetLetter(board, letters[positionOfNextTargetLetter],
                                                                                             currentCell);
        neighborsWhichConsistTargetLetter.removeAll(currentPath);

        for (int i = 0; i < neighborsWhichConsistTargetLetter.size(); i++) {
            currentPath.add(neighborsWhichConsistTargetLetter.get(0));
            positionOfNextTargetLetter++;
            boolean result = findWord(letters, board, currentPath, positionOfNextTargetLetter);
            if (result) {
                return result;
            }
        }

        currentPath.remove(currentPath.size() - 1);
        positionOfNextTargetLetter--;
        return false;
    }

    static List<Pair> findNeighborsWhichConsistTargetLetter(char[][] board, char targetedLetter, Pair currentBoardCell) {
        List<Pair> suitableNeighbors = new ArrayList<>();

        int i = board.length;
        int j = board[0].length;

        int currentI = currentBoardCell.index1;
        int currentJ = currentBoardCell.index2;

        if ((currentI + 1 < i && board[currentI + 1][currentJ] == targetedLetter)) {
            suitableNeighbors.add(new Pair(currentI + 1, currentJ));
        }

        if (currentI - 1 >= 0 && board[currentI - 1][currentJ] == targetedLetter) {
            suitableNeighbors.add(new Pair(currentI - 1, currentJ));
        }

        if ((currentJ + 1 < j && board[currentI][currentJ + 1] == targetedLetter)) {
            suitableNeighbors.add(new Pair(currentI, currentJ + 1));
        }

        if ((currentJ - 1 >= 0 && board[currentI][currentJ - 1] == targetedLetter)) {
            suitableNeighbors.add(new Pair(currentI, currentJ - 1));
        }

        return suitableNeighbors;
    }

    static class Pair {
        int index1;
        int index2;

        public Pair(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }

        public int getIndex1() {
            return index1;
        }

        public int getIndex2() {
            return index2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pair pair)) {
                return false;
            }
            return index1 == pair.index1 && index2 == pair.index2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index1, index2);
        }
    }

}
