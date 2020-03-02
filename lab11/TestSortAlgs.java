import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Lxs
 */
public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> q1 = new Queue();
        q1.enqueue(10);
        q1.dequeue();
        q1.enqueue(5);
        q1.enqueue(4);
        q1.enqueue(3);
        q1.enqueue(0);
        q1.enqueue(1);
        q1.enqueue(2);

        for (int i = 10000; i > 0; i--) {
            q1.enqueue((int) Math.random() * 1000);
        }

        assertFalse(isSorted(q1));
        assertTrue(isSorted(QuickSort.quickSort(q1)));
    }

    @Test
    public void testMergeSort() {
        Queue<String> q2 = new Queue();

        q2.enqueue("Joe");
        q2.enqueue("Nancy");
        q2.enqueue("Caroline");
        q2.enqueue("Jeffery");
        q2.enqueue("Tom");
        q2.enqueue("Ava");
        q2.enqueue("Peter");
        q2.enqueue("Josh");
        q2.enqueue("Hug");

        assertFalse(isSorted(q2));
        assertTrue(isSorted(MergeSort.mergeSort(q2)));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items A Queue of items
     * @return true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
