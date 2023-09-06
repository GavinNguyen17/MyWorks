package test;

import assignment4.annotations.Alphabetical;
import assignment4.annotations.Order;
import assignment4.annotations.Ordered;
import assignment4.annotations.Test;
import assignment4.assertions.Assert;

@Alphabetical
public class TestH {
    @Test
    
    public void AAAAAAAAAAAAAAAAD() {
    	System.out.println("Test 3 ");
        Assert.assertEquals(1, 1);
    }
    @Test
   
    public void AAAAAAAAAAAAAAAAC() {
    	System.out.println("Test 2 ");
        Assert.assertEquals(3, 1 + 2);
    }
    @Test
    
    public void AAAAAAAAAAAAAAAAB() {
    	System.out.println("Test 1 ");
    
        Assert.assertEquals(3, 3);
        
    }
    @Test
 
    public void AAAAAAAAAAAAAAAAE() {
    	System.out.println("Test 4 ");
    }
    @Test
 
    public void AAAAAAAAAAAAAAAAA() {
    	System.out.println("Test 0 ");
    }
    @Test
    
    public void AAAAAAAAAAAAAAAAF() {
    	System.out.println("Test 6 ");
    }
}
