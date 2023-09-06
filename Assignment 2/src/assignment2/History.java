package assignment2;

import java.util.*;

public class History {
//This class checks and prints history
	public static ArrayList <String> History=new ArrayList <String>();
	public static ArrayList <String> FeedBack=new ArrayList <String>();
	//Adds Guess and FeedBack to history
	public void addtoHistory(String Guess, ArrayList<String> Color) {
		History.add(Guess+"->");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<Color.size(); i++) {
			sb.append(Color.get(i));
		}
		FeedBack.add(sb.toString());
	}
	//Prints out the previous guess and feed back. Ex. <Guess> -> FeedBack
	public void PrintHistory() {
		for(int i = 0;i<History.size();i++) {
	    		System.out.print(History.get(i));
	    		System.out.print(FeedBack.get(i));
	    		System.out.print("\n");
		}
			
				System.out.println("--------");
	}
	//Erases History
	public void RemoveHistory(){
		History.removeAll(History);
		FeedBack.removeAll(FeedBack);
	}
}
	

