/*
 * Name: Gavin Nguyen
 * EID: Gpn235
 */

// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.*;

public class Student {
    private int minCost;
    private int name;
    private int parent;
    private int index;
    private ArrayList<Student> neighbors;
    private ArrayList<Integer> prices;

    public Student(int x) {
        name = x;
        minCost = Integer.MAX_VALUE;
        neighbors = new ArrayList<Student>();
        prices = new ArrayList<Integer>();
    }

    public void setNeighborAndPrice(Student n, Integer w) {
        neighbors.add(n);
        prices.add(w);
    }
    public void setIndex(int i) {
        index = i;
    }

    public void setparent(int p){
        parent = p;
    }
    public int getparent(){
        return parent;
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<Student> getNeighbors() {
        return neighbors;
    }

    public ArrayList<Integer> getPrices() {
        return prices;
    }

    public int getminCost() { return minCost; }

    public void setminCost(int x) {
        minCost = x;
    }

    public void resetminCost() {
        minCost = Integer.MAX_VALUE;
    }

    public int getName() {
        return name;
    }
}
