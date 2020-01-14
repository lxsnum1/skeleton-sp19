import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * MyHashMap
 *
 * @author Lxs
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INIT_SIZE = 16;
    private static final double INIT_LOAD_FACTOR = 0.75;
    private double loadFactor;
    private BucketEntity<K, V>[] buckets;
    private Set<K> keySet;

    private static class BucketEntity<K, V> {
        K key;
        V val;
        BucketEntity<K, V> next;

        BucketEntity(K key, V value, BucketEntity<K, V> next) {
            this.key = key;
            this.val = value;
            this.next = next;
        }

        V get(K key) {
            for (BucketEntity<K, V> x = this; x != null; x = x.next) {
                if (key.equals(x.key)) {
                    return x.val;
                }
            }
            return null;
        }
    }

    public MyHashMap() {
        this(INIT_SIZE);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, INIT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = new BucketEntity[initialCapacity];
        this.loadFactor = loadFactor;
        keySet = new HashSet<>();
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        buckets = new BucketEntity[INIT_SIZE];
        keySet = new HashSet<>();
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("can't call containsKey() whit key = null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        BucketEntity<K, V> entity = buckets[hash(key)];
        return entity == null ? null : entity.get(key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return keySet.size();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (value == null) {
            remove(key);
            return;
        }

        int index = hash(key);
        BucketEntity<K, V> tmp = buckets[index];
        while (tmp != null) {
            if (tmp.key.equals(key)) {
                tmp.val = value;
                return;
            }
            tmp = tmp.next;
        }
        BucketEntity<K, V> newFront = new BucketEntity<>(key, value, buckets[index]);
        buckets[index] = newFront;
        keySet.add(key);

        if (keySet.size() > buckets.length * loadFactor) {
            resize(buckets.length * 2);
        }
    }

    private void resize(int capacity) {
        BucketEntity<K, V>[] tmp = new BucketEntity[capacity];
        for (BucketEntity<K, V> entity : buckets) {
            while (entity != null) {
                int j = (entity.key.hashCode() & 0x7fffffff) % capacity;
                BucketEntity<K, V> newFront = new BucketEntity<>(entity.key, entity.val, tmp[j]);
                tmp[j] = newFront;
                entity = entity.next;
            }
            buckets = tmp;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (keySet.remove(key)) {
            int index = hash(key);
            BucketEntity<K, V> head = buckets[index];
            if (key.equals(head.key)) {
                BucketEntity<K, V> newHead = head.next;
                buckets[index] = newHead;
                return head.val;
            }
            while (!head.next.key.equals(key)) {
                head = head.next;
            }
            BucketEntity<K, V> toRemove = head.next;
            head.next = toRemove.next;
            return toRemove.val;
        } else {
            return null;
        }
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        }
        return null;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }
}