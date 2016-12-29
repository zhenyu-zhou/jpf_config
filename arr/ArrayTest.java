package arr;

public class ArrayTest
{
	protected ArrayWrapper wrapper = new ArrayWrapper();
	protected int n = 9;

        public static void testInt(int x)
        {
                if(x > 5)
                         System.out.println("x>5");
	}

	public static void test(ArrayTest instance, int x)
	{
		/* if(x > 5)
			 System.out.println("x>5");

		if(instance.n == 9)
			System.out.println("n==9");

		int len = instance.wrapper.length();
		if(len > 10)
		{
			System.out.println("len > 10");
		} else {
			System.out.println("len <= 10");
		} */

		instance.wrapper.objArr[0] = new ArrayObject();
		if (instance.wrapper.objArr[0].n > 9)
		{
			System.out.println("n > 9");
		} else {
			System.out.println("n <= 9");
		}
	}

	public static void main(String[] args)
	{
		ArrayTest instance = new ArrayTest();
		test(instance, 6);
		testInt(6);
	}
}
