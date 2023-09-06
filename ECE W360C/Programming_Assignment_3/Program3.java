/*
 * Name: Gavin Nguyen
 * EID: Gpn235
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


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
public class Program3 extends AbstractProgram3 {

    /**
     * Determines the solution of the optimal response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "responseTime" field set to the optimal response time
     */
    @Override
    public TownPlan findOptimalResponseTime(TownPlan town) {
        /* TODO implement this function */
        ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> TownArray = new ArrayList();         //Create a 3D array with House, Station, and a place to put time and postion 
        for(int i =0;i<town.getHouseCount();i++){
        TownArray.add(new ArrayList<>());
            for(int j =0; j<town.getStationCount();j++){
                TownArray.get(i).add(new ArrayList<>());

            }
        }
        int min = Integer.MIN_VALUE;                                                            //Make an int min that is set to -inf for finding the min time
        
        ArrayList<Integer> positions = new ArrayList<>(town.getHousePositions());               //Create an arraylist positions to get all the house positions
        for(int i = 0;i<town.getStationCount();i++){                                            //Create a For loop i to number of stations
            int numstations=i+1;                                                                //Get the number of stations by getting i and adding 1 since the code is zero indexed
            
            for(int j = 0;j<town.getHouseCount();j++){                                          //Create a for loop j to number of houses
                
                if(i>=j){                                                                       //If the number of stations is greater than or equal to the number of houses then position that stations at each house and make response time zero
                       ArrayList<Integer> stationlocation = new ArrayList<>();
                        for(int k = 0; k<j+1;k++){
                            stationlocation.add(positions.get(k));
                        }
                        stationlocation.add(0,0);                               // Set response time to the zeroth element in the 3D array and the positions in the subsequent keys (house)->(station)->(response time, position(1),position(2),...,(position(i)))
                        TownArray.get(j).get(i).add(stationlocation);
                        
                    }
                     else if(numstations==1){                                                   //Else If number of stations equals 1 then find the response time by doing getting the jth house minus first house divided by two, to get the postion do first house plus jth house divided by two
                     ArrayList<Integer> stationlocation = new ArrayList<>();
                    int low = positions.get(0);
                    int high = positions.get(j);
                    int time = (high-low)/2;
                    int stationposition = (high+low)/2;
                    stationlocation.add(time);
                    stationlocation.add(stationposition);
                    
                    TownArray.get(j).get(i).add(stationlocation);                               // Set response time to the zeroth element in the 3D array and the positions in the subsequent keys (house)->(station)->(response time, position(1),position(2),...,(position(i)))
                }
                else if(numstations>1){                                             //Else if the number of stations is greater than 1
                        ArrayList<Integer> MinArray=new ArrayList<>();              // Create a new arraylist called MinArray
                         ArrayList<Integer> stationlocation = new ArrayList<>();    //Create a new arraylist called stationlocation
                          min=Integer.MIN_VALUE;                                    // Set min to -inf
                         for(int k = 0; k<j;k++){                                   //Create a for loop from k=0 to j
                        
                        int PrevMaxTime = TownArray.get(k).get(i-1).get(0).get(0); //Get the previous max time by getting the using the Townarray at k,i-1, at the zeroth element
                        int high = positions.get(j);                                           //The current last house is at the jth location
                        int low = positions.get(k+1);                                          //The current first house is at k+1
                        int time = (high - low)/2;                                              //Find the time by doing (high-low)/2
                         
                        if(time<PrevMaxTime){                                                   //If the previous time is greater than the current time then make time the previous time
                            time = PrevMaxTime;
                        }
                        if(min>time||min==Integer.MIN_VALUE){                                   //If min is greater than current time then make min current time
                            min=time;
                            MinArray.clear();
                            for(int l=0;l<TownArray.get(k).get(i-1).get(0).size();l++){   //Set MinArray to current TownArray at k,i-1
                                MinArray.add(TownArray.get(k).get(i-1).get(0).get(l));
                            }
                            MinArray.set(0, min);                                         //Set min response time to the zeroth element in the min array
                            MinArray.add((high+low)/2);                                         //Add the new station position
                            
                        }
                       
                         }
                     stationlocation=MinArray;                                                //Once the for loop is complete place MinArray into the TownArray at location, j,i
                             TownArray.get(j).get(i).add(stationlocation);
                }
               
                }
            }
            int house = town.getHouseCount()-1;
            int station = town.getStationCount()-1;
            
           
            town.setResponseTime(TownArray.get(house).get(station).get(0).get(0));  //Return the resonse time of HouseCount, StationCount
            return town;

        }
        
        
    

    /**
     * Determines the solution of the set of police station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "policeStationPositions" field set to the optimal police station positions
     */
    @Override
    public TownPlan findOptimalPoliceStationPositions(TownPlan town) {
        /* TODO implement this function */
         ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> TownArray = new ArrayList();         //Create a 3D array with House, Station, and a place to put time and postion 
        for(int i =0;i<town.getHouseCount();i++){
        TownArray.add(new ArrayList<>());
            for(int j =0; j<town.getStationCount();j++){
                TownArray.get(i).add(new ArrayList<>());

            }
        }
        int min = Integer.MIN_VALUE;                                                            //Make an int min that is set to -inf for finding the min time
        
        ArrayList<Integer> positions = new ArrayList<>(town.getHousePositions());               //Create an arraylist positions to get all the house positions
        for(int i = 0;i<town.getStationCount();i++){                                            //Create a For loop i to number of stations
            int numstations=i+1;                                                                //Get the number of stations by getting i and adding 1 since the code is zero indexed
            
            for(int j = 0;j<town.getHouseCount();j++){                                          //Create a for loop j to number of houses
                
                if(i>=j){                                                                       //If the number of stations is greater than or equal to the number of houses then position that stations at each house and make response time zero
                       ArrayList<Integer> stationlocation = new ArrayList<>();
                        for(int k = 0; k<j+1;k++){
                            stationlocation.add(positions.get(k));
                        }
                        stationlocation.add(0,0);                               // Set response time to the zeroth element in the 3D array and the positions in the subsequent keys (house)->(station)->(response time, position(1),position(2),...,(position(i)))
                        TownArray.get(j).get(i).add(stationlocation);
                        
                    }
                     else if(numstations==1){                                                   //Else If number of stations equals 1 then find the response time by doing getting the jth house minus first house divided by two, to get the postion do first house plus jth house divided by two
                     ArrayList<Integer> stationlocation = new ArrayList<>();
                    int low = positions.get(0);
                    int high = positions.get(j);
                    int time = (high-low)/2;
                    int stationposition = (high+low)/2;
                    stationlocation.add(time);
                    stationlocation.add(stationposition);
                    
                    TownArray.get(j).get(i).add(stationlocation);                               // Set response time to the zeroth element in the 3D array and the positions in the subsequent keys (house)->(station)->(response time, position(1),position(2),...,(position(i)))
                }
                else if(numstations>1){                                             //Else if the number of stations is greater than 1
                        ArrayList<Integer> MinArray=new ArrayList<>();              // Create a new arraylist called MinArray
                         ArrayList<Integer> stationlocation = new ArrayList<>();    //Create a new arraylist called stationlocation
                          min=Integer.MIN_VALUE;                                    // Set min to -inf
                         for(int k = 0; k<j;k++){                                   //Create a for loop from k=0 to j
                        
                        int PrevMaxTime = TownArray.get(k).get(i-1).get(0).get(0); //Get the previous max time by getting the using the Townarray at k,i-1, at the zeroth element
                        int high = positions.get(j);                                           //The current last house is at the jth location
                        int low = positions.get(k+1);                                          //The current first house is at k+1
                        int time = (high - low)/2;                                              //Find the time by doing (high-low)/2
                         
                        if(time<PrevMaxTime){                                                   //If the previous time is greater than the current time then make time the previous time
                            time = PrevMaxTime;
                        }
                        if(min>time||min==Integer.MIN_VALUE){                                   //If min is greater than current time then make min current time
                            min=time;
                            MinArray.clear();
                            for(int l=0;l<TownArray.get(k).get(i-1).get(0).size();l++){   //Set MinArray to current TownArray at k,i-1
                                MinArray.add(TownArray.get(k).get(i-1).get(0).get(l));
                            }
                            MinArray.set(0, min);                                         //Set min response time to the zeroth element in the min array
                            MinArray.add((high+low)/2);                                         //Add the new station position
                            
                        }
                       
                         }
                     stationlocation=MinArray;                                                //Once the for loop is complete place MinArray into the TownArray at location, j,i
                             TownArray.get(j).get(i).add(stationlocation);
                }
               
                }
            }
            int house = town.getHouseCount()-1;
            int station = town.getStationCount()-1;
            ArrayList<Integer> stationArray = new ArrayList<>();                            //Create a stationArray ArrayList
           for(int i =1;i<town.getStationCount()+1;i++){                                    //Get all the station positions
            stationArray.add(TownArray.get(house).get(station).get(0).get(i));
           }
            town.setPoliceStationPositions(stationArray);
            
        return town;                                                                        //Return positions
    }
}
