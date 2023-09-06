// SortTools.java
/*
 * EE422C Project 1 submission by
 * Replace <...> with your actual data.
 * <Gavin Nguyen>
 * <gpn235>
 * <17180>
 * Spring 2023
 * Slip days used:
 */

package assignment1;

public class SortTools {
    /**
      * Return whether the first n elements of x are sorted in non-decreasing
      * order.
      * @param x is the array
      * @param n is the size of the input to be checked
      * @return true if array is sorted
      */
    public static boolean isSorted(int[] x, int n) {
        // stub only, you write this!
        if(n==0||x.length==0) {
        	return false;
        }
    	for(int i = 1; i<n;i++) {
    		if(x[i]<x[i-1]) {				// If the number in last number is bigger than the current number it is false
    			return false;
    		}
    	}
    	return true;
        
    }

    /**
     * Return an index of value v within the first n elements of x.
     * @param x is the array
     * @param n is the size of the input to be checked
     * @param v is the value to be searched for
     * @return any index k such that k < n and x[k] == v, or -1 if no such k exists
     */
    public static int find(int[] x, int n, int v) {
        // stub only, you write this!
        // TODO: complete it
    	
    	//Using binary search to find if the number is in the array since binary search is log(N)
    	int high = n-1;
    	int low = 0;
    	int  mid =0;
    	while(low<=high) {
    		mid = (high+low)/2;
    		 if(x[mid]<v) {
    			 low = mid+1;
    		 }
    		 else if(x[mid]>v) {
    			 high = mid-1;
    		 }
    		 else return mid;
    	}								
        return -1;
    }

    /**
     * Return a sorted, newly created array containing the first n elements of x
     * and ensuring that v is in the new array.
     * @param x is the array
     * @param n is the number of elements to be copied from x
     * @param v is the value to be added to the new array if necessary
     * @return a new array containing the first n elements of x as well as v
     */
    public static int[] copyAndInsert(int[] x, int n, int v) {
        // stub only, you write this!
        // TODO: complete it
    	int temp=0, temp2=v;
    	if((SortTools.find(x, n, v)==-1)){	
    		int[] a = new int[n+1];
    		if(n==0) {
    			a[0]=v;
    			return a;
    		}
    		//array a gets all the contents of array x
    		for(int k = 0; k<n;k++) {
    			a[k]=x[k];
    		}
    		//puts array in order
    	for(int i = 0; i<a.length;i++) {
    		if(a[i]>=temp2||i==(n)) {
            	temp=a[i];
    			a[i]=temp2;
    			temp2=temp;
            	}
    	}
        return a;
    }
    	//if v is already in the array up to the n index then copy only the numbers up to n
    	else {
    		int[] a = new int[n];
    		for(int k = 0; k<n;k++) {
    			a[k]=x[k];
    		}
    		return a;
    	}
    }

    /**
     * Insert the value v in the first n elements of x if it is not already
     * there, ensuring those elements are still sorted.
     * @param x is the array
     * @param n is the number of elements in the array
     * @param v is the value to be added
     * @return n if v is already in x, otherwise returns n+1
     */
    public static int insertInPlace(int[] x, int n, int v) {
        // stub only, you write this!
        // TODO: complete it
    	// uses the find tool to check if v is in array x returns n if there is
    	if(SortTools.find(x, n, v)>-1) {
    		return n;
    	}
    	else {
    		//puts array in order
    	int temp=0, temp2=v;
        for(int i=0;i<x.length;i++ ) {
        	if(x[i]>=temp2||i==(n)) {
        	temp=x[i];
			x[i]=temp2;
			temp2=temp;
        	}
        	
        }
        //returns n+1 is v is not in array x
        return n+1;
        }
    }

    /**
     * Sort the first n elements of x in-place in non-decreasing order using
     * insertion sort.
     * @param x is the array to be sorted
     * @param n is the number of elements of the array to be sorted
     */
    public static void insertSort(int[] x, int n) {
        // stub only, you write this!
        // TODO: complete it
    	//compares indexes and swaps them if index+1<index
    	for(int i = 1; i<n; i++) {
    		int temp = x[i];
    		
    		for(int j= i-1;j>=0 && x[j]>temp;j--) {
    			x[j+1]=x[j];
    			x[j]=temp;
    			
    		}
    		
    	}

    }
}
