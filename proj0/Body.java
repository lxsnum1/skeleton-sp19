/**
 * Body
 */
public class Body {

    public double xxPos; // Its current x positon
    public double yyPos; // Its current y positon
    public double xxVel; // Its current velocity in the x direction
    public double yyVel; // Its current velocity in the y direction
    public double mass; // Its mass
    public String imgFileName; // The name of the file that corresponds to the image that depicts the body

    private static final double gravConst = 6.67e-11; // gravitational constant

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Body b) {
        return gravConst * this.mass * b.mass / (Math.pow(calcDistance(b), 2));
    }

    public double calcForceExertedByX(Body b) {
        return calcForceExertedBy(b) * (b.xxPos - xxPos) / calcDistance(b);
    }

    public double calcForceExertedByY(Body b) {
        return calcForceExertedBy(b) * (b.yyPos - this.yyPos) / calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] b) {
        double netForceX = 0.0;
        for (int i = 0; i < b.length; i++) {
            if (!equals(b[i])) {
                netForceX += calcForceExertedByX(b[i]);
            }
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Body[] b) {
        double netForceY = 0.0;
        for (Body someBody : b) {
            if (!this.equals(someBody)) {
                netForceY += calcForceExertedByY(someBody);
            }
        }
        return netForceY;
    }

    public void update(double dt, double xForce, double yForce) {
        xxVel += xForce / mass * dt;
        xxPos += xxVel * dt;
        yyVel += yForce / mass * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
    }
}
