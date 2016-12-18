

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class Forwarding_test {

    public void test(int x)
    {
        if(x > 5)
                System.out.println(">5");
        else
                System.out.println("<5");
    }

    public void testLong(Long x)
    {
        if(x > 5)
                System.out.println(">5");
        else
                System.out.println("<5");
    }

    public static void main(String[] args)
    {
            System.out.println("Forwarding test main");
            Forwarding_test instance = new Forwarding_test();
	    instance.test(3);
	    instance.testLong(3L);

	    System.out.println("Forwarding test main end");
    }

}

