
public class Point {

	public double x, y;
	public boolean isAprx;
	
	public Point(double x, double y) {
		this(x, y, false);
	}
	
	public Point(double x, double y, boolean isAprx) {
		this.x = x;
		this.y = y;
		this.isAprx = isAprx;
	}
	
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
