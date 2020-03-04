package bearmaps.hw4;

import java.util.List;

/**
 * Interface for shortest path solvers. Created by hug.
 */
public interface ShortestPathsSolver<Vertex> {

    /**
     * SOLVED if the AStarSolver was able to complete all work in the time given.
     * UNSOLVABLE if the priority queue became empty. TIMEOUT if the solver ran out
     * of time. You should check to see if you have run out of time every time you
     * dequeue.
     */
    SolverOutcome outcome();

    /**
     * A list of vertices corresponding to a solution. Should be empty if result was
     * TIMEOUT or UNSOLVABLE.
     */
    List<Vertex> solution();

    /**
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE
     */
    double solutionWeight();

    /** The total number of priority queue dequeue operations. */
    int numStatesExplored();

    /** The total time spent in seconds by the constructor. */
    double explorationTime();
}
