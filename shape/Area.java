package shape;

public class Area
{
	public double getArea(Shape s)
	{ return s.getArea(); }

	public static void main(String[] args)
	{
		Square sq = new Square();
		// sq.setLen(new java.util.Random().nextDouble() - 0.5);
		sq.setLen(1.5);
		System.out.println(new Area().getArea(sq));
		// Circle c = new Circle();
		// c.setRadium(1.0);
		// System.out.println(new Area().getArea(c));
	}
}

