/*
 * Name: Gavin Nguyen
 * EID: Gpn235
 */

// Implement your heap here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Heap {
    private ArrayList<Student> minHeap;

    public Heap() {
        minHeap = new ArrayList<Student>();
    }

    /**
     * buildHeap(ArrayList<Student> students)
     * Given an ArrayList of Students, build a min-heap keyed on each Student's minCost
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param students
     */
    public void buildHeap(ArrayList<Student> students) {
        // TODO: implement this method
        int size = students.size();                         //Set size to student size
        for(int i =0 ;size!=0;i++){                         //For loop while size doesn't equal zero
            if(minHeap.isEmpty()){                          //If min heap is empty add the first tudent to heap and set its index to 0
                minHeap.add(0,students.get(i));
                minHeap.get(0).setIndex(i);
                size--;                                     //Reduce size by 1
            }
            
                int l = (i*2)+1;                            //Left node is (index*2)+1
                int r = (i*2)+2;                            //Right node is (index*2)+2
                 if(size==0){                               //If size is 0 leave
                    break;
                }
                insertNode(students.get(l));                //Place next student into the (index*2)+1 index
                size--;                                     //Reduce size by 1
                if(size==0){                                //If size is 0 leave
                    break;
                }
                insertNode(students.get(r));                //Insert next student into (index*2)+2 index
                size--;                                     //Reduce size by 1
            
        }
    }

    public void HeapifyUp(Student root, Student currnum, int rootlocation, int currloaction){
        minHeap.set(currloaction, root);                                            //Switch current student with the old root, and switch their indexes
        minHeap.get(currloaction).setIndex(currloaction);
        minHeap.set(rootlocation, currnum);
        minHeap.get(rootlocation).setIndex(rootlocation);
        if(rootlocation==0){                                                        //If the root location was 0 then return since there is nothing above it
            return;
        }
        if((rootlocation)%2!=0){                                                    //If the rootlocation is odd then find its root by doing its (index-1)/2
           
            int student = minHeap.get((rootlocation-1)/2).getminCost();
            if(student>currnum.getminCost()){                                      //If current cost is less than the root cost heapify up
            HeapifyUp(minHeap.get((rootlocation-1)/2), currnum, (rootlocation-1)/2, rootlocation);
            }
        }
        else{
            int student = minHeap.get((rootlocation-2)/2).getminCost();           //If the rootlocation is odd then find its root by doing its (index-2)/2
             if(student>currnum.getminCost()){                                    //If current cost is less than the root cost heapify up
            HeapifyUp(minHeap.get((rootlocation-2)/2), currnum, (rootlocation-1)/2, rootlocation);
            }
        }
        return;

    }

    public void HeapifyDown(Student child, Student currnum, int childlocation, int currloaction){
        minHeap.set(currloaction, child);                       //Swap the current student with the one below it, and change its index
        minHeap.get(currloaction).setIndex(currloaction);
        minHeap.set(childlocation, currnum);
        minHeap.get(childlocation).setIndex(childlocation);
        int lnode = (childlocation*2)+1;                        //Get the index of the left child
        int rnode = (childlocation*2)+2;                        //Get the index of the right child
        if(lnode>=minHeap.size()){                              //If left child index is greater than minHeap size leave
            return;
        }
        int lvalue=minHeap.get(lnode).getminCost();             //Set lvalue to the mincost of the left child
        int rvalue=-1;                                          //Set rvalue to -1
         if(rnode<minHeap.size()){
            
             rvalue=minHeap.get(rnode).getminCost();            //If rnode is less than minHeap size make rvalue the mincost of right child
        }
        
         
        if(currnum.getminCost()>lvalue||(currnum.getminCost()>rvalue&&rvalue!=-1)){   //If current student min is greater than lvalue or greater than rvalue (only if rvalue does not equal -1) then heapify down
            if(lvalue<rvalue||rvalue==-1){
                HeapifyDown(minHeap.get(lnode), currnum, lnode, childlocation);
            }
            else{
                HeapifyDown(minHeap.get(rnode), currnum, rnode, childlocation);
            }
        }
    }
    /**
     * insertNode(Student in)
     * Insert a Student into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the Student to insert.
     */
    public void insertNode(Student in) {
        // TODO: implement this method
        minHeap.add(in);                                             //Add student into the heap arraylist
        minHeap.get(minHeap.size()-1).setIndex(minHeap.size()-1);    //Set student index to minHeapSize-1
        int newlocation = minHeap.size()-1;                          //Get the student's index
        if((newlocation)%2!=0){                                      //If the new index is odd
           
            int root = minHeap.get((newlocation-1)/2).getminCost();  //Find the root of the current student by doing (index-1)/2
            if(root>in.getminCost()){                                //If root is greater than the student's cost then heapify up
            HeapifyUp(minHeap.get((newlocation-1)/2), in, (newlocation-1)/2, newlocation);
            }
        }
        else{                                                        //If the new index is even
            int root = minHeap.get((newlocation-2)/2).getminCost();  //Find the root of the current student by doing (index-2)/2
             if(root>in.getminCost()){                               //If root is greater than the sudent cost then heapify up
            HeapifyUp(minHeap.get((newlocation-2)/2), in, (newlocation-1)/2, newlocation);
            }
        }

        
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public Student findMin() {
        // TODO: implement this method
        if(minHeap.isEmpty()){                      //If heap is empty return null
        return null;
        }
        return minHeap.get(0);               //Return grab the integer at index 0
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public Student extractMin() {
        // TODO: implement this method
        if(minHeap.isEmpty()){              //If heap empty return null
        return null;
        }
        Student min = findMin();            //Find minimum
        delete(min.getIndex());             //Delete minimum
        return min;                         //Return minimum
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        // TODO: implement this method
        if(minHeap.size()==0){                          //If minheap size is zero leave
            return;
        }
        if(index==minHeap.size()-1){                    //If the element that is being deleted is the last element delete it and leave
            minHeap.remove(index);
        }
        else{                                           
            Student laststudent = minHeap.get(minHeap.size()-1); //Grab the last student and move it to the current index that is being deleted 
            minHeap.set(index, laststudent);
            minHeap.get(index).setIndex(index);
            minHeap.remove(minHeap.size()-1);                   //Remove the last node
           
            int lnode = (index*2)+1;                            //Get the next left node
            int rnode = (index*2)+2;                            //Get the next right node
            int lvalue=-1;                                      //Set current l value to -1
             if(lnode<minHeap.size()){                          //If left node is in the min heap then get its value
            lvalue=minHeap.get(lnode).getminCost();
             }
            int rvalue=-1;                                      //Set current r value to -1
            if(rnode<minHeap.size()){                           //If right node is in the min heap then get its value
            rvalue=minHeap.get(rnode).getminCost();
            }
            if((laststudent.getminCost()>lvalue && lvalue!=-1)||(laststudent.getminCost()>rvalue&& rvalue!=-1)){ //If the value of the left or right node is less than the student that was just moved and the left and/or right node is not zero move the node 
                if(lvalue<rvalue||rvalue==-1){                                                                   //If left node is less than right node or right node is -1 then heapify down the left node
                    HeapifyDown(minHeap.get(lnode), laststudent, lnode, index);
                }
                else{
                    HeapifyDown(minHeap.get(rnode), laststudent, rnode, index);                                 //If right node is less than left node then heapify down right node
                }
            }


            }
    }

    /**
     * changeKey(Student r, int newCost)
     * Changes minCost of Student s to newCost and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the Student in the heap that needs to be updated.
     * @param newCost - the new cost of Student r in the heap (note that the heap is keyed on the values of minCost)
     */
    public void changeKey(Student r, int newCost) {
        // TODO: implement this method
        int studentindex= r.getIndex();
        minHeap.get(studentindex).setminCost(newCost);
        int lnode = (studentindex*2)+1;
        int rnode = (studentindex*2)+2;
         int lvalue=-1;
          int rvalue=-1;
        if(lnode<minHeap.size()){
         lvalue=minHeap.get(lnode).getminCost();
        }
        if(rnode<minHeap.size()){
         rvalue=minHeap.get(rnode).getminCost();
        }

        if((r.getminCost()>lvalue&&lvalue!=-1)||(r.getminCost()>rvalue&&rvalue!=-1)){
            if(lvalue<rvalue||rvalue==-1){
                HeapifyDown(minHeap.get(lnode), r, lnode, r.getIndex());
            }
            else{
                 HeapifyDown(minHeap.get(rnode), r, rnode, r.getIndex());
            }
        }

        else if(studentindex!=0){
         if((studentindex)%2!=0){
            int root = minHeap.get((studentindex-1)/2).getminCost();
            if(root>r.getminCost()){
            HeapifyUp(minHeap.get((studentindex-1)/2), r, (studentindex-1)/2, studentindex);
            }
        }
        else{
            int root = minHeap.get((studentindex-2)/2).getminCost();
             if(root>r.getminCost()){
            HeapifyUp(minHeap.get((studentindex-2)/2), r, (studentindex-1)/2, studentindex);
            }
        }
    }

        
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<Student> toArrayList() {
        return minHeap;
    }
}
