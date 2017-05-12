import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
	
	public static double f(double x) {
		return x*x*x;
	}

	public static void main(String[] args) {
		
		ArrayList<Point> originalFunc = new ArrayList<Point>();
		for(float i = 0; i < 5; i+=.3) {
			
			originalFunc.add(new Point(i,f(i)));
		}
		System.out.println("Added " + originalFunc.size() + " points");
		//originalFunc.add(p4);
		DerivativeList fOfX = new DerivativeList(originalFunc);
		DerivativeList[] derivatives = new DerivativeList[fOfX.derivates.size()+1];
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

		float totalStep = 0;
		float stepSize = .5f;
		float maxStep = .5f;
		System.out.println("Stepping by a total of " + maxStep +  " with " + stepSize + " increments");
		while(totalStep <= maxStep) {
			totalStep += stepSize;
			step(derivatives, stepSize);
		}
//			for(int i =0; i < derivatives.length; i++) {
//				System.out.print(derivatives[i]);
//				for(int j = 0; j <= i; j++) {
//					System.out.print("            ");
//				}
//			}
		Point pred = derivatives[0].derivates.get(derivatives[0].derivates.size()-1);
		System.out.println("Predicted " + pred);
		System.out.println("Actual value: " + f(pred.x));
		System.out.println("Percent error: " + ((pred.y - f(pred.x))/f(pred.x)*100 + "%"));
	}
	
	public static void step(DerivativeList[] ders, double stepSize) {
		for(int i = ders.length - 2; i >= 0; i--) {
			ArrayList<Point> currentFunc = ders[i].derivates;
			ArrayList<Point> der = ders[i+1].derivates;
			double x = currentFunc.get(currentFunc.size()-1).x + stepSize;
			double y = currentFunc.get(currentFunc.size()-1).y + stepSize *  der.get(der.size()-1).y;
			currentFunc.add(new Point(x, y));
			//ders[i].derivates.add(paramE)
		}
	}
	
}
