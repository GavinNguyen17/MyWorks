package assignment2;
import java.util.ArrayList;
public class CharArray {
	//Word is the word Players are trying to guess
	 public static ArrayList <Character> Word=new ArrayList <Character>();
	//Guess is the word Players have entered
	 public static ArrayList <Character> Guess=new ArrayList<Character>();
	//Is an arraylist for the Feedback
	 public static ArrayList <String> FeedBackArray=new ArrayList<String>();
// This class creates and checks character Arrays
	
	//Creates a Character Array for the Actual Word
	 public void WordCharArray(String string){
		String str = string;
		  
        // Creating array of string length
        // using length() method
        char[] character = new char[str.length()];
  
        // Copying character by character into array
        // using for each loop
        for (int i = 0; i < str.length(); i++) {
            character[i] = str.charAt(i);
        }
        //Places characters into Word Arraylist
        for (int i = 0; i < str.length(); i++) {
        	Word.add(i, character[i]);
        }
	 }
		
	//Creates a Character Array for the Guess
        public void GuessCharArray(String string){
    		String str = string;
    		  
            // Creating array of string length
            // using length() method
            char[] character = new char[str.length()];
      
            // Copying character by character into array
            // using for each loop
            for (int i = 0; i < str.length(); i++) {
                character[i] = str.charAt(i);
            }
            //Places characters into Guess ArrayList
            for (int i = 0; i < str.length(); i++) {
            	Guess.add(i, character[i]);
            }
       }
        //Creates a blank arraylist length wordLength with only blanks ( "_" )
        public void CreateEmptyArray(int wordLength){
        	for (int i = 0; i < wordLength; i++) {
        		FeedBackArray.add(i, "_");
            }
       }
        //checks for all similar characters in guess and word that are in a similar location
        //if letters are in a similar location then print a G in new array and places a '_' in word at the index so the letter is later not double checked
        public void FindGreen(){
        	for(int i = 0; i<Word.size();i++) {
        		if(Word.get(i)==Guess.get(i)) {
        			FeedBackArray.set(i,"G");
        			Word.set(i, '_');
        		}
        	}
        }
        //checks all characters in guess and word and tries to find similar characters at different indexes
        //if characters are the same place a 'Y' in the new array at the index and place a '_' in word at the index
        public void FindYellow(){
        	for (int i =0; i<Guess.size();i++) {
        		for(int j=0;j<Word.size();j++) {
        			if((Word.get(j)==Guess.get(i))) {
        				if(FeedBackArray.get(i)!="G") {
        					FeedBackArray.set(i,"Y");
        					Word.set(j, '_');
        					j=Word.size();
        				}
        			}
        		}
        	}
        }
        
        //Prints out feedback
        public void Print(String history) {
        	for(int i=0;i<FeedBackArray.size();i++) {
        		System.out.print(FeedBackArray.get(i));
        	}
        	System.out.print("\n");
        //Places entered guess and feedback into history
        	History historyadd = new History();
        	historyadd.addtoHistory(history, FeedBackArray);
        //Clears the Feedback, Guess, and Word arrays
        	Guess.removeAll(Guess);
        	FeedBackArray.removeAll(FeedBackArray);
        	Word.removeAll(Word);
        }
}
