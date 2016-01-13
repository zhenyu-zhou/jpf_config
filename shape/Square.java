package shape;

public class Square extends Shape
{
	public static double len = -1;
	public static Test test = null; // new Test();

	public void setLen(double l) { len = l; }
	public double getLen() { return len; }
	public double getArea()
	{
		if(test == null)
			test = this.new Test();
		test.say();
		area = len*len;
		print();
		if(len < 0)
			// System.out.println("minus len!");
			throw new RuntimeException("Invalid len!");
		else if(len == 0)
		{
			// int t = Integer.parseInt("are you kidding?");
			int[] arr = new int[2];
			arr[10] = 5;
			System.out.println("len 0");
			return area;
		}	
		else
			return area;
		// return -1;
	}

        /* public static void main(String[] args)
        {
                Square sq = new Square();
                sq.setLen(1.5);
                System.out.println(sq.getArea());
        } */
}

