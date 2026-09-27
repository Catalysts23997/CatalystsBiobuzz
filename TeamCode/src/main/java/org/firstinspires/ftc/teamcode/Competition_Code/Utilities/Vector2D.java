package org.firstinspires.ftc.teamcode.Competition_Code.Utilities;

public class Vector2D {
    public final double x;
    public final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Returns a new Vector2D representing (this + other) */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /** Returns a new Vector2D representing (this - other) */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /** Scales the vector by a constant factor */
    public Vector2D scale(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /** Calculates the Euclidean length (magnitude) ||v|| */
    public double magnitude() {
        return Math.hypot(x, y);
    }

    /** Returns a unit vector (length of 1) in the same direction */
    public Vector2D normalize() {
        double mag = magnitude();
        if (mag == 0) return new Vector2D(0, 0);
        return scale(1.0 / mag);
    }

    /** Computes the dot product: v1 · v2 */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /** Computes 2D cross product magnitude (v1.x * v2.y - v1.y * v2.x) */
    public double cross(Vector2D other) {
        return this.x * other.y - this.y * other.x;
    }

    /** Rotates the vector counter-clockwise by an angle in radians */
    public Vector2D rotate(double angleRad) {
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);
        return new Vector2D(x * cos - y * sin, x * sin + y * cos);
    }
}