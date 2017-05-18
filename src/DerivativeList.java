import java.util.ArrayList;
import java.util.List;


public class DerivativeList {

	public ArrayList<Point> derivates;
	
	public DerivativeList(ArrayList<Point> dir) {
		this.derivates = dir;
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < derivates.size(); i++) {
			Point p = derivates.get(i);
			s += "(" + p.x + ", " + p.y + "), ";
		}
		return s + " \n ";
	}
	
	public static void main(String[] args) {
		Point p1 = new Point(0,0);
		Point p2 = new Point(5,5);
		
		Point q1 = new Point(1,3);
		Point q2 = new Point(4,0);
		System.out.println(Display.intersect(p2, p1, q2, q1));
	}
	
	
}
