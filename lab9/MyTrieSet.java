import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * MyTrieSet
 *
 * @author Lxs
 */
public class MyTrieSet implements TrieSet61B {

    private TrieNode root;

    private static class TrieNode {
        boolean isKey;
        Map<Character, TrieNode> children;

        TrieNode(boolean b) {
            this.isKey = b;
            children = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new TrieNode(false);
    }

    /**
     * Clears all items out of Trie
     */
    @Override
    public void clear() {
        root = new TrieNode(false);
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise
     */
    @Override
    public boolean contains(String key) {
        if (key == null || root == null) {
            return false;
        }

        TrieNode currNode = root;
        for (int i = 0; i < key.length(); i++) {
            currNode = currNode.children.get(key.charAt(i));
            if (currNode == null) {
                return false;
            }
        }

        return currNode.isKey;
    }

    /**
     * Inserts string KEY into Trie
     */
    @Override
    public void add(String key) {
        if (key == null) {
            return;
        }

        TrieNode currNode = root;
        for (int i = 0; i < key.length(); i++) {
            TrieNode newNode = new TrieNode(false);
            currNode = currNode.children.putIfAbsent(key.charAt(i), newNode);
            if (currNode == null) {
                currNode = newNode;
            }
        }
        currNode.isKey = true;
    }

    /**
     * Returns a list of all words that start with PREFIX
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            return null;
        }
        List<String> list = new LinkedList<>();
        TrieNode currNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            currNode = currNode.children.get(prefix.charAt(i));
            if (currNode == null) {
                return list;
            }
        }

        StringBuilder pre = new StringBuilder(prefix);
        collectString(currNode, list, pre);
        return list;
    }

    private void collectString(TrieNode n, List<String> x, StringBuilder pre) {
        if (n.isKey) {
            x.add(pre.toString());
        }

        for (char c : n.children.keySet()) {
            StringBuilder sb = new StringBuilder(pre);
            sb.append(c);
            collectString(n.children.get(c), x, sb);
        }
    }

    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        StringBuilder longestPrefix = new StringBuilder();
        TrieNode currNode = root;
        for (int i = 0; i < key.length(); i += 1) {
            char c = key.charAt(i);
            if (!currNode.children.containsKey(c)) {
                return longestPrefix.toString();
            } else {
                longestPrefix.append(c);
                currNode = currNode.children.get(c);
            }
        }
        return longestPrefix.toString();
    }

    public static void main(String[] args) {
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
    }
}