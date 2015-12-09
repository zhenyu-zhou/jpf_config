import java.util.ArrayList;

public class Example_fun {
	
    public static void main (String[] args) {
	            Example_fun ex = new Example_fun();
	            ex.foo(2, 1, null);
    }
    
	public int foo(int x, int y, ArrayList<Integer> l){
		Example_fun e = new Example_fun();
		if (x > y){
			System.out.println("First");
			l.add(x);
			System.out.println(l.get(0));
			e.test1();
			return x;
		}else{
			System.out.println("Second");
			// l.add(y);
			// System.out.println(l.get(0));
			e.test2();
			return y;
		}
	}

	public void test1()
	{System.out.println("test 1");}

	public void test2()
	{System.out.println("test 2");}
}
