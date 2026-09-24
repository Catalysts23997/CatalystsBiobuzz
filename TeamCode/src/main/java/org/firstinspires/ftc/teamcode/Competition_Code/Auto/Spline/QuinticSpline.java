package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline;


public class QuinticSpline {
    private final double a0, a1, a2, a3, a4, a5; // X coefficients
    private final double b0, b1, b2, b3, b4, b5; // Y coefficients

    // Upgraded constructor for continuous chaining
    public QuinticSpline(double x0, double y0, double dx0, double dy0, double ddx0, double ddy0,
                           double x1, double y1, double dx1, double dy1, double ddx1, double ddy1) {
        // Solve for X coefficients using the exact injected derivatives
        a0 = x0;
        a1 = dx0;
        a2 = 0.5 * ddx0;
        a3 = -10*x0 + 10*x1 - 6*dx0 - 4*dx1 - 1.5*ddx0 + 0.5*ddx1;
        a4 = 15*x0 - 15*x1 + 8*dx0 + 7*dx1 + 1.5*ddx0 - ddx1;
        a5 = -6*x0 + 6*x1 - 3*dx0 - 3*dx1 - 0.5*ddx0 + 0.5*ddx1;

        // Solve for Y coefficients identically...
        b0 = y0;
        b1 = dy0;
        b2 = 0.5 * ddy0;
        b3 = -10*y0 + 10*y1 - 6*dy0 - 4*dy1 - 1.5*ddy0 + 0.5*ddy1;
        b4 = 15*y0 - 15*y1 + 8*dy0 + 7*dy1 + 1.5*ddy0 - ddy1;
        b5 = -6*y0 + 6*y1 - 3*dy0 - 3*dy1 - 0.5*ddy0 + 0.5*ddy1;
    }


    public double[] getPosition(double t) {
        double t2 = t * t, t3 = t2 * t, t4 = t3 * t, t5 = t4 * t;
        double x = a5*t5 + a4*t4 + a3*t3 + a2*t2 + a1*t + a0;
        double y = b5*t5 + b4*t4 + b3*t3 + b2*t2 + b1*t + b0;
        return new double[]{x, y};
    }

    public double[] getVelocity(double t) {
        double t2 = t * t, t3 = t2 * t, t4 = t3 * t;
        double dx = 5*a5*t4 + 4*a4*t3 + 3*a3*t2 + 2*a2*t + a1;
        double dy = 5*b5*t4 + 4*b4*t3 + 3*b3*t2 + 2*b2*t + b1;
        return new double[]{dx, dy};
    }

    public double[] getAcceleration(double t) {
        double t2 = t * t, t3 = t2 * t;
        double ddx = 20*a5*t3 + 12*a4*t2 + 6*a3*t + 2*a2;
        double ddy = 20*b5*t3 + 12*b4*t2 + 6*b3*t + 2*b2;
        return new double[]{ddx, ddy};
    }

    public double getCurvature(double t) {
        double[] vel = getVelocity(t);
        double[] accel = getAcceleration(t);
        double num = Math.abs(vel[0] * accel[1] - vel[1] * accel[0]);
        double denom = Math.pow(vel[0] * vel[0] + vel[1] * vel[1], 1.5);
        return (denom < 1e-6) ? 0.0 : num / denom;
    }
}
