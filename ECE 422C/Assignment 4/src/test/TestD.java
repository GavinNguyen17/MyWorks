package test;

import assignment4.annotations.Alphabetical;
import assignment4.annotations.Test;
import assignment4.assertions.Assert;

@Alphabetical
public class TestD {
    @Test
    public void testA() {
    	System.out.println("Test 5");
        Assert.assertEquals(1, 1);
    }
    @Test
    public void testC() {
    	System.out.println("Test 6");
        Assert.assertEquals(3, 1 + 2);
    }
    @Test
    public void atest() {
    	System.out.println("Test 1");
        Assert.assertEquals(2, 2);
    }
    @Test
    public void b0() {
    	System.out.println("Test 2");
    }
    @Test
    public void foo() {
    	System.out.println("Test 3");
    }
    @Test
    public void foo1() {
    	System.out.println("Test 4");
    }
}
