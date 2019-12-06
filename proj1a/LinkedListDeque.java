/**
 * double-ended queue using generalized data type
 *
 * @author Lxs
 */
public class LinkedListDeque<NodeType> {
    /**
     * The first item (if it exists) in the deque is the sentinel.next
     * The last item (if it exists) in the deque is the sentinel.prev
     */
    private DequeNode sentinel;
    private int size;

    private class DequeNode {
        private NodeType item;
        private DequeNode prev;
        private DequeNode next;

        DequeNode() {
        }

        DequeNode(NodeType item, DequeNode p, DequeNode n) {
            item = item;
            prev = p;
            next = n;
        }
    }

    /**
     * Create an empty deque
     */
    public LinkedListDeque() {
        size = 0;
        sentinel = new DequeNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }


    public void addFirst(NodeType item) {
        size += 1;
        DequeNode newNode = new DequeNode(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
    }

    public void addLast(NodeType item) {
        size += 1;
        DequeNode newNode = new DequeNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
    }

    public NodeType removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        NodeType toRemove = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return toRemove;
    }

    public NodeType removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        NodeType toRemove = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return toRemove;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        DequeNode toPrint = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(toPrint.item + " ");
            toPrint = toPrint.next;
        }
        System.out.print('\n');
    }


    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     */
    public NodeType get(int index) {
        DequeNode toGet = sentinel.next;
        for (int i = 0; i < index; i++) {
            toGet = toGet.next;
        }
        return toGet.item;
    }


    /**
     * Same as get, but uses recursion
     * First, need a private helper method
     */
    public NodeType getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }

    private NodeType getRecursive(int index, DequeNode curr) {
        if (index == 0) {
            return curr.item;
        }
        return getRecursive(index - 1, curr.next);
    }

    /**
     * Creates a deep copy of other
     */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new DequeNode();
        sentinel.next = sentinel;
        sentinel.next = sentinel;

        for (int i = 0; i < other.size(); i++) {
            addLast((NodeType) other.get(i));
        }

    }
}
