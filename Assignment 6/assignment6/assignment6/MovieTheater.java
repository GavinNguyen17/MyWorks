package assignment6;

import assignment6.MovieTheater.SalesLogs;
import assignment6.Seat.SeatLetter;
import assignment6.Seat.SeatType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MovieTheater {

    private int printDelay;
    private SalesLogs log;
     Queue<Seat> rumSeat=new LinkedList<Seat>();
     Queue<Seat> comSeat=new LinkedList<Seat>();
     Queue<Seat> stanSeat=new LinkedList<Seat>();
     Queue<Seat> totalSeat=new LinkedList<Seat>();
    /**
     * Constructs a MovieTheater, where there are a set number of rows per seat type.
     *
     * @param rumbleNum the number of rows with rumble seats.
     * @param comfortNum the number of rows with comfort seats.
     * @param standardNum the number of rows with standard seats.
     */
    public MovieTheater(int rumbleNum, int comfortNum, int standardNum){
        printDelay = 100;
        log = new SalesLogs();
        Seat pushSeat;
        
       //Creates seats for Rumble Row
        //Also adds to total number of seats
        for(int i = 1; i<=rumbleNum; i++) {
      	   for(int j=0; j<Seat.SeatLetter.values().length;j++) {
      		   Seat.SeatLetter Letter = Seat.SeatLetter.values()[j];
      		   pushSeat = new Seat(Seat.SeatType.RUMBLE,i,Letter);
      		   rumSeat.add(pushSeat);
      		 totalSeat.add(pushSeat);
      	   }
         }
      //Creates seats for Comfort Row, Adds number of Rumble rows to 
      // number of Comfort Row count to ensure that the rows numbered correctly
        //Also adds to total number of seats
       for(int i = 1+rumbleNum; i<=comfortNum+rumbleNum; i++) {
    	   for(int j=0; j<Seat.SeatLetter.values().length;j++) {
    		   Seat.SeatLetter Letter = Seat.SeatLetter.values()[j];
    		   pushSeat = new Seat(Seat.SeatType.COMFORT,i,Letter);
    		   comSeat.add(pushSeat);
    		   totalSeat.add(pushSeat);
    	   }
       }
     //Creates seats for Standard Row, Adds number of Rumble and Comfort rows to 
     // number of Standard Row count to ensure that the rows numbered correctly
       //Also adds to total number of seats
       for(int i = 1+comfortNum+rumbleNum; i<=standardNum+comfortNum+rumbleNum; i++) {
    	   for(int j=0; j<Seat.SeatLetter.values().length;j++) {
    		   Seat.SeatLetter Letter = Seat.SeatLetter.values()[j];
    		   pushSeat = new Seat(Seat.SeatType.STANDARD,i,Letter);
    		   stanSeat.add(pushSeat);
    		   totalSeat.add(pushSeat);
    	   }
       	}
        // TODO: Finish implementing this constructor.
    }

    /**
     * Returns the next available seat not yet reserved for a given seat type.
     *
     * @param seatType the type of seat (RUMBLE, COMFORT, STANDARD).
     * @return the next available seat or null if the theater is full.
     */
    synchronized public Seat getNextAvailableSeat(SeatType seatType) {
        // TODO: Implement this method.
    	// Checks if Rumble seat type is removed from queue
    		
    	if (seatType==Seat.SeatType.RUMBLE) {
    		if(!rumSeat.isEmpty()) {
    			return rumSeat.poll();
    	}
    		seatType=Seat.SeatType.COMFORT;

    		}
    	
    	// Checks if Comfort seat type is removed from queue
    	if (seatType==Seat.SeatType.COMFORT) {
    		if(!comSeat.isEmpty()) {
    			return comSeat.poll();
    }
    		seatType=Seat.SeatType.STANDARD;
    		
    	}
    	// Checks if Standard seat type is removed from queue
    	if (seatType == Seat.SeatType.STANDARD) {
    		if(!stanSeat.isEmpty()) {
    			return stanSeat.poll();
    }
    	}
    	
    	//return null if there is no more Standard seat/no more seats
        return null;
    }

    /**
     * Prints a ticket to the console for the customer after they reserve a seat.
     *
     * @param boothId id of the ticket booth.
     * @param 

 a particular seat in the theater.
     * @return a movie ticket or null if a ticket booth failed to reserve the seat.
     */
    public Ticket printTicket(String boothId, Seat seat, int customer) {
        // TODO: Implement this method.
    	Ticket print = new Ticket(boothId, seat, customer);
    	//If seat has already been taken do not print
    	Object lock = new Object();
    	
    	
    	if(log.ticketLog.contains(print)) {
    		return null;
    		
    	}
    	else {
    		if(seat!=null) {
    	//add seat to log
    	//add ticket to log
    	
    	
    	//print delay
    	try {
			Thread.sleep(printDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//print ticket
    	String result = print.toString();
    	System.out.println(result);
    	synchronized(totalSeat){
        	log.addTicket(print);
        	}
    	synchronized(totalSeat){
        	log.addSeat(seat);
        	}
    	return print;
    		}
    	}
    	return null;
    }
    
    
    /**
     * Lists all seats sold for the movie in the order of reservation.
     *
     * @return list of seats sold.
     */
    public List<Seat> getSeatLog() {
        return log.getSeatLog();
    }

    /**
     * Lists all tickets sold for the movie in order of printing.
     *
     * @return list of tickets sold.
     */
    public List<Ticket> getTransactionLog() {
    	return log.getTicketLog();
    }


	static class SalesLogs {

        private ArrayList<Seat> seatLog;
        private ArrayList<Ticket> ticketLog;

        private SalesLogs() {
            seatLog = new ArrayList<Seat>();
            ticketLog = new ArrayList<Ticket>();
        }

        public List<Seat> getSeatLog() {
            return (List<Seat>)(seatLog.clone());
        }

        public List<Ticket> getTicketLog() {
            return (List<Ticket>)(ticketLog.clone());
        }

        public void addSeat(Seat s) {
            seatLog.add(s);
            
        }

        public void addTicket(Ticket t) {
            ticketLog.add(t);
           
        }
       
        
    }
}
