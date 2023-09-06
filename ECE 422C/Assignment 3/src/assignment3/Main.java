/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Victor Depetris>
 * <vgd 255>
 * <17195>
 * <Gavin Nguyen>
 * <gpn235>
 * <17180>
 * Slip days used: <0>
 * Git URL: git@github.com:ECE422C-Shi/sp-23-assignment-3-sp23-pair-31.git
 * Spring 2023
 */

package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Queue;
import java.util.Map;
import java.util.Stack;

public class Main {

    // static variables and constants only here.
	private static ArrayList<String> FirstLast = new ArrayList<String>();
    	private static ArrayList<String> Ladder = new ArrayList<String>();
   	private static Set<String> dictionary;
    	private static Set<String> visited;
    	private static Map<String,String> Nodes;

    public static void main(String[] args) throws Exception {

        Scanner kb;     // input Scanner for commands
        PrintStream ps; // output file, for student testing and grading only
        // If arguments are specified, read/write from/to files instead of Std IO.
        if (args.length != 0) {
            kb = new Scanner(new File(args[0]));
            ps = new PrintStream(new File(args[1]));
            System.setOut(ps);              // redirect output to ps
        } else {
            kb = new Scanner(System.in);    // default input from Stdin
            ps = System.out;                // default output to Stdout
        }
        initialize();
	parse(kb);
        if(FirstLast.size()!=0) {
            ArrayList<String> BFSwordLadder = getWordLadderBFS(FirstLast.get(0), FirstLast.get(1));
            printLadder(BFSwordLadder);

            ArrayList<String> DFSwordLadder = getWordLadderDFS(FirstLast.get(0), FirstLast.get(1));
            printLadder(DFSwordLadder);
        }
        // TODO methods to read in words, output ladder
    }
     /**
     * initialize static variables like Sets, and Arraylists.
     */
    public static void initialize() {
        // initialize your static variables or constants here.
        // We will call this method before running our JUNIT tests.  So call it
         // only once at the start of main.
	FirstLast=new ArrayList<>();
        Ladder=new ArrayList<>();
        dictionary=makeDictionary();
        visited = new HashSet<>();
    }

