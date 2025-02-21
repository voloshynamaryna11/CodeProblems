package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KWayMerge {

    // 2-WAY MERGE
    static List<Integer> merge2Lists(List<Integer> list1, List<Integer> list2) {
        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);
        ArrayList<Integer> resultList = new ArrayList<>();

        while (!list1.isEmpty() || !list2.isEmpty()) {
            if (list2.isEmpty() || (!list1.isEmpty() && list1.get(0) < list2.get(0))) {
                resultList.add(list1.get(0));
                list1.remove(0);
            } else {
                resultList.add(list2.get(0));
                list2.remove(0);
            }
        }

        return resultList;
    }

    // K-WAY MERGE
    static List<Integer> mergeKLists(List<List<Integer>> list) {
        list.forEach(listTemp -> listTemp.sort(Integer::compareTo));
        List<Integer> resultList = new ArrayList<>();

        Map<Integer, Integer> tempPool = new HashMap<>();

        //Add first entries from each array in our pool
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isEmpty()) {
                tempPool.put(i, list.get(i).get(0));
            }
        }

        while (!list.stream().allMatch(List::isEmpty)) {
            Optional<Map.Entry<Integer, Integer>> minElementInPool = tempPool.entrySet().stream()
                                                                             .min(new Comparator<Map.Entry<Integer, Integer>>() {
                                                                                 @Override
                                                                                 public int compare(Map.Entry<Integer, Integer> o1,
                                                                                                    Map.Entry<Integer, Integer> o2) {
                                                                                     return Integer.compare(o1.getValue(), o2.getValue());
                                                                                 }
                                                                             });

            Integer minElementInPoolValue = minElementInPool.get().getValue();
            Integer indexOfArrayWhichConsistsTheMinElement = minElementInPool.get().getKey();
            List<Integer> listWithMinElement = list.get(indexOfArrayWhichConsistsTheMinElement);

            resultList.add(minElementInPoolValue);
            tempPool.remove(indexOfArrayWhichConsistsTheMinElement);
            listWithMinElement.remove(0);
            if (!listWithMinElement.isEmpty()) {
                Integer nextElement = listWithMinElement.get(0);
                tempPool.put(indexOfArrayWhichConsistsTheMinElement, nextElement);
            }
        }

        return resultList;
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode resultListNodeCopy = null;
        ListNode resultListNode = null;

        Map<Integer, ListNode> tempPool = new HashMap<>();

        //Add first entries from each array in our pool
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                tempPool.put(i, lists[i]);
            }
        }

        while (!tempPool.isEmpty()) {
            Optional<Map.Entry<Integer, ListNode>> minElementInPool = tempPool.entrySet().stream()
                                                                             .min(new Comparator<Map.Entry<Integer, ListNode>>() {
                                                                                 @Override
                                                                                 public int compare(Map.Entry<Integer, ListNode> o1,
                                                                                                    Map.Entry<Integer, ListNode> o2) {
                                                                                     return Integer.compare(o1.getValue().val, o2.getValue().val);
                                                                                 }
                                                                             });

            Integer indexOfArrayWhichConsistsTheMinElement = minElementInPool.get().getKey();
            ListNode listNodeWithMinElement = minElementInPool.get().getValue();

            if (resultListNodeCopy == null) {
                resultListNodeCopy = new ListNode(listNodeWithMinElement.val);
                resultListNode = resultListNodeCopy;
            } else {
                resultListNodeCopy.next = new ListNode(listNodeWithMinElement.val);
                resultListNodeCopy = resultListNodeCopy.next;
            }

            tempPool.remove(indexOfArrayWhichConsistsTheMinElement);
            listNodeWithMinElement = listNodeWithMinElement.next;
            if (listNodeWithMinElement != null) {
                tempPool.put(indexOfArrayWhichConsistsTheMinElement, listNodeWithMinElement);
            }
        }

        return resultListNode;
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
    }

    public static void main(String[] args) {
        ListNode list1 = createListNode(new ArrayList<>(List.of(1)));
        ListNode list2 = createListNode(new ArrayList<>(List.of(3, 4, 78))); // Empty list
        ListNode list3 = createListNode(new ArrayList<>(List.of(2, 5, 9)));
        ListNode list4 = createListNode(new ArrayList<>(List.of(6, 7, 12)));

        // Store them in a list
        ListNode[] linkedLists = {};

        ListNode listNode = mergeKLists(linkedLists);

        printListNode(listNode);
    }

    private static ListNode createListNode(List<Integer> list) {
        if (list.isEmpty()) return null;

        ListNode head = new ListNode(list.get(0));
        ListNode current = head;

        for (int i = 1; i < list.size(); i++) {
            current.next = new ListNode(list.get(i));
            current = current.next;
        }

        return head;
    }

    private static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }
}
