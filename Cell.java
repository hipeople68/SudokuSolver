public class Cell {
	/*A Cell represents a single square on the Sudoku Game Board. 
	 * It knows it's number - 0 means it is not solved.
	 * It knows the potential numbers that it could have from 1-9.
	 * The Sudoku game board is sub-divided into 9 smaller 3x3 sections that I will call a box. 
	 * These boxes will be numbered from left to right, top to bottom, from 1 to 9.  Each cell
	 * will know which box it belongs in.
	 */
	
	private int number; // This is the solved value of the cell.
	private boolean[] potential = {false, true, true, true, true, true, true, true, true, true};
	/*This array represents the potential of the cell to be each of the given index numbers.  Index [0] is not used since
	 * the cell cannot be solved for 0. 
	 */
	private int boxID;//The boxID is the box to which the cell belongs.
	
	//USEFUL METHODS:
	
	///TODO: canBe 
	//This method returns TRUE or False depending on whether the cell has the potential to be number
	public boolean canBe(int number) {
		return potential[number];
	}
	
	///TODO: cantBe
	//This sets the potential array to be false for a given number
	public void cantBe(int number) {
		potential[number] = false;
	}
	
	//This method returns a count of the number of potential numbers that the cell could be.
	public int numberOfPotentials() {
		int count = 0;
		for(int i = 1; i < 10; i++) {
			if(potential[i])
				count++;
		}
		return count;
	}
	
	///TODO: getFirstPotential
	//pain
	//This method will return the first number that a cell can possibly be.
	public int getFirstPotential() {
		for(int i = 1; i < 10; i++) {
			if(potential[i])
				return i;
		}
		return -1;
	}
	public int getSecondPotential() {
		for(int i = getFirstPotential()+1; i < 10; i++) {
			if(potential[i])
				return i;
		}
		return 0;
	}
	
	
	
	//GETTERS AND SETTERS
	public int getNumber() {
		return number;
	}
	
	///TODO: setNumber
	// This method sets the number for the cell but also sets all of the potentials for the cell (except for the solved number)
	//		to be false
	public void setNumber(int number) {
		if(this.number!=0) {
			System.out.println("Overwrite occured");
		}
		this.number = number;
		for(int i = 0; i < 10; i++) {
			potential[i] = false;
		}
		potential[number] = true;
	}
	
	public boolean[] getPotential() {
		return potential;
	}
	public void setPotential(boolean[] potential) {
		this.potential = potential;
	}
	public int getBoxID() {
		return boxID;
	}
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
	public void clone(boolean[] tmp) {
		for(int i = 0; i < 10; i++) {
			potential[i] = tmp[i];
		}
	}

}
