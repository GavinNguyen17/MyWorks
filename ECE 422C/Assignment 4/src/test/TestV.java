package test;

import assignment4.annotations.Alphabetical;
import assignment4.annotations.Order;
import assignment4.annotations.Ordered;
import assignment4.annotations.Test;
import assignment4.assertions.Assert;

@Alphabetical
public class TestV {
    @Test
    public void testA() {
    	System.out.println("Test 1");
        Assert.assertEquals(1, 1);
    }
    @Test
    public void testa() {
    	System.out.println("Test 2");
        Assert.assertEquals(3, 1 + 2);
    }
}