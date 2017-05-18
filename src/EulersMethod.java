import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class EulersMethod {

	
	private ArrayList<Point> dataPoints;
	private DerivativeList[] derivatives;
	
	public EulersMethod() {
		dataPoints = new ArrayList<Point>();
	}
	
	public DerivativeList getFunction() {
		return derivatives[0];
	}
	
	public void addPoint(Point p) {
		dataPoints.add(p);
	}
	
	public static double f(double x) {
		return x*x*x;
	}

	public void calculateDerivatives() {
		
		
		//originalFunc.add(p4);
		DerivativeList fOfX = new DerivativeList(dataPoints);
		derivatives = new DerivativeList[fOfX.derivates.size()+1];
		for(int i = 0; i < derivatives.length; i++) {
			derivatives[i] = new DerivativeList(new ArrayList<Point>());
		}
		derivatives[derivatives.length-1] = new DerivativeList(new ArrayList<Point>(Arrays.asList(new Point(0,0))));
		derivatives[0] = fOfX;
		for(int i = 0; i < derivatives.length-1; i++) {
			List<Point> list = derivatives[i].derivates;
			for(int j = 0; j < list.size() - 1; j++) {
	
				double x1 = list.get(j).x;
				double x2 = list.get(j+1).x;
				double y1 = list.get(j).y;
				double y2 = list.get(j+1).y;
				
				double derAprx = (y1 - y2)/(x1 - x2);
				double xAprx = (x1 + x2)/2;
				derivatives[i+1].derivates.add(new Point(xAprx, derAprx));
				
			}
		}
//		for(int i =0; i < derivatives.length; i++) {
//			System.out.print(derivatives[i]);
//			for(int j = 0; j <= i; j++) {
//				System.out.print("            ");
//			}
//		} 

//		float totalStep = 0;
//		float stepSize = .5f;
//		float maxStep = .5f;
//		System.out.println("Stepping by a total of " + maxStep +  " with " + stepSize + " increments");
//		while(totalStep <= maxStep) {
//			totalStep += stepSize;
//			step(derivatives, stepSize);
//		}
//			for(int i =0; i < derivatives.length; i++) {
//				System.out.print(derivatives[i]);
//				for(int j = 0; j <= i; j++) {
//					System.out.print("            ");
//				}
//			}
//		Point pred = derivatives[0].derivates.get(derivatives[0].derivates.size()-1);
//		System.out.println("Predicted " + pred);
//		System.out.println("Actual value: " + f(pred.x));
//		System.out.println("Percent error: " + ((pred.y - f(pred.x))/f(pred.x)*100 + "%"));
	}
	
	public void step(double stepSize) {
		for(int i = derivatives.length - 2; i >= 0; i--) {
			ArrayList<Point> currentFunc = derivatives[i].derivates;
			ArrayList<Point> der = derivatives[i+1].derivates;
			double x = currentFunc.get(currentFunc.size()-1).x + stepSize;
			double y = currentFunc.get(currentFunc.size()-1).y + stepSize *  der.get(der.size()-1).y;
			currentFunc.add(new Point(x, y, true));
			//ders[i].derivates.add(paramE)
		}
	}
	
	public void clearAprx() {
		for(DerivativeList d : derivatives) {
			Iterator<Point> iter = d.derivates.iterator();
			while(iter.hasNext()) {
				if(iter.next().isAprx) {
					iter.remove();
				}
			}
		}
	}
	
}
