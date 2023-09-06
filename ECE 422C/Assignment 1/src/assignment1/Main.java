/*
 * This file is how you might test out your code.  Don't submit this, and don't
 * have a main method in SortTools.java.
 */

package assignment1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
public class Main {
    public static void main(String [] args) {
    	//isSorted
        System.out.println("----- Testing isSorted...");
        int[] x = {1,2,3};
        int n = 3;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        System.out.println("Correct output: true, Your output: " + SortTools.isSorted(x,n));
        x = new int[]{100};
        n = 1;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        System.out.println("Correct output: true, Your output: " + SortTools.isSorted(x,n));
        x = new int[]{6, 5, 4, 3, 2, 1};
        n = 6;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        System.out.println("Correct output: false, Your output: " + SortTools.isSorted(x,n));
        x = new int[]{-1, 2, 2, 3};
        n = 4;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        System.out.println("Correct output: true, Your output: " + SortTools.isSorted(x,n));
        x = new int[]{1,2,3,4,3,6};
        n = 6;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        System.out.println("Correct output: false, Your output: " + SortTools.isSorted(x,n));
        n = 4;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        System.out.println("Correct output: true, Your output: " + SortTools.isSorted(x,n));

        //find
        System.out.println("\n\n----- Testing find -----");
        x = new int[]{1,2,3};
        n = 3;
        int v = 3;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 2, Your output: " + SortTools.find(x,n,v));
        x = new int[]{100};
        n = 1;
        v = 100;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 0, Your output: " + SortTools.find(x,n,v));
        x = new int[]{2,2,2,2,2};
        n = 5;
        v = 2;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 0-4, Your output: " + SortTools.find(x,n,v));
        x = new int[]{-1, 2, 2, 3};
        n = 4;
        v = 2;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 1, Your output: " + SortTools.find(x,n,v));
        v = 3;
        n = 2;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: -1, Your output: " + SortTools.find(x,n,v));

        //copyAndInsert
        System.out.println("\n\n----- Testing copyAndInsert -----");
        x = new int[]{1,2,4};
        n = 3;
        v = 3;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: [1, 2, 3, 4], Your output: " + Arrays.toString(SortTools.copyAndInsert(x,n,v)));
        x = new int[]{100};
        n = 1;
        v = -100;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: [-100, 100], Your output: " + Arrays.toString(SortTools.copyAndInsert(x,n,v)));
        x = new int[]{2,2,2,2,2};
        n = 5;
        v = 2;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: [2, 2, 2, 2, 2], Your output: " + Arrays.toString(SortTools.copyAndInsert(x,n,v)));
        x = new int[]{-1, 2, 3, 3};
        n = 4;
        v = 4;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: [-1, 2, 3, 3, 4], Your output: " + Arrays.toString(SortTools.copyAndInsert(x,n,v)));
        v = 5;
        n = 3;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: [-1, 2, 3, 5], Your output: " + Arrays.toString(SortTools.copyAndInsert(x,n,v)));

        //insertInPlace
        System.out.println("\n\n----- Testing insertInPlace -----");
        x = new int[]{1,3,5,7};
        n = 3;
        v = 4;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 4, Your output: " + SortTools.insertInPlace(x,n,v));
        System.out.println("Post-execution, correct x[] = {1, 3, 4, 5}");
        System.out.println("Post-execution, your x[] = " + Arrays.toString(x) + "\n");

        x = new int[]{1,3,3,7};
        n = 3;
        v = 4;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 4, Your output: " + SortTools.insertInPlace(x,n,v));
        System.out.println("Post-execution, correct x[] = {1, 3, 3, 4}");
        System.out.println("Post-execution, your x[] = " + Arrays.toString(x) + "\n");

        x = new int[]{1,2,4};
        n = 3;
        v = 3;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 4, Your output: " + SortTools.insertInPlace(x,n,v));
        System.out.println("Post-execution, correct x[] = {1, 2, 3}");
        System.out.println("Post-execution, your x[] = " + Arrays.toString(x) + "\n");

        x = new int[]{100};
        n = 1;
        v = -100;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 2, Your output: " + SortTools.insertInPlace(x,n,v));
        System.out.println("Post-execution, correct x[] = {-100}");
        System.out.println("Post-execution, your x[] = " + Arrays.toString(x) + "\n");

        x = new int[]{2,2,2,2};
        n = 4;
        v = 2;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 4, Your output: " + SortTools.insertInPlace(x,n,v));
        System.out.println("Post-execution, correct x[] = {2, 2, 2, 2}");
        System.out.println("Post-execution, your x[] = " + Arrays.toString(x) + "\n");

        x = new int[]{-1, 2, 3, 3};
        v = 4;
        n = 4;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 5, Your output: " + SortTools.insertInPlace(x,n,v));
        System.out.println("Post-execution, correct x[] = {-1, 2, 3, 4}");
        System.out.println("Post-execution, your x[] = " + Arrays.toString(x) + "\n");

        v = 5;
        n = 3;
        System.out.println("x[] = " + Arrays.toString(x) + ", v = "+ v + ", n = "+ n);
        System.out.println("Correct output: 4, Your output: " + SortTools.insertInPlace(x,n,v));
        System.out.println("Post-execution, correct x[] = {-1, 3, 5, ...}");
        System.out.println("Post-execution, your x[] = " + Arrays.toString(x) + "\n");



        System.out.println("\n\n----- Testing insertSort -----");
        x = new int[]{1,5,4,11,6,7};
        n=6;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        SortTools.insertSort(x,n);
        System.out.println("Correct output: [1, 4, 5, 6, 7, 11], Your output: " + Arrays.toString(x));
        x = new int[]{100};
        n=1;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        SortTools.insertSort(x,n);
        System.out.println("Correct output: [100], Your output: " + Arrays.toString(x));
        x = new int[]{10,10,10};
        n=3;
        System.out.println("x[] = " + Arrays.toString(x) + ", n = "+ n);
        SortTools.insertSort(x,n);
        System.out.println("Correct output: [10,10,10], Your output: " + Arrays.toString(x));
}
}