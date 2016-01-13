package shape;

public abstract class Shape
{
	protected double area = -1;
	public abstract double getArea();
	public void print()
	{System.out.println(area);}

	protected class Test
	{
		public void say()
		{System.out.println("hello from shape!");}
	}
}

