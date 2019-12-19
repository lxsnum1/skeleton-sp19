/**
 * array deque based on circular array list
 *
 * @author Lxs
 */
public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    private static final int INIT_CAPACITY = 8;
    private static final int REFACTOR = 2;
    private static final int MIN_FACTOR = 4;

    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAPACITY];
        // initial nextLast - nextLast =1
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    public ArrayDeque(ArrayDeque<T> other) {
        new ArrayDeque<T>();
        for (int i = 0; i < other.size; i++) {
            addLast((T) other.get(i));
        }
    }

    /**
     * Get the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such item exists, returns null. Must not alter the deque.
     */
    @Override
    public void printDeque() {
        for (int i = plusOne(nextFirst); i != nextLast; i = plusOne(i)) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
    // public void printDeque() {
    // int toPrint = plusOne(nextFirst);
    // for (int i = 0; i < size; i++) {
    // System.out.print(items[toPrint] + " ");
    // toPrint = plusOne(toPrint);
    // }
    // System.out.print('\n');
    // }

    private boolean isFull() {
        return size == items.length;
    }

    private boolean isSparse() {
        return items.length > 16 && size < items.length / MIN_FACTOR;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * index change by add one circular
     */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /**
     * index change by minus one circular
     */
    private int minusOne(int index) {
        // unlike Python, in Java, the % symbol represents "remainder" rather than
        // "modulus",
        // therefore, it may give negative value, so + items.length is necessary,
        // or to use Math.floorMod(x, y)
        return (index - 1 + items.length) % items.length;
    }

    /**
     * resize the array: if full, capacity * 2; if sparse, capacity / 2
     */
    private void resize(int capacity) {
        T[] newDeque = (T[]) new Object[capacity];

        int oldFirst = plusOne(nextFirst);
        int oldLast = minusOne(nextLast);

        if (oldFirst > oldLast) {
            System.arraycopy(items, oldFirst, newDeque, 0, items.length - oldFirst);
            System.arraycopy(items, 0, newDeque, items.length - oldFirst, oldLast + 1);
        } else {
            System.arraycopy(items, oldFirst, newDeque, 0, size);
        }

        nextFirst = capacity - 1;
        nextLast = size;
        items = newDeque;
    }

    // private void resize(int capacity) {
    // T[] newDeque = (T[]) new Object[capacity];
    // // the index of the first item in original deque
    // int oldIndex = plusOne(nextFirst);
    // for (int newIndex = 0; newIndex < size; newIndex++) {
    // newDeque[newIndex] = items[oldIndex];
    // oldIndex = plusOne(oldIndex);
    // }
    // items = newDeque;
    // nextFirst = capacity - 1;
    // // since the new deque is starting from true 0 index.
    // nextLast = size;
    // }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize(items.length * REFACTOR);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize(items.length * REFACTOR);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        nextFirst = plusOne(nextFirst);
        T toRemove = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;

        if (isSparse()) {
            resize(items.length / REFACTOR);
        }
        return toRemove;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        nextLast = minusOne(nextLast);
        T toRemove = items[nextLast];
        items[nextLast] = null;
        size -= 1;

        if (isSparse()) {
            resize(items.length / REFACTOR);
        }
        return toRemove;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such item exists, returns null. Must not alter the deque
     * and must take constant time.
     */
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[plusOne(nextFirst + index)];
    }
}
