import java.awt.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/*
* The GridBox class involves the grids/boxes which are the base of the whole game together with their interactive behaviors 
* NOTE: this class is used as it will help us for future functionalities, more implementation will be added to this class in the future
* @author  Mahek Bharat Parmar

*/

public class Cell extends JButton {


	protected int xIndex;
	protected int yIndex;
	protected int state; 		//0(white) or 1(black)


	public Cell(int xIndex, int yIndex, int state) {
		super();
		
		this.setBorder(new MatteBorder(1,1,1,1,Color.lightGray));
					
		this.xIndex = xIndex;					//position of the individual cell 
		this.yIndex = yIndex;
		this.state = state;
		setBackground();

	}

	/*
	 * Sets the background of the cell based on the cell state
	 */
	public void setBackground() {
		//if state=1, cell is alive, sets background to black
		if (state == 1) {
			this.setBackground(Color.black);
		}
		//state=0, cell is dead, sets background to white
		else {
			this.setBackground(Color.white);
		}
	}

	/*
	 * This method returns the x-position of the cell
	 * @return xIndex, the x-position of the cell in the grid
	 */
	public int getXIndex() {
		return this.xIndex;
	}
	
	/*
	 * This method returns the y-position of the cell
	 * @return yIndex, the y-position of the cell in the grid
	 */
	public int getYIndex() {
		return this.yIndex;
	}
	
	/*
	 * This method returns the state of a cell
	 * @return int, 0 or 1
	 */
	public int getState() {
		return this.state;
	}
	
	/*
	 * This function makes a cell alive, by changing its state to 1
	 */
	public void makeAlive() {
		this.state =1;
		setBackground();
	}
	
	/*
	 * This function makes a cell dead, by changing its state to 0
	 */
	public void makeDead() {
		this.state =0;
		setBackground();
	}

	/*
	 * This function updates the state of a cell
	 * @param int newState, the new state of the cell
	 */
	public void updateState(int newState) {
		this.state = newState;
	}
	
}