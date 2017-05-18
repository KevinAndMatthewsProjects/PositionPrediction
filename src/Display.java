import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Display extends JApplet {

	private EulersMethod eX1;
	private EulersMethod eY1;
	private EulersMethod eX2;
	private EulersMethod eY2;
	private Random random;

	private long lastTime, startTime;
	private double totalTime;
	private double updateTime;

	private boolean isRunning = true;

	public void start() {
		totalTime = 0;
		isRunning = true;
	}

	public void stop() {
		isRunning = false;
	}

	public void init() {

		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		startTime = System.nanoTime();
		lastTime = System.nanoTime();
		random = new Random();
		eX1 = new EulersMethod();
		eY1 = new EulersMethod();
		eX1.addPoint(getPoint(0, true, false));
		eY1.addPoint(getPoint(0, false, false));
		eX1.calculateDerivatives();
		eY1.calculateDerivatives();

		eX2 = new EulersMethod();
		eY2 = new EulersMethod();
		eX2.addPoint(getPoint(0, true, true));
		eY2.addPoint(getPoint(0, false, true));
		eX2.calculateDerivatives();
		eY2.calculateDerivatives();
	}

	public void drawLine(int x1, int y1, int x2, int y2, Graphics g) {
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x1, y1-1, x2, y2-1);
		g.drawLine(x1, y1+1, x2, y2+1);
		g.drawLine(x1, y1-2, x2, y2-2);
		g.drawLine(x1, y1+2, x2, y2+2);
	}
	
	public void paint(Graphics g) {
		int centerX = this.getWidth()/2;
		int centerY = this.getHeight()/2;
		if (isRunning) {
			startTime = System.nanoTime();
			totalTime += (startTime - lastTime) / 1000000000d;
			updateTime += (startTime - lastTime) / 1000000000d;
		}
		if (updateTime > 2) {
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			System.out.println("Updating");
			// updateTime = 0;
			eX1.clearAprx();
			eY1.clearAprx();
			Point XPoint1 = getPoint(totalTime, true, false);
			Point YPoint1 = getPoint(totalTime, false, false);
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Located plane at (" + (int) (XPoint1.y-centerX)+ ", "
					+ (int) (this.getHeight()-(YPoint1.y+centerY)) + ")", 10, 20);
			eX1.addPoint(XPoint1);
			eY1.addPoint(YPoint1);
			eX1.calculateDerivatives();
			eY1.calculateDerivatives();

			eX2.clearAprx();
			eY2.clearAprx();
			Point XPoint2 = getPoint(totalTime, true, true);
			Point YPoint2 = getPoint(totalTime, false, true);

			g.drawString("Located plane at (" + (int) (XPoint2.y-centerX) + ", "
					+ (int) (this.getHeight()-(YPoint2.y+centerY)) + ")", this.getWidth() - 400, 20);
			eX2.addPoint(XPoint2);
			eY2.addPoint(YPoint2);
			eX2.calculateDerivatives();
			eY2.calculateDerivatives();

			ArrayList<Point> X1 = eX1.getFunction().derivates;
			ArrayList<Point> Y1 = eY1.getFunction().derivates;

			ArrayList<Point> X2 = eX2.getFunction().derivates;
			ArrayList<Point> Y2 = eY2.getFunction().derivates;
			// System.out.println(Y);
			int i;
			g.setColor(Color.BLACK);
			int r = 20;
			g.fillOval(centerX-r/2, centerY-r/2, r, r);
			
			
			for(int x = 0; x < this.getWidth(); x+= this.getWidth()/10) {
				g.drawLine(x, 0, x, this.getHeight());
			}
			for(int y = 0; y < this.getHeight(); y+= this.getHeight()/10) {
				g.drawLine(0, y, this.getWidth(), y);
			}
			
			for (i = 1; i < X1.size(); i++) {
				g.setColor(Color.black);
				drawLine((int) X1.get(i - 1).y, (int) Y1.get(i - 1).y,
						(int) X1.get(i).y, (int) Y1.get(i).y, g);
				g.setColor(Color.BLUE);
				drawLine((int) X2.get(i - 1).y, (int) Y2.get(i - 1).y,
						(int) X2.get(i).y, (int) Y2.get(i).y, g);
			}
			g.setColor(Color.RED);
			g.drawLine(centerX, 0, centerX, this.getHeight());
			g.drawLine(0, centerY, this.getWidth(), centerY);
			updateTime = 0;

			for (int s = 0; s < 10; s++) {
				eX1.step(.5);
				eY1.step(.5);

				eX2.step(.5);
				eY2.step(.5);
			}

			for (; i < X1.size(); i++) {
				// System.out.println("DRAWING");

				drawLine((int) X1.get(i - 1).y, (int) Y1.get(i - 1).y,
						(int) X1.get(i).y, (int) Y1.get(i).y, g);
				drawLine((int) X2.get(i - 1).y, (int) Y2.get(i - 1).y,
						(int) X2.get(i).y, (int) Y2.get(i).y, g);
			}

			X1 = eX1.getFunction().derivates;
			Y1 = eY1.getFunction().derivates;

			X2 = eX2.getFunction().derivates;
			Y2 = eY2.getFunction().derivates;

			boolean crash = false;
			for (int k = 1; k < X1.size(); k++) {
				for (int j = 1; j < X1.size(); j++) {
					Point left1 = new Point(X1.get(k).y, Y1.get(k).y);
					Point right1 = new Point(X2.get(j).y, Y2.get(j).y);

					Point left2 = new Point(X1.get(k - 1).y, Y1.get(k - 1).y);
					Point right2 = new Point(X2.get(j - 1).y, Y2.get(j - 1).y);

					if (intersect(left1, left2, right1, right2)) {
						crash = true;
					}
				}

			}

			if (crash) {

				g.drawString("CRASH D:", 200, 200);
			}

		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lastTime = startTime;
		paint(g);
	}

	public Point getPoint(double t, boolean x, boolean right) {
		return new Point(t, ((x ? xVal(t, right) : yVal(t, right))) + 10
				* Math.abs(random.nextGaussian()));
	}

	public double xVal(double t, boolean right) {
		return (t * t) * (right ? -1 : 1) + (right ? this.getWidth() : 0)
				+ (right ? t : t / 4);
	}

	public double yVal(double t, boolean right) {
		return t * t + (right ? t : 1);
	}

	public static boolean intersect(Point p1, Point p2, Point q1, Point q2) {

		double minX1 = p1.x < p2.x ? p1.x : p2.x;
		double maxX1 = p1.x > p2.x ? p1.x : p2.x;

		double minX2 = q1.x < q2.x ? q1.x : q2.x;
		double maxX2 = q1.x > q2.x ? q1.x : q2.x;

		double minY1 = p1.y < p2.y ? p1.y : p2.y;
		double maxY1 = p1.y > p2.y ? p1.y : p2.y;

		double minY2 = q1.y < q2.y ? q1.y : q2.y;
		double maxY2 = q1.y > q2.y ? q1.y : q2.y;

		// System.out.println("Min X's: " + minX1 + " " + minX2);
		// System.out.println("Max X's: " + maxX1 + " " + maxX2);
		// System.out.println("Min Y's: " + minY1 + " " + minY2);
		// System.out.println("Max X's: " + maxX1 + " " + maxX2);

		double slope1 = (p1.y - p2.y) / (p1.x - p2.x);
		double slope2 = (q1.y - q2.y) / (q1.x - q2.x);

		double intercept1 = p1.y - slope1 * p1.x;
		double intercept2 = q1.y - slope2 * q1.x;

		if (slope1 == slope2 && intercept1 == intercept2) {
			return true;
		}

		double xInt = (intercept1 - intercept2) / (slope2 - slope1);
		double yInt = xInt * slope1 + intercept1;

		// System.out.println("Intersects at " + xInt + ", " + yInt);

		return (xInt <= maxX1 && xInt <= maxX2 && xInt >= minX1
				&& xInt >= minX2 && yInt <= maxY1 && yInt <= maxY2
				&& yInt >= minY1 && yInt >= minY2);
	}

}
