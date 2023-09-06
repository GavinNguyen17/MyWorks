/*
 * Name: Gavin Nguyen
 * EID: Gpn235
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 {
    private ArrayList<Student> students;    // this is a list of all Students, populated by Driver class
    private Heap minHeap;

    // additional constructors may be added, but don't delete or modify anything already here
    public Program2(int numStudents) {
        minHeap = new Heap();
        students = new ArrayList<Student>();
    }

    /**
     * findMinimumStudentCost(Student start, Student dest)
     *
     * @param start - the starting Student.
     * @param dest  - the end (destination) Student.
     * @return the minimum cost possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumStudentCost(Student start, Student dest) {
        // TODO: implement this function
        minHeap.buildHeap(students);                                    //Build MiniHeap
        //Initalize Single Source
         ArrayList<Student> path = new ArrayList<>();                   //Create an arraylist of students
         for(int i = 0; i<students.size();i++){
            minHeap.changeKey(students.get(i), Integer.MAX_VALUE);      //Change all keys in minheap to infinity/max value
         }

            minHeap.changeKey(start, 0);                       // Change the start key to zero
        
        while(minHeap.findMin()!=null){
            Student min = minHeap.extractMin();                       //Extract minimum number from minheap
            path.add(min);                                            //Add min to the path arraylist
            ArrayList<Student> Neighbors = new ArrayList<>(min.getNeighbors()); //Get all the neighbors of the minimum number and put into an array list
            ArrayList<Integer> Prices = new ArrayList<>(min.getPrices());  //Get all the prices from min to its neighbors
            for(int i = 0; i<Neighbors.size();i++){                           //For loop until all neighbors have been visited
                if(Neighbors.get(i).getminCost()>min.getminCost()+Prices.get(i)){      //If current cost of neighbor's mincost is greater than min node's cost plus the wire cost, change neighbor's mincost
                    minHeap.changeKey(Neighbors.get(i), min.getminCost()+Prices.get(i));
                }
            }
        }
        int cost = 0;
         for(int i = 0; i<path.size();i++){                       //Traverse the path arraylist, once the dest name is found return the mincost
            if(path.get(i)==dest){
                cost=path.get(i).getminCost();
            }
            
         }
        return cost;
    }
    
    /**
     * findMinimumClassCost()
     *
     * @return the minimum total cost required to connect (span) each student in the class.
     * Assume the given graph is always connected.
     */
    public int findMinimumClassCost() {
         minHeap.buildHeap(students);                                       //Build Min Heap
        //Initalize Single Source
         int path[] = new int[students.size()];                             //Create an in array to keep track of cost of each node in the path (key = node name, element = min cost)
         int queue[] = new int[students.size()];                            //Create a queue to keep track of what nodes need to be searched
        minHeap.changeKey(students.get(0), 0);                //Set key at index zero to min cost 0
        for(int i=0;i<path.length;i++){                                     //For loop to initalize path array to max value and array queue to 0
            path[i]=Integer.MAX_VALUE;
            queue[i]=0;
        }
        path[0]=0;                                                          //Set path array at the zeroth index to zero
         while(minHeap.findMin()!=null){                                    //While min heap is not empty continue
            Student min = minHeap.extractMin();                             //Extract min
            queue[min.getName()]=-1;                                        //Set queue array at index min name to -1 so we never check that node again
            ArrayList<Student> Neighbors = new ArrayList<>(min.getNeighbors());  //Get all the neighbors near min
            ArrayList<Integer> Prices = new ArrayList<>(min.getPrices());        //Get all wire cost from min to its neighbors
                for(int i =0; i<Neighbors.size();i++){                           //For loop until all neighbors have been visited
                    if((queue[Neighbors.get(i).getName()]!=-1)&&(Prices.get(i)<Neighbors.get(i).getminCost())){  //If neighbor hasn't been removed from the min tree and the price to get to it is less than it's current min cost change it
                        path[Neighbors.get(i).getName()]=Prices.get(i);                                         //Set path array at index min name to it's new min cost
                        minHeap.changeKey(Neighbors.get(i), Prices.get(i));
                    }
                }
                
         }

        int cost = 0;
        for(int i =0;i<path.length;i++){  //Go through the path array and add up all min cost and return the total cost
            cost=cost+path[i];
        }
        // TODO: implement this function
        return cost;
    }

    //returns edges and prices in a string.
    public String toString() {
        String o = "";
        for (Student v : students) {
            boolean first = true;
            o += "Student ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<Student> ngbr = v.getNeighbors();
            for (Student n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with prices ";
            ArrayList<Integer> wght = v.getPrices();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<Student> getAllstudents() {
        return students;
    }

    // used by Driver class to populate each Student with correct neighbors and corresponding prices
    public void setEdge(Student curr, Student neighbor, Integer price) {
        curr.setNeighborAndPrice(neighbor, price);
    }

    // used by Driver.java and sets students to reference an ArrayList of all Students
    public void setAllNodesArray(ArrayList<Student> x) {
        students = x;
    }
}
