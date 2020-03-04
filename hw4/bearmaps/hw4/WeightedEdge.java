package bearmaps.hw4;

/**
 * Utility class that represents a weighted edge. Created by hug.
 */
public class WeightedEdge<Vertex> {

    /** The source of this edge. */
    private Vertex v;

    /** The destination of this edge. */
    private Vertex w;

    /** The weight of this edge. */
    private double weight;

    public WeightedEdge(Vertex v, Vertex w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public Vertex from() {
        return v;
    }

    public Vertex to() {
        return w;
    }

    public double weight() {
        return weight;
    }
}
