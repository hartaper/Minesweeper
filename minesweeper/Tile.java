package minesweeper;

public class Tile {
	public boolean isMine;
	public boolean isFlagged;
	public boolean isOpened;
	public int surroundingMines;
	
	
	public Tile() {
		isMine = false;
		isFlagged = false;
		isOpened = false;
		surroundingMines = 0;
	}
	
	
	public void setMine() {
		isMine = true;
	}
	
	
	public void setSurroundingMines() {
		surroundingMines++;
	}
	
	
	public void setFlag() {
		isFlagged = !isFlagged;
	}
	
	
	public void openTile() {
		isOpened = true;
	}
}
