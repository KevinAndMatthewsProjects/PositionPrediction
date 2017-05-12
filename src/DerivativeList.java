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
	
}
