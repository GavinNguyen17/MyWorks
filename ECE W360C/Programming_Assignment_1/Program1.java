/*
 * Name: Gavin Nguyen
 * EID: GPN235
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {


    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        /* TODO implement this function */
        
        ArrayList Matching = problem.getStudentMatching();
         //System.out.println(Matching);
        /*Student pairing is (S,H) this function checks is there is an S' that H wants more than
          S where S' is not paired to any school (-1), if true the matching is unstable   
        */
        for(int currentstudent = 0; currentstudent<Matching.size();currentstudent++){
            int SchoolNum = (Integer) Matching.get(currentstudent);                               // Checks what school the current student is in
            if(SchoolNum!=-1){
            ArrayList HPref = problem.getHighSchoolPreference().get(SchoolNum);
            int ListOrder = HPref.indexOf(currentstudent);                                       // Gets the High School Preference index of the current student
            if(ListOrder!=0){                                                                    // Ensures the student they are looking at is not already their first choice
                for(int j = ListOrder-1;j>0;j--){                                                // Starts at the index of the current student and goes backwards to see if there are any students that the school wanted more than the current student
                    int MorePerferedStudent = (Integer) HPref.get(j);
                    int AttendingSchool = (Integer) Matching.get(j);                               
                    if(Matching.get(MorePerferedStudent).equals(-1)){                                    // If the student they want more than the current student they have is not in a school (-1) than this Matching is unstable
                    return false;
                        }
                    }
                }   
            }
        }

        /*Student pairing is (S,H) this function checks if there is an S' that H wants
         * more than S, and Checks if S' wants H more than its current pairing,
         * if both are true than the matching is unstable
          */
        for(int currentstudent = 0; currentstudent<Matching.size();currentstudent++){
            int SchoolNum = (Integer) Matching.get(currentstudent);                               // Checks what school the current student is in
            if(SchoolNum!=-1){
            ArrayList HPref = problem.getHighSchoolPreference().get(SchoolNum);
            int ListOrder = HPref.indexOf(currentstudent);                                       // Gets the High School Preference index of the current student
            if(ListOrder!=0){                                                                    // Ensures the student they are looking at is not already their first choice
                for(int j = ListOrder-1;j>0;j--){                                                // Starts at the index of the current student and goes backwards to see if there are any students that the school wanted more than the current student
                    int MorePerferedStudent = (Integer) HPref.get(j);
                    int AttendingSchool = (Integer) Matching.get(MorePerferedStudent);
                    ArrayList SPref = problem.getStudentPreference().get(MorePerferedStudent);                               
                    if(SPref.indexOf(AttendingSchool)>SPref.indexOf(SchoolNum)){                 // If the student wants the current school more than they want the school they are currently paired with than the matching is unstable
                    return false;
                        }
                    }
                }   
            }
        }

        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {
        /* TODO implement this function */
         int numofschools=problem.getHighSchoolCount();
        int numofstudents = problem.getStudentCount();
        int totalspots = problem.totalHighSchoolSpots();
        ArrayList<Integer> Students =new ArrayList<>();
        ArrayList<Integer> Rankings = new ArrayList<>();
        HashMap<Integer,Integer> Hash = new HashMap();
        for(int i=0;i<numofstudents;i++){                               //Create an arraylist where all elements are initalized to -1, and all the indexes represent the students
            Rankings.add(-1);
         }

         for(int i=0;i<numofschools;i++){                               //Create an arraylist of hashmaps where the hashmaps contain the School and the number of spots in the school (School, Spots)
            int currspots = problem.getHighSchoolSpots().get(i);
            Hash.put(i,currspots);
         }
      
         for(int i=0;i<numofstudents;i++){                               //Create an arraylist of all the students
            Students.add(i);
         }
        while(!Students.isEmpty()){
             
            int currStudent = Students.remove(0);                 //Grab the first student from the Students ArrayList and remove them from the ArrayList

            for(int i=0; i<problem.getStudentPreference().get(currStudent).size()&&Rankings.get(currStudent)==-1;i++){ //Iterate the current students's preference list
                int PerferedSchool = (Integer) problem.getStudentPreference().get(currStudent).get(i);      //Gets the perfered schhol from the current students preference list at index i
                if(Hash.get(PerferedSchool)>0){                                                             //If perfered school still has spots then add school
                    Rankings.set(currStudent, PerferedSchool);
                    int spots = Hash.get(PerferedSchool);
                    spots--; 
                    totalspots--;                                                                           //Decrease number of total spots
                    Hash.replace(PerferedSchool,spots);                                                     //Decrease number of spots
                    break;
            }
            else{
                for(int j =0; j<Rankings.size();j++){                                                       //Go through all the elements in the ranking list to see to find the perfered school
                    
                    if(Rankings.get(j)==PerferedSchool){
                    if(problem.getHighSchoolPreference().get(PerferedSchool).indexOf(currStudent)<problem.getHighSchoolPreference().get(PerferedSchool).indexOf(j)){  //Compare the two schools
                        Students.add(j);    //Add previous student back into student array
                        Rankings.set(j,-1);
                        Rankings.set(currStudent, PerferedSchool);
                        break;  //Break out of the for loop
                    }
                }
                    
                    else{}
                    
                }
            }
        }
    }
        

        problem.setStudentMatching(Rankings);
        return problem;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_highschooloptimal(Matching problem) {
        /* TODO implement this function */
        int numofschools=problem.getHighSchoolCount();
        int numofstudents=problem.getStudentCount();
        int totalspots = problem.totalHighSchoolSpots();
        ArrayList<Integer> Schools =new ArrayList<>();
        ArrayList<Integer> Rankings = new ArrayList<>();
        HashMap<Integer,Integer> SpotsHash = new HashMap();

        for(int i=0;i<numofstudents;i++){                               //Create an arraylist where all elements are initalized to -1, and all the indexes represent the students
            Rankings.add(-1);
         }

         for(int i=0;i<numofschools;i++){                               //Create an arraylist of hashmaps where the hashmaps contain the School and the number of spots in the school (School, Spots)
            int currspots = problem.getHighSchoolSpots().get(i);
            SpotsHash.put(i,currspots);
            Schools.add(i);
         }
         while(totalspots!=0){
            int currSchool= Schools.remove(0);                    //Remove the first school from the School arraylist
            int spots = SpotsHash.get(currSchool);                      //Get the current number of spots for that school
          
            for(int i=0; i<problem.getHighSchoolPreference().get(currSchool).size() && totalspots!=0&& spots!=0;i++){      //Iterate the current school's preference list
                int PeferedStudent = (Integer) problem.getHighSchoolPreference().get(currSchool).get(i);                   //Get the perfered student at index i in the school's preference list
                if(Rankings.get(PeferedStudent).equals(-1)){                                                               //If the Rankings array has the element -1 at index i the place the PerferedStudent in the index
                    Rankings.set(PeferedStudent, currSchool);
                    spots--;
                    SpotsHash.replace(currSchool, spots);                                                                  //Reduce the number of spots that school has
                    totalspots--;                                                                                          //Reduce total number of spots
                    if(spots==0){                                                                                          //If there is no more spots break the loop
                        break;
                    }
                }
                else{
                   
                    if(problem.getStudentPreference().get(PeferedStudent).indexOf(Rankings.get(PeferedStudent))>problem.getStudentPreference().get(PeferedStudent).indexOf((currSchool))&& Rankings.get(PeferedStudent)!=currSchool){   //Compare the two school using the student's preference list if the paried school index is greater than the current school proposing then switch schools
                    
                        int TempSpots = SpotsHash.get(Rankings.get(PeferedStudent));
                        TempSpots=TempSpots+1;
                        SpotsHash.replace(Rankings.get(PeferedStudent),TempSpots );   //Increase the number of spots the previous paried school has
                        Schools.add(Rankings.get(PeferedStudent));                    //Add previous school back into Schools ArrayList
                        Rankings.set(PeferedStudent, currSchool);
                        spots--;
                        SpotsHash.replace(currSchool, spots);
                        if(spots==0){
                            break;
                        }
                        
                    }
                    else{};
                }
            }
         }
        
        problem.setStudentMatching(Rankings);
       return problem;
    }
}