package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * KDTree
 *
 * @author Lxs
 */
public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = true;
    Node root;

    private static class Node {

        Point point;
        boolean orientation;
        Node left;
        Node right;

        Node(Point p, boolean orient) {
            this.point = p;
            this.orientation = orient;
        }
    }

    public KDTree(List<Point> points) {
        points = new ArrayList<>(points);
        Collections.shuffle(points);
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    private Node add(Point p, Node n, boolean splitDim) {
        if (n == null) {
            return new Node(p, splitDim);
        }

        if (p.equals(n.point)) {
            return n;
        }

        int cmp = compare(p, n.point, n.orientation);
        if (cmp < 0) {
            n.left = add(p, n.left, !n.orientation);
        } else {
            n.right = add(p, n.right, !n.orientation);
        }
        return n;
    }

    private int compare(Point p1, Point p2, boolean splitDim) {
        if (splitDim == HORIZONTAL) {
            return Double.compare(p1.getX(), p2.getX());
        } else {
            return Double.compare(p1.getY(), p2.getY());
        }
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root.point);
    }

    private Point nearest(Node curr, Point goal, Point best) {
        if (curr == null) {
            return best;
        }

        if (Point.distance(curr.point, goal) < Point.distance(best, goal)) {
            best = curr.point;
        }

        Node goodSide, badSide;
        if (compare(goal, curr.point, curr.orientation) < 0) {
            goodSide = curr.left;
            badSide = curr.right;
        } else {
            goodSide = curr.right;
            badSide = curr.left;
        }

        best = nearest(goodSide, goal, best);
        if (isWorthLooking(curr, goal, best)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    private boolean isWorthLooking(Node n, Point goal, Point best) {
        if (n.orientation == HORIZONTAL) {
            return Math.pow(n.point.getX() - goal.getX(), 2) < Point.distance(best, goal);
        } else {
            return Math.pow(n.point.getY() - goal.getY(), 2) < Point.distance(best, goal);
        }
    }
}