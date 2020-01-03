package es.datastructur.synthesizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO: Make sure to that this class and all of its methods are public

public class ArrayRingBuffer<T> implements BoundQueue<T> {
    /**
     * Index for the next dequeue or peek.
     */
    private int first;
    /**
     * Index for the next enqueue.
     */
    private int last;
    /**
     * Variable for the fillCount.
     */
    private int fillCount;
    /**
     * Array for storing the buffer data.
     */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * index change by add one circular
     */
    private int plusOne(int index) {
        return (index + 1) % rb.length;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then throw new
     * RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = plusOne(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then throw
     * new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T head = rb[first];
        rb[first] = null;
        first = plusOne(first);
        fillCount -= 1;
        return head;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then throw
     * new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    // TODO: When you get to part 4, implement the needed code to support
    // iteration and equals.

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int currPos;

        private ArrayRingBufferIterator() {
            currPos = first;
        }

        @Override
        public boolean hasNext() {
            return currPos != last;
        }

        @Override
        public T next() {
            T currItem = rb[currPos];
            currPos = plusOne(currPos);
            return currItem;
        }
    }

    // @Override
    // public String toString() {
    //     StringBuilder returnSB = new StringBuilder("{");
    //     for (int i = first; i != last; i = plusOne(i)) {
    //         returnSB.append(rb[i].toString());
    //         returnSB.append(", ");
    //     }
    //     returnSB.append(rb[last]);
    //     returnSB.append("}");
    //     return returnSB.toString();
    // }

    @Override
    public String toString() {
        List<String> listOfItems = new ArrayList<>();
        for (T x : this) {
            listOfItems.add(x.toString());
        }
        return "{" + String.join(", ", listOfItems) + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if (this.fillCount() != o.fillCount) {
            return false;
        }
        for (int i = first; i != last; i = plusOne(i)) {
            if (rb[i] != o.rb[i]) {
                return false;
            }
        }
        return true;
    }
}
