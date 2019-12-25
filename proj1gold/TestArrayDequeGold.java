import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TestArrayDequeGold
 */
public class TestArrayDequeGold {

    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ans = new ArrayDequeSolution<>();

        int length = StdRandom.uniform(100);
        for (int i = 0; i < length; i++) {
            int rand = StdRandom.uniform(10);
            sad.addLast(rand);
            ans.addLast(rand);
        }
        assertEquals(ans, sad);
    }
}