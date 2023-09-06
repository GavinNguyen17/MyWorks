package test;

import assignment4.annotations.Alphabetical;
import assignment4.annotations.Order;
import assignment4.annotations.Ordered;
import assignment4.annotations.Test;
import assignment4.assertions.Assert;

@Alphabetical
public class TestO {
	@Test
    public void test1() {
    	System.out.println("Test 1");
        Assert.assertTrue(false);
    }
    @Test
    public void test2() {
    	System.out.println("Test 2");
        Assert.assertEquals(4, 1 + 2);
    }
    @Test
    public void test3() {
    	System.out.println("Test 1");
        Assert.assertTrue(false);
    }
    @Test
    public void test4() {
    	System.out.println("Test 2");
        Assert.assertEquals(4, 1 + 2);
    }
    @Test
    public void test5() {
    	System.out.println("Test 1");
        Assert.assertTrue(false);
    }
    @Test
    public void test6() {
    	System.out.println("Test 2");
        Assert.assertEquals(4, 1 + 2);
    }
    @Test
    public void test7() {
    	System.out.println("Test 1");
        Assert.assertTrue(false);
    }
    @Test
    public void test8() {
    	System.out.println("Test 2");
        Assert.assertEquals(4, 1 + 2);
    }
}
