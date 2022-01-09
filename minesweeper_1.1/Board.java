package minesweeper;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Board {
	Tile[][] board = new Tile[Game.HEIGHT][Game.WIDTH];
	
	
	public Board() throws IOException {
		insertTilesInBoard(board);
		setMineTiles(board);
		randomizeMineLocations(board);
		findNearbyMines(board);
		runGame(board);		
	}
	

	private void insertTilesInBoard(final Tile[][] board) {
		for (int row = 0; row < Game.HEIGHT; row++) {
			for (int col = 0; col < Game.WIDTH; col++) {
				board[row][col] = new Tile();
			}
		}
	}
	

	private void setMineTiles(final Tile[][] board) {
		int mines = 0;
		for (int row = 0; row < Game.HEIGHT; row++) {
			for (int col = 0; col < Game.WIDTH; col++) {
				if (mines < Game.MINE_COUNT) {
					board[row][col].setMine();
					mines++;
				}
			}
		}
	}
	

	//Implementation of Fisher-Yates algorithm
	public void randomizeMineLocations(Tile[][] board) {
		Random random = new Random();
		
		for (int i = board.length - 1; i > 0; i--) {
			for (int j = board[i].length - 1; j > 0; j--) {
				int m = random.nextInt(i + 1);
				int n = random.nextInt(j + 1);
				
				Tile swap = board[i][j];
				board[i][j] = board[m][n];
				board[m][n] = swap;
			}
		}
	}
	
	
	//Confirming that findNearbyMines doesn't go out of board bounds
	private boolean validNeighbour(int row, int col) {
		if (row >= 0 && row < Game.WIDTH && col >= 0 && col < Game.HEIGHT) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	void findNearbyMines(final Tile[][] board) {
		for (int row = 0; row < Game.HEIGHT; row++) {
			for (int col = 0; col < Game.WIDTH; col++) {
				if (validNeighbour(row - 1, col - 1) && board[row - 1][col - 1].isMine) {
					board[row][col].setSurroundingMines();
				}
				if (validNeighbour(row - 1, col) && board[row - 1][col].isMine) {
					board[row][col].setSurroundingMines();
				}
				if (validNeighbour(row - 1, col + 1) && board[row - 1][col + 1].isMine) {
					board[row][col].setSurroundingMines();
				}
				if (validNeighbour(row, col - 1) && board[row][col - 1].isMine) {
					board[row][col].setSurroundingMines();
				}
				if (validNeighbour(row, col + 1) && board[row][col + 1].isMine) {
					board[row][col].setSurroundingMines();
				}
				if (validNeighbour(row + 1, col - 1) && board[row + 1][col - 1].isMine) {
					board[row][col].setSurroundingMines();
				}
				if (validNeighbour(row + 1, col) && board[row + 1][col].isMine) {
					board[row][col].setSurroundingMines();
				}
				if (validNeighbour(row + 1, col + 1) && board[row + 1][col + 1].isMine) {
					board[row][col].setSurroundingMines();
				}
			}
		}	
	}
	
	
	static void printBoard(final Tile[][] board) {
		System.out.print("   ");
		
		for (int col = 0; col < Game.WIDTH; col++) {
			System.out.print("[" + (col) + "]");
		}
		
		System.out.println("");
		
		for (int row = 0; row < Game.HEIGHT; row++) {
			System.out.print("[" + (row) + "]");
			for (int col = 0; col < Game.WIDTH; col++) {
				if(board[row][col].isOpened && board[row][col].isMine == false) {
					System.out.print(" " + board[row][col].surroundingMines + " ");				
				}
				
				else if(board[row][col].isOpened && board[row][col].isMine) {
					System.out.print(" * ");
				}
				
				else if(board[row][col].isFlagged) {
					System.out.print(" F ");
				}
								
				else {
					System.out.print(" o ");
				}
			}
			System.out.println("");
		}
	}
	
	
	private static String userInput() throws IOException {
			BufferedReader reader = new BufferedReader(
		            new InputStreamReader(System.in));
			System.out.print("Select coordinate and action: ");
			String command = reader.readLine();			
			System.out.println(" ");
			return command;
	}
	
		
	private static void printHelpMessage() {
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
	}
	
	
	private static void runGame(final Tile[][] board) throws IOException {
		int flags = Game.MINE_COUNT;
		int right_flags = 0;
		int wrong_flags = 0;
		while(true) {			
			try {
				printBoard(board);
				System.out.println("Mines remaining: " + flags);
				
				//Take command from user as input
				String command = userInput();
				
				
				//User inputs command like "f23"
				//First letter is the selected action and the two numbers 
				//represent the coordinates of the tile
				String action = command.substring(0,1);
				
				//Quit game
				if (action.equals("q")) {
					System.out.println("Game quitted");
					break;
				}
				
				//Print help message
				if (action.equals("h")) {
					printHelpMessage();
					continue;
				}
				
				//Parse coordinates
				int y_coor = Integer.parseInt(command.substring(1,2));
				int x_coor = Integer.parseInt(command.substring(2,3));
								
				//Place flag
				if(action.equals("f")) {
					if(board[x_coor][y_coor].isFlagged && board[x_coor][y_coor].isMine) {
						flags++;
						right_flags--;
					}
					else if(board[x_coor][y_coor].isFlagged) {
						flags++;
						wrong_flags--;
					}
					else if(board[x_coor][y_coor].isMine) {
						flags--;
						right_flags++;
					}
					else {
						flags--;
						wrong_flags++;
					}
					
					board[x_coor][y_coor].setFlag();					
					
					// Game is won if all the conditions are true
					if(flags == 0 && right_flags == Game.MINE_COUNT && wrong_flags == 0) {
						printBoard(board);
						System.out.println("You won!");
						break;
					}

				}
				
				//Open tile
				else if(action.equals("o")) {
					board[x_coor][y_coor].openTile();
					if (board[x_coor][y_coor].isMine) {
						printBoard(board);
						System.out.println("You lost!");
						break;
					}
				}
								
				//User inputs unassigned character
				else {
					System.out.println("Input proper command.");
				}
			
			} catch(Exception e) {
				System.out.println("Input proper command.");
			}
			
		}
		
	}
}
