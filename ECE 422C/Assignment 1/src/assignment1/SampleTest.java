package assignment1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class SampleTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);

    @Test
    public void sampleTest() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = x.length;

        assertEquals(2, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }

    @Test
    public void sampleTest1() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = 1;

        assertEquals(-1, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }
    
    @Test
    public void sampleTest2() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = 4;

        assertEquals(2, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }
    @Test
    public void sampleTest3() {
        int[] x = new int[]{-5, -3, -1, 1, 3, 5,5,9,11,15,13,15,17,19,21,23,25};
        int[] original = x.clone();
        int n = 10;
        assertEquals(5, SortTools.find(x, n, 5));  //6, is also a valid answer
        assertArrayEquals(original, x);
    }
    @Test
    public void sampleTest4() {
        int[] x = new int[]{1,10,100,1000,10000,100000,1000000};
        int[] original = x.clone();
        int n = 3;
        assertEquals(-1, SortTools.find(x, n, 1000));  
        assertArrayEquals(original, x);
    }
    
    @Test
    public void sampleTest5() {
        int[] x = new int[]{1,10,100,1000,10000,100000,1000000};
        int[] original = x.clone();
        int n = 6;
        assertEquals(3, SortTools.find(x, n, 1000));  
        assertArrayEquals(original, x);
    }
    @Test
    public void sampleTest6() {
        int[] x = new int[]{10,20,30,40,50,45,35,45};
        int[] original = x.clone();
        int n = 5;
        assertEquals(2, SortTools.find(x, n, 30));  
        assertArrayEquals(original, x);
    }
    @Test
    public void sampleTest7() {
        int[] x = new int[]{10,20,30,40,50};
        int[] original = x.clone();
        int n = 5;
        assertEquals(4, SortTools.find(x, n, 50));  
        assertArrayEquals(original, x);
    }
    @Test
    public void sampleTest8() {
        int[] x = new int[]{20,20,20,20,20};
        int[] original = x.clone();
        int n = 5;
        assertEquals(2, SortTools.find(x, n, 20));  
        assertArrayEquals(original, x);
    }
    @Test
    public void sampleTest9() {
        int[] x = new int[]{20,20,20,20,20};
        int[] original = x.clone();
        int n = 5;
        assertEquals(-1, SortTools.find(x, n, 30));  
        assertArrayEquals(original, x);
    }
    @Test
    public void isSortedTest1() {
        int[] x = new int[]{1,10,100,1000,10000,100000,1000000};
        int n = 7;
        assertTrue(SortTools.isSorted(x, n));
    }
    @Test
    public void isSortedTest2() {
        int[] x = new int[]{1,10,100,1000,10000,100,10};
        int n = 5;
        assertTrue(SortTools.isSorted(x, n));
    }
    @Test
    public void isSortedTest3() {
        int[] x = new int[]{1,10,100,1000,10000,100,10};
        int n = 6;
        assertFalse(SortTools.isSorted(x, n));
    }
    @Test
    public void isSortedTest4() {
        int[] x = new int[]{12,5,6,8,7,3,2,5,9,8,7,5,3,6,9,8,5,1,3,5,4};
        int n = 21;
        assertFalse(SortTools.isSorted(x, n));
    }
    @Test
    public void isSortedTest5() {
        int[] x = new int[]{12,5,6,8,7,3,2,5,9,8,7,5,3,6,9,8,5,1,3,5,4};
        int n = 1;
        assertTrue(SortTools.isSorted(x, n));
    }
    @Test
    public void isSortedTest6() {
        int[] x = new int[]{1,3,5,4,2,1};
        int n = 3;
        assertTrue(SortTools.isSorted(x, n));
    }
    @Test
    public void isSortedTest7() {
        int[] x = new int[]{12,5,6,8,7,3,2,5,9,8,7,5,3,6,9,8,5,1,3,5,4};
        int n = 0;
        assertFalse(SortTools.isSorted(x, n));
    }
    @Test
    public void isSortedTest8() {
        int[] x = new int[]{1,3,5,4,2,1};
        int n = 4;
        assertFalse(SortTools.isSorted(x, n));
    }
    @Test
    public void copyTest1() {
    	int[] x = new int[]{1, 3, 5, 7};
        int[] original = x.clone();
        int n = 3;
        int[] act = new int[] {1, 3, 4, 5};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 4));
        assertArrayEquals(original, x);
        
    }
    @Test
    public void copyTest2() {
    	 int[] x = new int[]{-5,-4,-2,-1,0};
         int[] original = x.clone();
          int n = 3;
        int[] act = new int[] {-5,-4,-3,-2};
          int[] copy = SortTools.copyAndInsert(x, n, -3);
         assertArrayEquals(act,copy);
         assertArrayEquals(original, x);
        
        
    }
    @Test
    public void copyTest3() {
    	int[] x = new int[]{1, 3, 5, 7};
        int[] original = x.clone();
        int n = 4;
        int[] act = new int[] {1, 3, 5, 7, 8};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 8));
        assertArrayEquals(original, x);
        
    }
    @Test
    public void copyTest4() {
    	int[] x = new int[]{1, 3, 5, 7};
        int[] original = x.clone();
        int n = 4;
        int[] act = new int[] {1, 3,4, 5, 7};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 4));
        assertArrayEquals(original, x);
        
    }
    
    @Test
    public void copyTest5() {
    	int[] x = new int[]{1,2,3,4,5,6,7,8,9,10,11};
        int[] original = x.clone();
        int n = 11;
        int[] act = new int[] {0,1,2,3,4,5,6,7,8,9,10,11};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 0));
        assertArrayEquals(original, x);
        
    }
    @Test
    public void copyTest6() {
    	int[] x = new int[]{1,2,3,4,5,6,7,8,9,10,11};
        int[] original = x.clone();
        int n = 10;
        int[] act = new int[] {0,1,2,3,4,5,6,7,8,9,10};
        int[] copy = SortTools.copyAndInsert(x, n, 0);
        assertArrayEquals(act,copy);
        assertArrayEquals(original, x);
        
    }
    @Test
    public void copyTest7() {
    	int[] x = new int[]{1,2,3,4,5,6,7,8,9,10,11};
        int[] original = x.clone();
        int n = 0;
        int[] act = new int[] {0};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 0));
        assertArrayEquals(original, x);
}
    @Test
    public void copyTest8() {
    	int[] x = new int[]{1,2,3};
        int[] original = x.clone();
        int n = 1;
        int[] act = new int[] {1,4};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 4));
        assertArrayEquals(original, x);
}
    @Test
    public void copyTest9() {
    	int[] x = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    	int[] original = x.clone();
        int n = 5;
        int[] act = new int[] {0,0,0,0,0,1};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 1));
        assertArrayEquals(original, x);
}
    @Test
    public void copyTest10() {
    	int[] x = new int[]{1,2,3,4,5,6,7,8,9};
    	int[] original = x.clone();
        int n = 5;
        int[] copy = SortTools.copyAndInsert(x, n, 3);
        int[] act = new int[] {1,2,3,4,5};
        for(int i =0; i<copy.length;i++) {
        	System.out.print(copy[i]);
        }
        assertArrayEquals(act,copy);
        assertArrayEquals(original, x);
}
    @Test
    public void copyTest11() {
    	int[] x = new int[]{1,2,3,4,5,6,7,8,9,10};
    	int[] original = x.clone();
        int n = 10;
        int[] act = new int[] {1,2,3,4,5,6,7,8,9,10};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 1));
        assertArrayEquals(original, x);
}
    @Test
    public void copyTest12() {
    	int[] x = new int[]{1,2,3,4,5,6,7,8,9,10};
    	int[] original = x.clone();
        int n = 5;
        int[] act = new int[] {1,2,3,4,5,10};
        assertArrayEquals(act,SortTools.copyAndInsert(x, n, 10));
        assertArrayEquals(original, x);
}
    @Test
    public void InsertTest1() {
    	int[] x = new int[]{1,2,4,5};
        int n = 1;
        int[] act = new int[] {1,3,2,4};
        int insert = SortTools.insertInPlace(x, n, 3);
        assertArrayEquals(act,x);
        assertEquals(2, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest2() {
    	int[] x = new int[]{1, 3, 5, 7};
        int n = 3;
        int[] act = new int[] {1,3,4,5};
        int insert = SortTools.insertInPlace(x, n, 4);
        assertArrayEquals(act,x);
        assertEquals(4, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest3() {
    	int[] x = new int[]{1, 3, 3, 7};
        int n = 3;
        int[] act = new int[] {1,3,3,4};
        int insert = SortTools.insertInPlace(x, n, 4);
        assertArrayEquals(act,x);
        assertEquals(4, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest4() {
    	int[] x = new int[]{1, 3};
        int n = 1;
        int[] act = new int[] {1,4};
        int insert = SortTools.insertInPlace(x, n, 4);
        assertArrayEquals(act,x);
        assertEquals(2, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest5() {
    	int[] x = new int[]{1, 3};
        int n = 1;
        int[] act = new int[] {1,3};
        int insert = SortTools.insertInPlace(x, n, 1);
        assertArrayEquals(act,x);
        assertEquals(1, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest6() {
    	int[] x = new int[]{1,2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        int n = 15;
        int[] act = new int[] {1,2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        int insert = SortTools.insertInPlace(x, n, 12);
        assertArrayEquals(act,x);
        assertEquals(15, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest7() {
    	int[] x = new int[]{1,5,7,8};
        int n = 3;
        int[] act = new int[] {1,4,5,7};
        int insert = SortTools.insertInPlace(x, n, 4);
        assertArrayEquals(act,x);
        assertEquals(4, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest8() {
    	int[] x = new int[]{1,3,5,7};
        int n = 3;
        int[] act = new int[] {1,3,5,8};
        int insert = SortTools.insertInPlace(x, n, 8);
        assertArrayEquals(act,x);
        assertEquals(4, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest9() {
    	int[] x = new int[]{1,5,7,9,10};
        int n = 3;
        int[] act = new int[] {1,2,5,7,9};
        int insert = SortTools.insertInPlace(x, n, 2);
        assertArrayEquals(act,x);
        assertEquals(4, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest10() {
    	int[] x = new int[]{1,5,7,9,10};
        int n = 3;
        int[] act = new int[] {1,5,7,8,9};
        int insert = SortTools.insertInPlace(x, n, 8);
        assertArrayEquals(act,x);
        assertEquals(4, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest11() {
    	int[] x = new int[]{-10,-5,0,5,10};
        int n = 3;
        int[] act = new int[] {-10,-5,-1,0,5};
        int insert = SortTools.insertInPlace(x, n, -1);
        assertArrayEquals(act,x);
        assertEquals(4, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest12() {
    	int[] x = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int n = 5;
        int[] act = new int[] {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0};
        int insert = SortTools.insertInPlace(x, n, 1);
        assertArrayEquals(act,x);
        assertEquals(6, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest13() {
    	int[] x = new int[]{1,2,3,4,5,7,8,9,10};
        int n = 7;
        int[] act = new int[] {1,2,3,4,5,6,7,8,9};
        int insert = SortTools.insertInPlace(x, n, 6);
        assertArrayEquals(act,x);
        assertEquals(8, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest14() {
    	int[] x = new int[]{1,2,3,4,5,7,8,9,10};
        int n = 7;
        int[] act = new int[] {1,2,3,4,5,7,8,9,10};
        int insert = SortTools.insertInPlace(x, n, 5);
        assertArrayEquals(act,x);
        assertEquals(7, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest15() {
    	int[] x = new int[]{1,2,3,4,5,7,8,9,10};
        int n = 7;
        int[] act = new int[] {0,1,2,3,4,5,7,8,9};
        int insert = SortTools.insertInPlace(x, n, 0);
        assertArrayEquals(act,x);
        assertEquals(8, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest16() {
    	int[] x = new int[]{1,2,3,4,5,7,8,9,10};
        int n = 7;
        int[] act = new int[] {1,2,3,4,5,7,8,11,9};
        int insert = SortTools.insertInPlace(x, n, 11);
        assertArrayEquals(act,x);
        assertEquals(8, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest17() {
    	int[] x = new int[]{1,2,3,4,5,7,8,9,10};
        int n = x.length-1;
        int[] act = new int[] {1,2,3,4,5,7,8,9,11};
        int insert = SortTools.insertInPlace(x, n, 11);
        assertArrayEquals(act,x);
        assertEquals(9, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void InsertTest18() {
    	int[] x = new int[]{1,2,3,4,5,7,8,9,10};
        int n = 2;
        int[] act = new int[] {1,2,4,3,4,5,7,8,9};
        int insert = SortTools.insertInPlace(x, n, 4);
        assertArrayEquals(act,x);
        assertEquals(3, insert);
        assertTrue(SortTools.isSorted(x, n+1));
}
    @Test
    public void SortTest1() {
    	int[] x = new int[]{1,2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        int n = 3;
        int[] act = new int[] {1,2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
        
        
        
}
    @Test
    public void SortTest2() {
    	int[] x = new int[]{7,6,5,4,3,2,1,0,-1,-2,-3};
        int n = x.length;
        int[] act = new int[] {-3,-2,-1,0,1,2,3,4,5,6,7};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
        
        
        
}
    @Test
    public void SortTest3() {
    	int[] x = new int[]{2,3,4,5,6,7,8,9,10,1,11,12,13,14,15,16,17,18};
        int n = x.length;
        int[] act = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
        
        
        
}
    @Test
    public void SortTest4() {
    	int[] x = new int[]{2,3,4,5,6,7,8,9,10,1,11,12,13,14,15,16,17,18};
        int n = 3;
        int[] act = new int[] {2,3,4,5,6,7,8,9,10,1,11,12,13,14,15,16,17,18};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
        
        
        
}
    @Test
    public void SortTest5() {
    	int[] x = new int[]{2,35,27,163,10457,15,16,87};
        int n = 3;
        int[] act = new int[] {2,27,35,163,10457,15,16,87};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
    
    
 }
    @Test
    public void SortTest6() {
    	int[] x = new int[]{2,35,27,14,10457,15,16,87};
        int n = 3;
        int[] act = new int[] {2,27,35,14,10457,15,16,87};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
    
    
 }
    @Test
    public void SortTest7() {
    	int[] x = new int[]{2,35,27,14,10457,15,16,87};
        int n = x.length;
        int[] act = new int[] {2,14,15,16,27,35,87,10457};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
    
    
 }
    @Test
    public void SortTest8() {
    	int[] x = new int[]{2,4,6,8,10,3,5,7,8};
        int n = 5;
        int[] act = new int[] {2,4,6,8,10,3,5,7,8};
        SortTools.insertSort(x, n);
        assertArrayEquals(act,x);
    
    
 }@Test
    public void SortTest9() {
 	int[] x = new int[]{2,4,6,8,10,3,5,7,8};
     int n = x.length;
     int[] act = new int[] {2,3,4,5,6,7,8,8,10};
     SortTools.insertSort(x, n);
     assertArrayEquals(act,x);
 
 
}
 @Test
 public void SortTest10() {
	int[] x = new int[]{2,4,6,8,10,3,5,7,8};
  int n = 6;
  int[] act = new int[] {2,3,4,6,8,10,5,7,8};
  SortTools.insertSort(x, n);
  assertArrayEquals(act,x);


}
 @Test
 public void SortTest11() {
	int[] x = new int[]{7,3,1,3};
  int n = 1;
  int[] act = new int[] {7,3,1,3};
  SortTools.insertSort(x, n);
  assertArrayEquals(act,x);


}
}
