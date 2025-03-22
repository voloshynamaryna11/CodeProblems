package org.example;

public class MyHashMap<T, K> {

    private final double LOAD_FACTOR = 0.75;
    private int actualSize;
    Node<T, K>[] buckets;


    public MyHashMap() {
        actualSize = 0;
        buckets = new Node[16];
    }

    public void put(T key, K value) {
        if (checkForResizeNeeded()) {
            resize();
        }

        int hashCode = hash(key);
        int index = getIndex(hashCode);
        Node<T, K> node = buckets[index];

        if (node == null) {
            buckets[index] = new Node<>(key, value, null);
            actualSize++;
            return;
        }

        while (node.next != null) {
            if (key.equals(node.key)) {
                node.value = value;
                return;
            }

            node = node.next;
        }

        node.next = new Node<>(key, value, null);
    }

    public K get(T key) {
        int hashCode = hash(key);
        int index = getIndex(hashCode);
        Node<T, K> node = buckets[index];

        while (node != null) {
            if (key.equals(node.key)) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    public void remove(T key) {
        int hashCode = hash(key);
        int index = getIndex(hashCode);

        Node<T, K> node = buckets[index];
        Node<T, K> previous = null;
        while (node != null) {
            if (key.equals(node.key)) {
                if (previous == null) {
                    buckets[index] = node.next;
                    if(node.next == null) {
                        actualSize--;
                    }
                    return;
                } else {
                    previous.next = node.next;
                    return;
                }
            }
            previous = node;
            node = node.next;
        }
    }

    private boolean checkForResizeNeeded() {
        return ((double) actualSize/this.buckets.length) >= LOAD_FACTOR;
    }

    private void resize() {
       Node<T,K>[] bucketsCopy = this.buckets;
       this.buckets = new Node[this.buckets.length * 2];
       this.actualSize = 0;

        for (int i = 0; i < bucketsCopy.length; i++) {
            Node<T, K> bucket = bucketsCopy[i];
            while(bucket != null) {
                put(bucket.key, bucket.value);
                bucket = bucket.next;
            }
        }
    }

    private int getIndex(int hash) {
        return hash & (this.buckets.length - 1);
    }

    final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private static class Node<T, K> {
        T key;
        K value;
        Node<T, K> next;

        public Node(T key, K value, Node<T, K> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
