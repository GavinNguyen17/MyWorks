package test;

import assignment4.annotations.Order;
import assignment4.annotations.Ordered;
import assignment4.annotations.Test;
import assignment4.assertions.Assert;

@Ordered
public class TestI {
    @Test
    @Order(356)
    public void testA() {
    	System.out.println("Test 3 ");
        Assert.assertEquals(1, 1);
    }
    @Test
    @Order(20)
    public void testC() {
    	System.out.println("Test 2 ");
        Assert.assertEquals(3, 1 + 2);
    }
    @Test
    @Order(1)
    public void atest() {
    	System.out.println("Test 1 ");
    
        Assert.assertEquals(3, 3);
        
    }
    @Test
    @Order(400)
    public void b0() {
    	System.out.println("Test 4 ");
    }
    @Test
    @Order(0)
    public void foo() {
    	System.out.println("Test 0 ");
    }
    @Test
    @Order(6000)
    public void foo1() {
    	System.out.println("Test 6 ");
    }
}
