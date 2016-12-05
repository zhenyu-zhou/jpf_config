package arr;

public class ArrayTest
{
	protected ArrayWrapper wrapper;

	public static void test()
	{
		ArrayTest instance = new ArrayTest();
		int len = instance.wrapper.length();
		if(len > 10)
		{
			System.out.println("len > 10");
		} else {
			System.out.println("len <= 10");
		}
	}

	public static void main(String[] args)
	{
		test();
	}
}
