/**
 * NBody
 */
public class NBody {

    /**
     * Return the radius of the universe reading from the file
     */
    public static double readRadius(String planetsFileName) {
        In in = new In(planetsFileName);
        in.readInt();
        double galaxyRadius = in.readDouble();
        return galaxyRadius;
    }

    /**
     * Return an array of Bodys corresponding to the bodies in the file
     */
    public static Body[] readBodies(String planetsFileName) {
        In in = new In(planetsFileName);
        int num = in.readInt();
        in.readDouble();
        Body[] Planets = new Body[num];

        for (int i = 0; i < num; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            Planets[i] = new Body(xP, yP, xV, yV, m, img);
        }
        return Planets;
    }

    /**
     * Drawing the Initial Universe State
     */
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];
        double uniRadius = readRadius(filename);
        Body[] universe = readBodies(filename);

        String bkground = "./images/starfield.jpg";
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-uniRadius, uniRadius);
        StdDraw.clear();
        StdDraw.picture(0, 0, bkground);
        StdDraw.show();
        StdDraw.pause(1000);

        for (Body planet : universe) {
            planet.draw();
        }

    }
}