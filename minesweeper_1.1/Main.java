package minesweeper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	 public static void main (String[] args) throws IOException {
		 System.out.println("Welcome to minesweeper!");
		 System.out.println("Author: Harri Erme");
		 System.out.println("");
		 System.out.println("How to play:");
		 System.out.println("Open tile = 'o'");
		 System.out.println("Place or remove flag = 'f'");
		 System.out.println("Quit game = 'q'");
		 System.out.println("Help = 'h'");
		 System.out.println("");
		 System.out.println("Example command: o43");
		 System.out.println("");
		 System.out.println("The two numbers are coordinates");
		 System.out.println("where the first number indicates horizontal position");
		 System.out.println("and the second vertical position.");
		 System.out.println("");
		 System.out.println("The goal of the game is to flag all the mines by deducing");
		 System.out.println("their location from the amount of surrounding mines per tile.");
		 System.out.println("You win the game if you manage to flag all the mines");
		 System.out.println("and don't have any incorrect flags.");
		 System.out.println("You lose if you open a mine.");
		 System.out.println("");
		 
		 while(true) {
			 Board minesweeper = new Board();
			 String selection = "";
			 
			 while(!selection.equals("n") || !selection.equals("y")) {
				 BufferedReader reader = new BufferedReader(
				            new InputStreamReader(System.in));
				System.out.print("Want to play again? (y/n): ");
				selection = reader.readLine();
				if(selection.equals("n") || selection.equals("y")) {
					break;
				}
				else {
					System.out.println("Input proper command.");
				}
			}	
			
			if (selection.equals("n")) {
				break;
			}			
			
		 }
		 System.out.println("Thank you for playing!");		 
	 }	 
}
