package assignment6;

import assignment6.Seat.SeatLetter;
import assignment6.Seat.SeatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Cinema {
	public Map<String, SeatType[]> booths;
	public MovieTheater movieTheater;
	public List<String> boothIDS = new ArrayList<>();

    /**
     * Constructor to initilize the simulation based on starter parameters. 
     * 
     * @param booths maps ticket booth id to seat type preferences of customers in line.
     * @param movieTheater the theater for which tickets are sold.
     */
    public Cinema(Map<String, SeatType[]> booths, MovieTheater movieTheater) {
        // TODO: Implement this constructor
    	this.booths = booths;
    	this.movieTheater = movieTheater;
    	//Array list of the different booths
    	for(String i : booths.keySet()) {
    		boothIDS.add(i);
    	}
    }

    /**
     * Starts the ticket office simulation by creating (and starting) threads
     * for each ticket booth to sell tickets for the given movie.
     *
     * @return list of threads used in the simulation,
     *   should have as many threads as there are ticket booths.
     */
    public List<Thread> simulate() {
    	
        // TODO: Implement this method.
    	List<Thread> threads = new ArrayList<>();
    	Stack<Integer> CustomerID =new Stack<>();
    	for(int i = 0; i<booths.size();i++) {
    		String boothID = boothIDS.get(i);
    		Object lock = new Object();
    		Thread CurrentThread = new Thread() {
    			@Override
    			public void run() {
    				
    				for(SeatType SeatT : booths.get(boothID)) {
    					Seat nextAvaliable = null;
    					//Finds next available seat
    					nextAvaliable=movieTheater.getNextAvailableSeat(SeatT);
    					//Get the next customerID
    					synchronized(movieTheater.totalSeat) {
        					if(nextAvaliable!=null) {
        						if(CustomerID.isEmpty()) {
    	    						CustomerID.add(1);
    	    					}
    	    					else {
    	    						int nextID = CustomerID.peek();
    	    						for(;CustomerID.contains(nextID);nextID++) {}
    	    						CustomerID.add(nextID);
    	    					}
    						}
    					}
    					//Prints the ticket
    					synchronized(lock) {
    						if(nextAvaliable!=null) {
    						movieTheater.printTicket(boothID, nextAvaliable, CustomerID.peek());
    							}
    						}
    				
    				}
    			
    			}
    		};
    
    		CurrentThread.start();
    		threads.add(CurrentThread);
    	}
        return threads;
    }


    	public static void main(String[] args) {
    		//Test 1
    		//Original test
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT });
//    		booths.put("TO3", new SeatType[] { SeatType.COMFORT, SeatType.STANDARD,
//    		SeatType.STANDARD });
//    		booths.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.COMFORT,
//    		SeatType.STANDARD, SeatType.STANDARD });
//    		booths.put("TO5", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT });
//    		booths.put("TO4", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,
//    		SeatType.STANDARD });
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 1, 1));
//    		client.simulate();
//    		
    		//Test 2
    		//Like TEst 1 but more seats
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3", new SeatType[] { SeatType.COMFORT, SeatType.STANDARD,
//    		SeatType.STANDARD });
//    		booths.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.COMFORT,
//    		SeatType.STANDARD, SeatType.STANDARD });
//    		booths.put("TO5", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO4", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,
//    		SeatType.STANDARD,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 1, 1));
//    		client.simulate();
    		
    		
    		//Test 3
    		// Full house each seat type has 1 row, only 1 booth
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,
//    		SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.STANDARD,SeatType.STANDARD,
//    		SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD,});
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 1, 1));
//    		client.simulate();
    		
    		//Test 4
    		//No Standard Seats
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,
//    		SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.STANDARD,SeatType.STANDARD,
//    		SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD,});
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 1, 0));
//    		client.simulate();
    		
    		//Test 5
    		//People wanna go see a movie but the theater doesn't exist
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,SeatType.COMFORT,
//    		SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.STANDARD,SeatType.STANDARD,
//    		SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD,});
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(0, 0, 0));
//    		client.simulate();
    		
    		//Test 6
    		//Morbius is in theaters
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] {});
//    		booths.put("TO2", new SeatType[] {});
//    		booths.put("TO3", new SeatType[] {});
//    		booths.put("TO4", new SeatType[] {});
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 1, 1));
//    		client.simulate();
    		
    		//Test 7
    		//Max people in theater for each seat type has 2 rows
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3", new SeatType[] { SeatType.COMFORT, SeatType.STANDARD,
//    		SeatType.STANDARD });
//    		booths.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.COMFORT,
//    		SeatType.STANDARD, SeatType.STANDARD });
//    		booths.put("TO5", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO4", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,
//    		SeatType.STANDARD,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		Cinema client = new Cinema(booths, new MovieTheater(2, 2, 2));
//    		client.simulate();
    		
    		//Test 8
    		//Alot of seats
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT, SeatType.COMFORT, SeatType.COMFORT,
//    		SeatType.COMFORT });
//    		booths.put("TO3", new SeatType[] {SeatType.STANDARD,
//    		SeatType.STANDARD,SeatType.STANDARD,
//    		SeatType.STANDARD,SeatType.STANDARD,
//    		SeatType.STANDARD });
//    		booths.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		Cinema client = new Cinema(booths, new MovieTheater(5, 5, 5));
//    		client.simulate();
    		
    		//Test 9
    		//Theater has different seats but everyone wants the same seat
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3", new SeatType[] {SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 1, 1));
//    		client.simulate();
    		
    		//Test 10
    		//There are only rumble seats and eveyone wants one
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3", new SeatType[] {SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 0, 0));
//    		client.simulate();
    		
    		//Test 11
    		//There are only comfort seat
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(0, 1, 0) );
//    		client.simulate();
    		
			
    		
    		//Test 12
    		//A lot of test
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO4", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO5", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TO6", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO7", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO8", new SeatType[] {SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO299", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO1s", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3s", new SeatType[] {SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TOd2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("dTO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TOa3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TO2w", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TfO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TOa2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TOd1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TOw3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TOq2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TOb1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TOxzc3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TOasd2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TqdO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TddO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TOqd2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TewO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TdwO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T45O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO331", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO113", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T1d1O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("T33O1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TdsdO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T2d3O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO2311", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO1233", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TOdd2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TO1we", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3ha", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TOdas2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("TcO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("T[O3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T`O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("Td`O1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("Td`dO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("TOasd2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE});
//    		booths.put("TO``d1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("T22O3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T67O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("T77O1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("T90O3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T1O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("T2O1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("T3O3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T4O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("T5O1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("T6O3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("T7O2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("T8O1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("T9O3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("1TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("23TO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("3TO3", new SeatType[] {SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("4TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("5TO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("6TO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("7TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("8TO1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("9TO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("1aTO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("2aTO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("a3TO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("3aTO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("4sTO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("6dTO3", new SeatType[] {SeatType.COMFORT, SeatType.COMFORT,SeatType.COMFORT,
//    				SeatType.RUMBLE,SeatType.COMFORT,SeatType.COMFORT });
//    		booths.put("adsTO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("wwwTO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("123dTO3", new SeatType[] {SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.COMFORT,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.COMFORT, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.COMFORT,
//    				SeatType.COMFORT,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.COMFORT,SeatType.RUMBLE,SeatType.COMFORT, SeatType.COMFORT,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		booths.put("asfTO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.RUMBLE });
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(90, 0, 0));
//    		client.simulate();
    		
    		//Test 13
    		//There are only comfort seats and eveyone wants rumble
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO3", new SeatType[] {SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		booths.put("TO2", new SeatType[] { SeatType.RUMBLE, SeatType.RUMBLE,SeatType.RUMBLE,
//    				SeatType.RUMBLE,SeatType.RUMBLE,SeatType.RUMBLE });
//    		Cinema client = new Cinema(booths, new MovieTheater(0, 1, 0));
//    		client.simulate();
//    		//Test 14
//    		//People want standard but there are only comfort
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		booths.put("TO3", new SeatType[] {SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		booths.put("TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(0, 1, 0) );
//    		client.simulate();
    		
    		//Test 15
    		//People want standard but there are only rumble
    		
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		booths.put("TO3", new SeatType[] {SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		booths.put("TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(1, 0, 0) );
//    		client.simulate();
    		
    		//Test 16
    		//Only person is watching the movie because they had the seat they wanted
//    		Map<String, SeatType[]> booths = new HashMap<String, SeatType[]>();
//    		booths.put("TO1", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		booths.put("TO3", new SeatType[] {SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.RUMBLE,SeatType.STANDARD });
//    		booths.put("TO2", new SeatType[] { SeatType.STANDARD, SeatType.STANDARD,SeatType.STANDARD,
//    				SeatType.STANDARD,SeatType.STANDARD,SeatType.STANDARD });
//    		
//    		Cinema client = new Cinema(booths, new MovieTheater(0, 1, 0) );
//    		client.simulate();
    		}
    	
    	}
    	

