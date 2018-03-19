package priv.shen.TextCheck;

public class Point {
    public int x;
    public double y;
    public Point(int x,double y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
