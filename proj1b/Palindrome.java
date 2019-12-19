/**
 * Palindrome
 */
public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeHelper(deque);
    }

    private boolean isPalindromeHelper(Deque<Character> word) {
        if (word.size() == 1 || word.size() == 0) {
            return true;
        }

        if (word.removeFirst() == word.removeLast() && isPalindromeHelper(word)) {
            return true;
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeHelper(deque, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> word, CharacterComparator cc) {
        if (word.size() == 1 || word.size() == 0) {
            return true;
        }

        if (cc.equalChars(word.removeFirst(), word.removeLast()) && isPalindromeHelper(word)) {
            return true;
        }
        return false;
    }
}