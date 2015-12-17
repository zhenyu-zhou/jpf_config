package shape;

public class Square extends Shape
{
	private double len = -1;
	public void setLen(double l) { len = l; }
	public double getLen() { return len; }
	public double getArea()
	{
		// len = 0;
		if(len < 0)
			System.out.println("minus len!");
			// throw new RuntimeException("Invalid len!");
		else if(len == 0)
		{
			// int t = Integer.parseInt("are you kidding?");
			int[] arr = new int[2];
			arr[10] = 5;
			System.out.println("len 0");
		}	
		else
			return len*len;
		return -1;
	}

        public static void main(String[] args)
        {
                Square sq = new Square();
                sq.setLen(1.5);
                System.out.println(sq.getArea());
        }
}

