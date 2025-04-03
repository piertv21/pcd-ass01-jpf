package pcd.ass01.common.boids;

public class V2d {

    private final double x;
    private final double y;

    public V2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public V2d sum(V2d v) {
        return new V2d(x + v.x,y + v.y);
    }

    public double abs() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public V2d getNormalized() {
        double module = (double) Math.sqrt(x * x + y * y);
        return new V2d(x / module,y / module);
    }

    public V2d mul(double fact) {
        return new V2d(x * fact,y * fact);
    }

    public String toString() {
        return "Velocity2D(" + x + "," + y + ")";
    }
}