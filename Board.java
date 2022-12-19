import java.io.*;
import java.util.*;

public class Board{
	
	/*The Sudoku Board is made of 9x9 cells for a total of 81 cells.
	 * In this program we will be representing the Board using a 2D Array of cells.
	 * 
	 */

	private Cell[][] board = new Cell[9][9];
	private Stack<Cell[][]> stack = new Stack<Cell[][]>();
	
	//The variable "level" records the level of the puzzle being solved.
	private String level = "";

	
	///TODO: CONSTRUCTOR
	//This must initialize every cell on the board with a generic cell.  It must also assign all of the boxIDs to the cells
	public Board()
	{
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				board[x][y] = new Cell();
				board[x][y].setBoxID( 3*(x/3) + (y)/3+1);
			}
	}
	

	
	///TODO: loadPuzzle
	/*This method will take a single String as a parameter.  The String must be either "easy", "medium" or "hard"
	 * If it is none of these, the method will set the String to "easy".  The method will set each of the 9x9 grid
	 * of cells by accessing either "easyPuzzle.txt", "mediumPuzzle.txt" or "hardPuzzle.txt" and setting the Cell.number to 
	 * the number given in the file.  
	 * 
	 * This must also set the "level" variable
	 * TIP: Remember that setting a cell's number affects the other cells on the board.
	 */
	public void loadPuzzle(String level) throws Exception
	{
		this.level = level;
		String fileName = "easyPuzzle.txt";
		if(level.contentEquals("medium"))
			fileName = "mediumPuzzle.txt";
		else if(level.contentEquals("hard"))
			fileName = "hardPuzzle.txt";
		
		Scanner input = new Scanner (new File(fileName));
		
		for(int x = 0; x < 9; x++)
			for(int y = 0 ; y < 9; y++)
			{
				int number = input.nextInt();
				if(number != 0)
					solve(x, y, number);
			}
						
		input.close();
		
	}
	
	///TODO: isSolved
	/*This method scans the board and returns TRUE if every cell has been solved.  Otherwise it returns FALSE
	 * 
	 */
	public boolean isSolved() {
		for(int i = 0; i < 9; i++) {
			for(int k = 0; k < 9; k++) {
				if(board[k][i].getNumber()==0)
					return false;
			}
		}
		return true;
	}


	///TODO: DISPLAY
	/*This method displays the board neatly to the screen.  It must have dividing lines to show where the box boundaries are
	 * as well as lines indicating the outer border of the puzzle
	 */
	public void display() {
		for(int i = 0; i < 13; i++) {
			for(int k = 0; k < 13; k++) {
				if(i%4==0)
					System.out.print("--");
				else {
					if(k%4==0)
						System.out.print("| ");
					else
						System.out.print(board[i-(1+i/4)][k-(1+k/4)].getNumber()+" ");
				}
			}
			System.out.println();
		}
	}
	
	///TODO: solve
	/*This method solves a single cell at x,y for number.  It also must adjust the potentials of the remaining cells in the same row,
	 * column, and box.
	 */
	public void solve(int x, int y, int number) {
		int id = board[x][y].getBoxID();
		for(int i = 0; i < 9; i++) {
			for(int k = 0; k < 9; k++) {
				if(i == y || k == x || board[k][i].getBoxID() == id) {
					if(board[k][i].getNumber() == 0) {
						board[k][i].cantBe(number);
					}
				}
			}
		}
		board[x][y].setNumber(number);
	}
	
	//logicCycles() continuously cycles through the different logic algorithms until no more changes are being made.
	public void logicCycles()throws Exception
	{
		
		while(isSolved() == false)
		{
			int changesMade = 0;
			do
			{
				changesMade = 0;
				changesMade += logic1();
				changesMade += logic2();
				changesMade += logic3();
				changesMade += logic4();
				if(errorFound())
					break;
			}while(changesMade != 0);
				if(errorFound())
					break;
	
		}			
		
	}
	
	
	///TODO: logic1
	/*This method searches each row of the puzzle and looks for cells that only have one potential.  If it finds a cell like this, it solves the cell 
	 * for that number. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic1()
	{
		int changesMade = 0;
		for(int i = 0; i < 9; i++) {
			for(int k = 0; k < 9; k++) {
				if(board[k][i].numberOfPotentials()==1 && board[k][i].getNumber()==0) {
					changesMade++;
					System.out.println("1");
					solve(k, i, board[k][i].getFirstPotential());
				}
			}
		}

		
		return changesMade;
					
	}
	
	///TODO: logic2
	/*This method searches each row for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell.  It then does the same thing for the columns.This also tracks the number of cells that 
	 * it solved as it traversed the board and returns that number.
	 */
	
	public int logic2()
	{
		int changesMade = 0;
		boolean potential = false;
		int x = -1;
		int y = -1;
		for(int i = 0; i < 9; i++) {
			for(int num = 1; num < 10; num++) {
				x = -1;
				y = -1;
				potential = false;
				for(int k = 0; k < 9; k++) {
					if(board[k][i].canBe(num) && board[k][i].getNumber()==0) {
						if(x!=-1) {
							potential = false;
							break;
						}
						else {
							x = k;
							y = i;
							potential = true;
						}
					}
				}
				if(potential) {
					solve(x, y, num);
					System.out.println("2");
					changesMade++;
				}
			}
		}
		for(int k = 0; k < 9; k++) {
			for(int num = 1; num < 10; num++) {
				x = -1;
				y = -1;
				potential = false;
				for(int i = 0; i < 9; i++) {
					if(board[k][i].canBe(num) && board[k][i].getNumber()==0) {
						if(x!=-1) {
							potential = false;
							break;
						}
						else {
							x = k;
							y = i;
							potential = true;
						}
					}
				}
				if(potential) {
					solve(x, y, num);
					System.out.println("2");
					changesMade++;
				}
			}
		}
			
		return changesMade;
	}
	
	///TODO: logic3
	/*This method searches each box for a cell that is the only cell that has the potential to be a given number.  If it finds such a cell and it
	 * is not already solved, it solves the cell. This also tracks the number of cells that it solved as it traversed the board and returns that number.
	 */
	public int logic3()
	{
	
		int changesMade = 0;
		boolean potential = false;
		int x = -1;
		int y = -1;
		
		for(int boxY = 0; boxY < 9; boxY+=3) {
			for(int boxX = 0; boxX < 9; boxX+=3) {
				for(int num = 1; num < 10; num ++) {
					x = -1;
					y = -1;
					potential = false;
					for(int i = boxY; i < boxY+3; i++) {
						for(int k = boxX; k < boxX+3; k++) {
							if(board[k][i].canBe(num) && board[k][i].getNumber()==0) {
								if(x!=-1) {
									potential = false;
									break;
								}
								else {
									x = k;
									y = i;
									potential = true;
								}
							}
						}
					}
					if(potential) {
						//System.out.println("pog");
						System.out.println("3");
						solve(x, y, num);
						changesMade++;
					}
				}
			}
		}
		return changesMade;
	}
	
	
	///TODO: logic4
		/*This method searches each row for the following conditions:
		 * 1. There are two unsolved cells that only have two potential numbers that they can be
		 * 2. These two cells have the same two potentials (They can't be anything else)
		 * 
		 * Once this occurs, all of the other cells in the row cannot have these two potentials.  Write an algorithm to set these two potentials to be false
		 * for all other cells in the row.
		 * 
		 * Repeat this process for columns and rows.
		 * 
		 * This also tracks the number of cells that it solved as it traversed the board and returns that number.
		 */
	public int logic4()
		//stolen from Mr. Chow
	{
		int changesMade = 0;

		for(int row = 0; row < 9; row++) {
			for(int column = 0; column < 9; column++) {
				if(board[row][column].numberOfPotentials() == 2) {
					int firstPotential = board[row][column].getFirstPotential();
					int secondPotential = board[row][column].getSecondPotential();
					for(int search = column+1; search < 9; search++) {
						if(board[row][search].numberOfPotentials() == 2 && board[row][search].getFirstPotential() == firstPotential && board[row][search].getSecondPotential() == secondPotential) {
							for(int x = 0; x < 9; x++) {
								if(x == search || x == column)
									continue;
								else {
									if(board[row][x].canBe(firstPotential)) {
										board[row][x].cantBe(firstPotential);
										changesMade++;
									}
									if(board[row][x].canBe(secondPotential)) {
										board[row][x].cantBe(secondPotential);
										changesMade++;
									}
								}
							}
						}
					}
				}
			}
		}
		for(int column = 0; column < 9; column++) {
			for(int row = 0; row < 9; row++) {
				if(board[row][column].numberOfPotentials() == 2) {

					int firstPotential = board[row][column].getFirstPotential();
					int secondPotential = board[row][column].getSecondPotential();

					for(int search = row+1; search < 9; search++) {
						if(board[search][column].numberOfPotentials() == 2 && board[search][column].getFirstPotential() == firstPotential && board[search][column].getSecondPotential() == secondPotential) {
							for(int x = 0; x < 9; x++) {
								if(x == search || x == row)
									continue;
								else {
									if(board[x][column].canBe(firstPotential)) {
										board[x][column].cantBe(firstPotential);
										changesMade++;
									}
									if(board[x][column].canBe(secondPotential)) {
										board[x][column].cantBe(secondPotential);
										changesMade++;
									}
								}
							}
						}
					}
				}
			}
		}
		return changesMade;
	}

	public void guess() {
		Cell[][] tmp = new Cell[9][9];
		for(int i=0; i < 9; i++) {
			for(int k=0; k < 9; k++) {
				tmp[i][k] = new Cell();
				tmp[i][k].setNumber(board[i][k].getNumber());
				tmp[i][k].setBoxID(board[i][k].getBoxID());
				for(int j=1; j < 10; j++) {
					if(!board[i][k].canBe(j)) {
						tmp[i][k].cantBe(j);
					}
				}
			}
		}
		stack.push(tmp);
		for(int i=0; i < 9; i++) {
			for(int k=0; k<9; k++) {
				if(tmp[i][k].getNumber()==0) {
					solve(i, k, tmp[i][k].getFirstPotential());
				}
			}
		}
	}
	
	
	///TODO: errorFound
	/*This method scans the board to see if any logical errors have been made.  It can detect this by looking for a cell that no longer has the potential to be 
	 * any number.
	 */
	public boolean errorFound() {	
		for(int i = 0; i < 9; i++) {
			for(int k = 0; k < 9; k++) {
				if(board[i][k].numberOfPotentials() == 0)
					return true;
			}
		}
		return false;
	}
}
