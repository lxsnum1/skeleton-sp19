package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * ArrayHeapMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<Node> heap;
    private HashMap<T, Integer> itemMapIndex;

    private class Node implements Comparable<Node> {
        T item;
        double priority;

        Node(T i, double p) {
            this.item = i;
            this.priority = p;
        }

        double setP(double p) {
            double oldP = this.priority;
            this.priority = p;
            return oldP;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.priority, other.priority);
        }
    }

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        itemMapIndex = new HashMap<>();
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        Node tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
        itemMapIndex.put(heap.get(i).item, i);
        itemMapIndex.put(heap.get(j).item, j);
    }

    private void swim(int s) {
        if (s == 0) {
            return;
        }

        int p = parent(s);
        // min PQ
        if (heap.get(p).compareTo(heap.get(s)) > 0) {
            swap(p, s);
            swim(p);
        }
    }

    private void sink(int p) {
        while (leftChild(p) < size()) {
            int smallest = leftChild(p);
            if (rightChild(p) < size() && less(rightChild(p), smallest)) {
                smallest = rightChild(p);
            }

            if (less(p, smallest)) {
                break;
            }
            swap(p, smallest);
            p = smallest;
        }
    }

    private boolean less(int i, int j) {
        return heap.get(i).compareTo(heap.get(j)) < 0;
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present. You may assume that
     * item is never null.
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }

        heap.add(new Node(item, priority));
        itemMapIndex.put(item, heap.size() - 1);
        swim(heap.size() - 1);
    }

    /**
     * Returns true if the PQ contains the given item.
     */
    @Override
    public boolean contains(T item) {
        return itemMapIndex.containsKey(item);
    }

    /**
     * Returns the minimum item. Throws NoSuchElementException if the PQ is empty.
     */
    @Override
    public T getSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0).item;
    }

    /**
     * Removes and returns the minimum item. Throws NoSuchElementException if the PQ
     * is empty.
     */
    @Override
    public T removeSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        swap(0, size() - 1);
        T min = heap.remove(size() - 1).item;
        itemMapIndex.remove(min);
        sink(0);
        return min;
    }

    /**
     * Returns the number of items in the PQ.
     */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Changes the priority of the given item. Throws NoSuchElementException if the
     * item doesn't exist.
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }

        int i = itemMapIndex.get(item);
        if (priority > heap.get(i).setP(priority)) {
            sink(i);
        } else {
            swim(i);
        }
    }
}