package assignment2;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
//TODO: Design a Game.java class to handle top-level gameplay
//You may add whatever constructor or methods you like
	
	public void runGame(GameConfiguration config, String Word, Scanner Scan) {
		//Checks for word length, to check what file to enter
		Dictionary Dictionary = null;
		if (config.wordLength == 4) {
			 Dictionary = new Dictionary("4_letter_words.txt");
		}
		else if(config.wordLength==5) {
			 Dictionary = new Dictionary("5_letter_words.txt");
		}
		else if(config.wordLength==6) {
			 Dictionary = new Dictionary("6_letter_words.txt");
		}
		//Start of the program
		for(int GuessesLeft=0;config.numGuesses>GuessesLeft;) {
			History history = new History();
			System.out.println("Enter your guess:");
			String EnterGuess =Scan.nextLine();
			//Prints history if Player enters [history]
			if(EnterGuess.equals("[history]")) {
				history.PrintHistory();
			}
			//Prints prompt when player has word with incorrect length
			else if(EnterGuess.length()!=config.wordLength) {
				System.out.println("This word has an incorrect length. Please try again.");
			}
			//Prints prompt if player enters an invalid word
			else if(Dictionary.containsWord(EnterGuess)==false) {
				System.out.println("This word is not in the dictionary. Please try again.");
			}
			//If player enters the correct word
			else if ((EnterGuess.length()==config.wordLength)||Dictionary.containsWord(EnterGuess)==true) {
				if(EnterGuess.equals(Word)) {
					for(int i = 0;i<config.wordLength;i++) {
						System.out.print("G");
						
						}
					System.out.print("\n");
					System.out.println("Congratulations! You have guessed the word correctly.");
					history.RemoveHistory();
					System.out.println("Do you want to play a new game? (y/n)");
					return;
					}
				//If player enters a valid word but it isn't the correct word	
				//Creates character arrays for Word, Guess, and FeedBack
				CharArray CharArray = new CharArray();
				CharArray.WordCharArray(Word);
				CharArray.GuessCharArray(EnterGuess);
				CharArray.CreateEmptyArray(config.wordLength);
				//Looks for Green and Yellow for Feedback
				CharArray.FindGreen();
				CharArray.FindYellow();
				//Print out Feedback
				CharArray.Print(EnterGuess);
				//Increase for Number of Guesses left
				GuessesLeft++;
				int Difference = config.numGuesses-GuessesLeft;
				if(Difference!=0) {
				System.out.println("You have "+ Difference + " guess(es) remaining.");
				}
			}
		}
		//Clears history and player has run out of guesses
		History history = new History();
		history.RemoveHistory();
		System.out.println("You have run out of guesses."+"\n"+"The correct word was " + '"' + Word + '"'+"."+"\n"+"Do you want to play a new game? (y/n)");
		return;
	}
}
