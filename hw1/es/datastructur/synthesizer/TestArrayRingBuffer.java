package es.datastructur.synthesizer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> x = new ArrayRingBuffer<>(3);
        // 33.1 null null null
        x.enqueue(33.1);
        // 33.1 44.8 null null
        x.enqueue(44.8);
        // 33.1 44.8 62.3 null
        x.enqueue(62.3);
        System.out.println(x.isFull());
        System.out.println(x.dequeue());
        System.out.println(x.isFull());
        // 33.1 44.8 62.3 -3.4
        x.enqueue(-3.4);
        // 44.8 62.3 -3.4 null (returns 33.1)
        double a = x.dequeue();
        x.enqueue(3.0);
        x.enqueue(4.0);
    }
}
