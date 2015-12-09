// package examples;

import java.util.ArrayList;

public class Example {
	
    public static void main (String[] args) {
	            Example ex = new Example();
		    // ArrayList<Integer> l = null;
		    // ArrayList<Integer> = new ArrayList<Integer>();
	            ex.foo(2, 1, null);
    }
    
	public int foo(int x, int y, ArrayList<Integer> l){
		if (x > y){
			System.out.println("First");
			l.add(x);
			System.out.println(l.get(0));
			return x;
		}else{
			System.out.println("Second");
			l.add(y);
			System.out.println(l.get(0));
			return y;
		}
	}
}
