package org.example;

public class InplaceReversalOfLinkedList {

    public static void main(String[] args) {
//        ListNode node7 = new ListNode(7);
//        ListNode node6 = new ListNode(6, node7);
//        ListNode node5 = new ListNode(5, node6);
//        ListNode node4 = new ListNode(4, node5);
//        ListNode node3 = new ListNode(3, node4);
//        ListNode node2 = new ListNode(2, node3);
//        ListNode node = new ListNode(1, node2);
//
//        ListNode reverseLinkedList = reverseBetween1(node, 1, 7);
//
//        while (reverseLinkedList != null) {
//            System.out.println(reverseLinkedList.toString());
//            reverseLinkedList = reverseLinkedList.next;
//        }

        ListNode node2 = new ListNode(2);
        ListNode node1 = new ListNode(1, node2);

        removeNthFromEnd(node1, 1);
    }

    //Example of how to reverse LinkedList
    static ListNode reverseLinkedList(ListNode head) {
        if (head.next == null) {
            return head;
        }

        ListNode currentNode = head;
        ListNode tempNode = null;
        ListNode previousNode = null;
        while (currentNode != null) {
            tempNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = tempNode;
        }

        return previousNode;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        ListNode createNode(int val, ListNode node) {
            node.val = val;
            return node;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
        }
    }

    public static ListNode reverseBetween1(ListNode head, int left, int right) {
        int headSize = 0;
        ListNode firstPart = head;
        int i = 0;

        while (firstPart != null && i < (left - 2)) {
            firstPart = firstPart.next;
            i++;
        }

        ListNode lastPart = firstPart;
        int copyI = i;
        while (copyI < right) {
            lastPart = lastPart.next;
            copyI++;
        }

        if (left != 1 && right != headSize) {
            ListNode currentMiddle = firstPart.next;
            ListNode previous = lastPart;
            ListNode temp = null;
            while (i < right - 1) {
                temp = currentMiddle.next;
                currentMiddle.next = previous;
                previous = currentMiddle;
                currentMiddle = temp;
                i++;
            }

            firstPart.next = previous;
            return head;
        } else if (left == 1) {
            ListNode currentMiddle = firstPart;
            ListNode previous = lastPart;
            ListNode temp = null;
            while (i < right) {
                temp = currentMiddle.next;
                currentMiddle.next = previous;
                previous = currentMiddle;
                currentMiddle = temp;
                i++;
            }

            return previous;
        }

        return head;
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode current = head;
        int i = 0;

        while (current != null && i < (left - 2)) {
            current = current.next;
            i++;
        }

        while (i < right - 1) {
            ListNode temp = current.next;
            temp.val = 0;
            current.next = temp;
            current = current.next;
            i++;
        }

        return head;
    }


    // Given the head of a linked list, remove the nth node from the end of the list and return its head
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fastHead = head;
        ListNode slowNode = head;

        int currentNumberOfIteration = 0;
        int totalSize = 0;

        while (fastHead.next != null) {
            fastHead = fastHead.next;
            currentNumberOfIteration++;
            totalSize++;
            if (currentNumberOfIteration > n) {
                slowNode = slowNode.next;
            }
        }

        ListNode nodeForDeletion = slowNode.next;

        if (nodeForDeletion == null && n==1) {
            head = null;
            return head;
        }

        if(n == totalSize) {
            return head.next;
        }

        slowNode.next = nodeForDeletion.next;
        return head;
    }
}
