import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * BSTMap
 *
 * @author Lxs
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    /**
     * root of BST
     */
    private Node root;

    /**
     * Node of BST
     */
    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        private Node(K k, V v, int n) {
            key = k;
            val = v;
            size = n;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BSTMap() {
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        // root.size = 0; //unnecessary
        root = null;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key.
     *
     * @return {@code true}
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (key == null) {
            throw new IllegalArgumentException("can't call get() with a null key");
        }
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        }
        if (cmp > 0) {
            return get(x.right, key);
        }
        return x.val;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * return number of key-value pairs in BST rooted at x
     */
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (value == null) {
            // delete(key);
            return;
        }
        root = put(root, key, value);
        // assert check();
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            return new Node(key, value, 1);
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.val = value;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        Set<K> BSTSet = new HashSet<>();
        for (int i = 0; i < size(); i++) {
            BSTSet.add(select(root, i).key);
        }
        return BSTSet;
    }

    /**
     * Removes the mapping for the specified key from this map if present. Not
     * required for Lab 8. If you don't implement this, thr  ow an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        // throw new UnsupportedOperationException("can't remove", null);
        if (!containsKey(key)) {
            return null;
        }
        V toRemove = get(key);
        root = remove(root, key);
        check();
        return toRemove;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to the
     * specified value. Not required for Lab 8. If you don't implement this, throw
     * an UnsupportedOperationException.
     * if (value == null), then don't check value.
     */
    @Override
    public V remove(K key, V value) {
        // throw new UnsupportedOperationException("can't remove", null);
        if (!containsKey(key)) {
            return null;
        }
        if (!get(key).equals(value)) {
            return null;
        }
        root = remove(root, key);
        check();
        return value;
    }

    /* Return the tree which has the node with specific key been removed. */
    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            // If both left and right nodes are not null, then replace this node
            // with the smallest node in the right node (or with the largest
            // node in the left node, same, but does not implement here).
            Node temp = x;
            x = min(temp.right);
            x.right = deleteMin(temp.right);
            x.left = temp.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    /* Return the tree which has the node with min key been removed. */
    private Node deleteMin(Node x) {
        if (x.left == null) {
            // If x.left is null, meaning that x is the min, so replace it with
            // x.right, whether x.right is null or not does not matter.
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Node select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }
        return select(root, k);
    }

    /**
     * Return key of rank k.
     */
    private Node select(Node x, int k) {
        if (x == null) {
            return null;
        }
        int sz = size(x.left);
        if (k < sz) {
            return select(x.left, k);
        } else if (k > sz) {
            return select(x.right, k - sz - 1);
        } else {
            return x;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<K> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < size();
        }

        @Override
        public K next() {
            Node curr = select(i);
            i++;
            return curr.key;
        }
    }

    /*************************************************************************
     *  Check integrity of BST data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) {
            System.out.println("Not in symmetric order");
        }
        if (!isSizeConsistent()) {
            System.out.println("Subtree counts not consistent");
        }
        /* if (!isRankConsistent())
        	System.out.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent(); */
        return isBST() && isSizeConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree
    // is strict since order
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, K min, K max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.key.compareTo(min) < 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) < 0) {
            return false;
        }
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    /**
     * are the size fields correct?
     */
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) {
            return true;
        }
        if (x.size != size(x.left) + size(x.right) + 1) {
            return false;
        }
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    public void printInOrder() {
        printNode(root);
    }

    private void printNode(Node x) {
        if (x == null) {
            return;
        }
        printNode(x.left);
        System.out.println("(" + x.key + ": " + x.val + ")");
        printNode(x.right);
    }
}