    /**
     * @param keyboard Scanner connected to System.in
     * @return ArrayList of Strings containing start word and end word.
     * If command is /quit, return empty ArrayList.
     */
    public static ArrayList<String> parse(Scanner keyboard) {
        // TODO
	//If /quit return null
	String input = keyboard.next();
    	if(input.equals("/quit")){
            FirstLast.clear();
    	    return FirstLast;
        }
	//If not return /quit put First and Last word into arraylist
        FirstLast.add(input.toLowerCase());
        input=keyboard.next();
        FirstLast.add(input.toLowerCase());
        return FirstLast;
    }
     /**
     * @param start
     * @param end
     * @return arrayList with the Ladder.
     * if Ladder cannot be found, return arraylist of start and end word.
     */
    public static ArrayList<String> getWordLadderDFS(String start, String end) {

       // Returned list should be ordered start to end.  Include start and end.
        // If ladder is empty, return list with just start and end.
        // TODO some code
	start= start.toLowerCase();
        end = end.toLowerCase();
         Stack<String> wordStack = new Stack<String>();
        // Makes a map with <Word, Parent>
        Map<String,String> wordNodes=new HashMap<>();
        //Put start word in the stack
        wordStack.push(start);
        FirstLast.add(start);
        FirstLast.add(end);
        //If start and end word are the same, add start and end word to ladder and go print
        if(start==end) {
            Ladder.add(start);
            Ladder.add(end);
        }
        else {
            //Create an array list of the word to change the letter
            char []word = wordStack.peek().toCharArray();
            //Remove Word from stack
            wordStack.pop();
            //Add words to Visited arraylist
            visited.add(String.valueOf(word).toUpperCase());
            //Makes keeps a reference of the current word
	    String CurrentV=String.valueOf(word);
	    //Create node map where start is the child and null is the parent
            wordNodes.put(start, null);
	    // Looks at the position of the letter being changed
            for(int position =0;position<=word.length-1; position++) {
		// Changes the letter
                for(char alphabet ='a';alphabet<='z';alphabet++) {
                    word[position]=alphabet;
			// If word is in dictionary and has yet to be visited then add word to stack, visit arraylist, and wordNodes map;
                    if(dictionary.contains(String.valueOf(word).toUpperCase())&&!visited.contains(String.valueOf(word).toUpperCase())) {
                        wordStack.push(String.valueOf(word));
                        visited.add(String.valueOf(word).toUpperCase());
                        wordNodes.put(String.valueOf(word), CurrentV);
                    }
			//If word equals end word then place word and parent word into the ladder array list
                    if(String.valueOf(word).equals(end)) {
                        for(String currNode = String.valueOf(word); currNode!=null;) {
                            Ladder.add(0,currNode);
                            currNode=wordNodes.get(currNode);
                        }
                        return Ladder;
                    }
                    word=CurrentV.toCharArray();
                }
            }
            if (wordStack.isEmpty()) {
		if(visited.size()==1) {
            		Ladder.add(start);
                    Ladder.add(end);
			}
                return Ladder;
            }
		//Recursive call of the function trying to find the word
            while(!wordStack.isEmpty()&&!Ladder.contains(end)) {
                getWordLadderDFS(wordStack.peek(),end);
                if(!Ladder.contains(end)) {
                    if(!wordStack.isEmpty()&&!Ladder.contains(end)) {
                        wordStack.pop();
                        if (wordStack.isEmpty()) {
                        	if(String.valueOf(word).equals(FirstLast.get(0))) {
                        		Ladder.add(start);
                                Ladder.add(end);
                        	}
                            return Ladder;
                        }
                        if (!wordStack.isEmpty()) {
                            getWordLadderDFS(wordStack.peek(),end);
                        }
                    }
                    else if (wordStack.isEmpty()) {
                        return Ladder;
                    }
                }
            }
            // After word is found then put current word and its parent word into Ladder
            for(String currNode = Ladder.get(0); currNode!=null;) {
                currNode=wordNodes.get(currNode);
                Ladder.add(0,currNode);
                currNode=wordNodes.get(currNode);
            }
            return Ladder;


        }
        return Ladder;
        
    }
     /**
     * @param start
     * @param end
     * @return arrayList with the Ladder.
     * if Ladder cannot be found, return arraylist of start and end word.
     */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {

	// TODO some code
	start= start.toLowerCase();
        end = end.toLowerCase();
    	Queue<String> wordQueue= new LinkedList<String>();
    	// Makes a map with <Word, Parent>
    	Map<String,String> wordNodes=new HashMap<>();
    	//Put start word in the Queue
    	wordQueue.add(start);
    	//If start and end word are the same, add start and end word to ladder and go print
    	if(start==end) {
    		Ladder.add(start);
    		Ladder.add(end);
    	}
    	else {
    	while(!wordQueue.isEmpty()) {
    		//Create an array list of the word to change the letter
    		char []word = wordQueue.peek().toCharArray();
    		//Remove Word from Queue
    		wordQueue.remove();
    		//Add words to Visited arraylist
    		visited.add(String.valueOf(word).toUpperCase());
		//Puts the word that was removed from the Queue variable to keep in mind what the current word it
    		String CurrentV=String.valueOf(word);
    		//Places start word into the node map where start is the child and null is the parent
    		wordNodes.put(start, null);
    		//Characters go from a to z
    		for(char alphabet ='a';alphabet<='z';alphabet++) {
    			//Changes postion where the letter is being changed
    			for(int position =0;position<=word.length-1; position++) {
    			word[position]=alphabet;
    			//If the word is in the dictionary and has yet to be visited
    			//put the word in the Queue, vistied arraylist, and wordNodes map
    			if(dictionary.contains(String.valueOf(word).toUpperCase())&&!visited.contains(String.valueOf(word).toUpperCase())) {
    				wordQueue.add(String.valueOf(word));
    				visited.add(String.valueOf(word).toUpperCase());
    				wordNodes.put(String.valueOf(word), CurrentV);
    			}
    			//If the end word is found then place it into the ladder, and find
    			//its parent using the map system where the key is the current
    			//word and the value is the parent, and continue until you
    			//hit the null
    			if(String.valueOf(word).equals(end)) {
    				for(String currNode = String.valueOf(word); currNode!=null;) {
    					Ladder.add(currNode);
    					currNode=wordNodes.get(currNode);
    				}
			//Reverse Ladder
    				Collections.reverse(Ladder);
    				return Ladder;
    			}
    			word=CurrentV.toCharArray();
    			}
    		}
        
    	    }
    	}
    	Ladder.add(start);
		Ladder.add(end);
    	return Ladder; 


    }
     /**
     * @param ladder
     * Prints Ladder found to get from start word to end word.
     * If there is no ladder, no word ladder found message is printed.
     */
    public static void printLadder(ArrayList<String> ladder) {
	int rung=ladder.size()-2;
    	if(rung==0) {
    		System.out.println("no word ladder can be found between "+ladder.get(0)+" and " + ladder.get(1));
    	}
    	else {
    	System.out.print("a "+ rung +"-rung word ladder exists between "+ ladder.get(0) +" and " +ladder.get(ladder.size()-1)+"."+"\n");
    		for(int i =0;i<ladder.size();i++) {
    			System.out.println(ladder.get(i));
    		}
    	}
    	Ladder.clear();
    	visited.clear();
    }

    // TODO
    // Other private static methods here

    /* Do not modify makeDictionary */
    public static Set<String> makeDictionary() {
        Set<String> words = new HashSet<String>();        
	Scanner infile = null;
        try {
            infile = new Scanner(new File("five_letter_words.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary File not Found!");
            e.printStackTrace();
            System.exit(1);
        }
        while (infile.hasNext()) {
            words.add(infile.next().toUpperCase());
        }
        return words;
    }
}