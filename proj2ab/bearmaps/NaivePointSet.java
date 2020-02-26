package bearmaps;

import java.util.List;

/**
 * NaivePointSet
 *
 * @author Lxs
 */
public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {

        Point nearestPoint = points.get(0);
        Point destination = new Point(x, y);
        double shortestDist = Point.distance(nearestPoint, destination);

        for (Point p : points) {
            double currDist = Point.distance(p, destination);
            if (shortestDist > currDist) {
                nearestPoint = p;
                shortestDist = currDist;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3, 4.0);
        System.out.println(ret.getX() + " and " + ret.getY());
    }
}