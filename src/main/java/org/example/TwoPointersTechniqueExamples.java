package org.example;

public class TwoPointersTechniqueExamples {

    //Find sum of 2 elements of the array that will equal target value
    public boolean twoSum(int[] input, int targetValue) {
        int startPointer = 0;
        int endPointer = input.length - 1;

        while (startPointer < endPointer) {
            int currentSum = input[startPointer] + input[endPointer];
            if(input[startPointer] + input[endPointer] == targetValue) {
                return true;
            } else if (currentSum < targetValue) {
                startPointer++;
            } else if (currentSum > targetValue) {
                endPointer--;
            }
        }
        return false;
    }


    //Find value of the middle element of linkedList
    public <T> T findMiddle(MyNode<T> head) {
        MyNode<T> slowPointer = head;
        MyNode<T> fastPointer = head;

        while (fastPointer.nextNode != null && fastPointer.nextNode.nextNode != null) {
            fastPointer = fastPointer.nextNode.nextNode;
            slowPointer = slowPointer.nextNode;
        }

        return slowPointer.value;
    }

    private static class MyNode<T> {
        T value;
        MyNode<T> nextNode;

        public MyNode(T value, MyNode<T> nextNode) {
            this.value = value;
            this.nextNode = nextNode;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public MyNode<T> getNextNode() {
            return nextNode;
        }

        public void setNextNode(MyNode<T> nextNode) {
            this.nextNode = nextNode;
        }
    }
}
