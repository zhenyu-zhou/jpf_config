package shape;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Area
{
	static Area instance = new Area();	

	public double getArea(Shape s)
	{ return s.getArea(); }

	public void printArea(Square sq, int x)
	{
		// test the parameter variable
		if (x > 5)
			return ;
		else
			System.out.println(sq.getArea());
	}

	public void testClass(int x)
	{
		if (x > 6)
                        System.out.println(">6 in testClass");
                else
                        System.out.println("<6 in testClass");

		Class c = Area.class;
		// System.out.println(c.getName());
		printClass(c);
		printClass(Logger.class);
		Logger log = LoggerFactory.getLogger(shape.Area.class);
		// System.out.println("log: "+log);
	}

	public void printClass(Class c)
	{
		System.out.println("Name: "+c.getName());
	}

	public static void main(String[] args)
	{
		System.out.println("zzy: in area main");
		// Shape sq = new Square();
		// sq.setLen(new java.util.Random().nextDouble() - 0.5);
		// ((Square)sq).setLen(1.5);
		// System.out.println(new Area().getArea(sq));

		Square square  = new Square();
		square.setLen(1.3);
		instance.printArea(square, 4);

		instance.testClass(88);

		// Circle c = new Circle();
		// c.setRadium(1.0);
		// System.out.println(new Area().getArea(c));
	}
}

