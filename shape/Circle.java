package shape;

public class Circle extends Shape
{
	private double radium;
	public void setRadium(double r) { radium = r; }
	public double getRadium() { return radium; }
	public double getArea()
	{
		if(radium <= 0)
			throw new RuntimeException("Invalid r!!");
		else
			return 3.14*radium*radium;
	}
}

