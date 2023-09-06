package test;

import assignment4.annotations.Alphabetical;
import assignment4.annotations.Order;
import assignment4.annotations.Ordered;
import assignment4.annotations.Test;
import assignment4.assertions.Assert;

@Alphabetical
public class TestG {
    
    public void testA() {
    	System.out.println("Test 3 ");
        Assert.assertEquals(1, 1);
    }
  
    public void testC() {
    	System.out.println("Test 2 ");
        Assert.assertEquals(3, 1 + 2);
    }
   
    public void atest() {
    	System.out.println("Test 1 ");
    
        Assert.assertEquals(3, 3);
        
    }
    
    public void b0() {
    	System.out.println("Test 4 ");
    }
   
    public void foo() {
    	System.out.println("Test 0 ");
    }
    
    public void foo1() {
    	System.out.println("Test 6 ");
    }
}
