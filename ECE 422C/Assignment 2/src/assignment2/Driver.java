package assignment2;

import java.util.Scanner;

public class Driver {

	    public void start(GameConfiguration config) {
	        // TODO: complete this method
	        // We will call this method from our JUnit test cases.
	    	Scanner Scan = new Scanner(System.in);
	    	System.out.println("Hello! Welcome to Wordle.");
	    	System.out.println("Do you want to play a new game? (y/n)");
	    	while(1==1) {
	    		//scans for the letter y
	    		String RandomWord=null;
	    	String Play =Scan.nextLine();
	    	if(Play.equals("y")) {
	    		//If the word is a 4 letter word then grab a word from 4_letter_words.txt 
	    		Dictionary Word = null;
	    		if (config.wordLength == 4) {
	    			 Word = new Dictionary("4_letter_words.txt");
	    		}
	    		else if(config.wordLength==5) {
	    			 Word = new Dictionary("5_letter_words.txt");
	    		}
	    		else if(config.wordLength==6) {
	    			 Word = new Dictionary("6_letter_words.txt");
	    		}
	    		RandomWord=Word.getRandomWord();
	    		//Creates a Character Array
				if(config.testMode==true) {
	        		System.out.println(RandomWord);
				}
	    		//Run the game
	    		Game runGame = new Game();
	    		runGame.runGame(config, RandomWord, Scan);
	    	}
	    		//Scans for n, if n then end game
	    	else if(Play.equals("n")) {
	    		return;
	    	}
	    		//if anything but y or n ask prompt again
	    	else if(!Play.equals("n")||!Play.equals("y")) {
	    		System.out.println("Do you want to play a new game? (y/n)");
	    	}
	    }
	}
    

	    public void start_hardmode(GameConfiguration config) {
	        // TODO: complete this method for extra credit
	        // We will call this method from our JUnit test cases.
	    }
	
	    public static void main(String[] args) {
	        // Use this for your testing.  We will not be calling this method.
	    	GameConfiguration config = new GameConfiguration(5,6,true);
	    	Driver newDriver = new Driver();
	    	newDriver.start(config);
	    }
}
