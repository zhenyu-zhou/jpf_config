package arr;

public class ArrayWrapper
{
	public static final int SIZE = 5;
	private int[] arr = new int[SIZE];
	public static ArrayObject[] objArr = new ArrayObject[SIZE];

	public int length()
	{
		return arr.length;
	}
}
