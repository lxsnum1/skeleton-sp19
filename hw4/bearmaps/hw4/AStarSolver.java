package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * AStarSolver
 *
 * @author Lxs
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private double explorationTime;
    private int numStatesExplored;
    private double solutionWeight;
    private SolverOutcome solverOutcome;
    private LinkedList<Vertex> solution;

    private ExtrinsicMinPQ<Vertex> fringe;
    private Map<Vertex, Double> distToStart;
    private Map<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

        Stopwatch timer = new Stopwatch();
        numStatesExplored = 0;
        solution = new LinkedList<>();
        fringe = new ArrayHeapMinPQ<>();
        distToStart = new HashMap<>();
        edgeTo = new HashMap<>();
        distToStart.put(start, 0.0);
        fringe.add(start, input.estimatedDistanceToGoal(start, end));

        while (fringe.size() > 0) {
            Vertex v = fringe.removeSmallest();
            numStatesExplored = numStatesExplored + 1;
            explorationTime = timer.elapsedTime();

            if (v.equals(end)) {
                solverOutcome = SolverOutcome.SOLVED;
                solutionWeight = distToStart(end);

                for (Vertex currVertex = end; !currVertex.equals(start); ) {
                    solution.addFirst(currVertex);
                    currVertex = edgeTo.get(currVertex);
                }
                solution.addFirst(start);
                return;
            }

            if (explorationTime > timeout) {
                solverOutcome = SolverOutcome.TIMEOUT;
                solution = new LinkedList<>();
                solutionWeight = 0;
                return;
            }

            for (WeightedEdge<Vertex> edge : input.neighbors(v)) {
                relax(edge, input, end);
            }
        }

        solverOutcome = SolverOutcome.UNSOLVABLE;
        solution = new LinkedList<>();
        solutionWeight = 0;
        explorationTime = timer.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> input, Vertex goal) {
        Vertex from = e.from();
        Vertex to = e.to();
        double potentialDist = distToStart(from) + e.weight();
        if (potentialDist < distToStart(to)) {
            setDistToStart(to, potentialDist);
            edgeTo.put(to, from);

            if (fringe.contains(to)) {
                fringe.changePriority(to, distToStart(to) + input.estimatedDistanceToGoal(to, goal));
            } else {
                fringe.add(to, distToStart(to) + input.estimatedDistanceToGoal(to, goal));
            }
        }
    }

    private double distToStart(Vertex v) {
        if (distToStart.containsKey(v)) {
            return distToStart.get(v);
        }
        return Double.POSITIVE_INFINITY;
    }

    private void setDistToStart(Vertex v, double dist) {
        distToStart.put(v, dist);
    }

    @Override
    public SolverOutcome outcome() {
        return solverOutcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}