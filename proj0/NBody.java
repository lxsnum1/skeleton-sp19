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
        Body[] Planets = readBodies(filename);
        String bkground = "./images/starfield.jpg";

        StdDraw.setScale(-uniRadius, uniRadius);
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        double now = 0;
        while (now < T) {

            double[] xForces = new double[Planets.length];
            double[] yForces = new double[Planets.length];
            for (int i = 0; i < Planets.length; i++) {
                xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
            }

            for (int i = 0; i < Planets.length; i++) {
                yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
            }

            for (int i = 0; i < Planets.length; i++) {
                Planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, bkground);
            for (Body planet : Planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            now += dt;
        }

        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < Planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", Planets[i].xxPos, Planets[i].yyPos,
                    Planets[i].xxVel, Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
        }

    }
}