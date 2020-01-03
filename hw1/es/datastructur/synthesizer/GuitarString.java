package es.datastructur.synthesizer;
//Note: This file will not compile until you complete task 1 (BoundedQueue).

/**
 * @author Lxs
 */
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final means the
     * values cannot be changed at runtime.
     */

    /**
     * Sampling Rate
     */
    private static final int SR = 44100;
    /**
     * energy decay factor
     */
    private static final double DECAY = .996;

    /**
     * Buffer for storing sound data.
     */
    private BoundQueue<Double> buffer;

    /**
     * Create a guitar string of the given frequency.
     */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<>(capacity);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0.0);
        }
    }

    /**
     * Pluck the guitar string by replacing the buffer with white noise.
     */
    public void pluck() {
        // Dequeue everything in buffer, and replace with random numbers
        // between -0.5 and 0.5. You can get such a number by using:
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.dequeue();
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
        }
    }

    /**
     * Advance the simulation one time step by performing one iteration of the
     * Karplus-Strong algorithm.
     */
    public void tic() {
        double front = buffer.dequeue();
        double back = (front + buffer.peek()) * 0.5 * DECAY;
        buffer.enqueue(back);
        // Do not call StdAudio.play().
    }

    /**
     * Return the double at the front of the buffer.
     */
    public double sample() {
        return buffer.peek();
    }
}
