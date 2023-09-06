package test;

import assignment4.annotations.Test;
import assignment4.assertions.Assert;

public class TestA {
/*    @Test
    public void test1() throws InterruptedException {
    	System.out.println("Test 1");
        Assert.assertTrue(true);
        Thread.sleep(1000);
    }*/
    @Test
    public void test2() throws InterruptedException {
    	System.out.println("Test 2");
        Assert.assertEquals(3, 1 + 2);
        Thread.sleep(1000);
    }
}
