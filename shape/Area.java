package shape;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class Area
{
	static Area instance = new Area();	
	public enum global {Ext, Int};
	public static Hashtable <Integer, Long> tmp = new Hashtable <Integer, Long>();

	public double getArea(Shape s)
	{ return s.getArea(); }

	public void printArea(Square sq, int x)
	{
		Square s = new Square();
		// test the parameter variable
		if (x > 5)
			return ;
		else
			System.out.println(sq.getArea());
	}

	public void testClass(int x)
	{
		if (x > 6)
                        System.out.println(">6 in testClass");
                else
                        System.out.println("<6 in testClass");

		Class c = Area.class;
		// System.out.println(c.getName());
		printClass(c);
		printClass(Logger.class);
		Logger log = LoggerFactory.getLogger(shape.Area.class);
		// System.out.println("log: "+log);
	}

	public void printClass(Class c)
	{
		System.out.println("Name: "+c.getName());
	}
	
	public void testLong(Long l1, Long l2)
	{
		if(l1 > 5)
			System.out.println(">5");
		else    System.out.println("<5");
		long t = l2+1;
                if(t > 11)
                        System.out.println(">10");
                else    System.out.println("<10");
		if(false) System.out.println("false");
		else System.out.println("true");
	}

	public void put(global g, long v)
	{
		tmp.put(g.ordinal(), v);
	}

	public void testHashEnum(Long l1, Long l2)
	{
                if(l1 > 5)
                        System.out.println(">5");
                else    System.out.println("<5");

		// put(global.Ext, l2+1);
		// tmp.put(global.Ext.ordinal(), l2+1);
                if(tmp.get(global.Ext.ordinal()) > 15)
                        System.out.println(">10");
                else    
			System.out.println("<10");
		/* if(tmp.get(global.Int.ordinal()) > 5){
			System.out.println(">5");
		}else{
		System.out.println("<5");
		} */
	}

public enum Color {  
  RED, GREEN, YELLOW  
}
Color c = Color.GREEN; 

	public void testEnum(Area a)
	{
		switch(a.c)
		{
			case RED: System.out.println("RED"); break;
                        case GREEN: System.out.println("GREEN"); break;
                        case YELLOW: System.out.println("YELLOW"); break;
		}
	}

	public void testSwitch(int x)
	{
		switch(x)
		{
			case 1: System.out.println(1); break;
			case 2: System.out.println(2); break;
			default: System.out.println("other");
		}
	}

	public static void main(String[] args)
	{
		System.out.println("zzy: in area main");
		// Shape sq = new Square();
		// sq.setLen(new java.util.Random().nextDouble() - 0.5);
		// ((Square)sq).setLen(1.5);
		// System.out.println(new Area().getArea(sq));

		/* Square square  = new Square();
		square.setLen(1.3);
		instance.printArea(square, 4);

		instance.testClass(88); */

		// instance.testLong(0L, 1L);
		// instance.testLong(99);

		// instance.testEnum(instance);
		// instance.testSwitch(3);

		instance.testHashEnum(0L, 1L);

		// Circle c = new Circle();
		// c.setRadium(1.0);
		// System.out.println(new Area().getArea(c));
	}
}

