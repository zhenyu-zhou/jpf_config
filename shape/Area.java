package shape;

public class Area
{
	static Area instance = new Area();	

	public double getArea(Shape s)
	{ return s.getArea(); }

	public void printArea(Square sq, int x)
	{
		if (x > 5)
			return ;
		else
			System.out.println(sq.getArea());
	}

	public static void main(String[] args)
	{
		System.out.println("zzy: in area main");
		Shape sq = new Square();
		// sq.setLen(new java.util.Random().nextDouble() - 0.5);
		// ((Square)sq).setLen(1.5);
		// System.out.println(new Area().getArea(sq));

		Square square  = new Square();
		square.setLen(1.3);
		instance.printArea(square, 4);

		// Circle c = new Circle();
		// c.setRadium(1.0);
		// System.out.println(new Area().getArea(c));
	}
}

