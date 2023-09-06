package test;

import assignment4.annotations.Order;
import assignment4.annotations.Ordered;
import assignment4.annotations.Test;
import assignment4.assertions.Assert;

@Ordered
public class TestF {
 
    @Order(3)
    public void testA() {
    	System.out.println("Test 3 ");
        Assert.assertEquals(1, 1);
    }

    @Order(2)
    public void testC() {
    	System.out.println("Test 2 ");
        Assert.assertEquals(3, 1 + 2);
    }

    @Order(1)
    public void atest() {
    	System.out.println("Test 1 ");
    
        Assert.assertEquals(3, 3);
        
    }

    @Order(4)
    public void b0() {
    	System.out.println("Test 4 ");
    }

    @Order(0)
    public void foo() {
    	System.out.println("Test 0 ");
    }

    @Order(6)
    public void foo1() {
    	System.out.println("Test 6 ");
    }
}
