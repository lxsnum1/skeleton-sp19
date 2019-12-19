import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(" "));
        assertEquals(false, palindrome.isPalindrome("ab"));
        assertTrue(palindrome.isPalindrome("mom"));
        assertTrue(palindrome.isPalindrome("121"));
        assertFalse(palindrome.isPalindrome("46521dfs16a46"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("word", obo));
    }

    @Test
    public void testIsPalindromeOffByN() {
        OffByN ob3 = new OffByN(3);
        assertTrue(palindrome.isPalindrome("word", ob3));
    }
}

// Uncomment this class once you've created your Palindrome class.