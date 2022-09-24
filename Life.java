import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;


public class Life{
	
	protected static JFrame frame;
	protected static JPanel grid;
	
	protected static Cell cells[][];
	protected static Cell nextGenCells[][];
	protected static JLabel label;
	
	static Random randgen = new Random();
	
	protected static int row;
	protected static int col;
	
	protected static String type;
	protected static int iterations;
	
	public static void main(String[] args) throws InterruptedException {
		
		//gets the command line arguments
        iterations = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        type = args[2];
        
        row = size;
        col = size;
        
		frame = new JFrame("Game of Life");
		frame.setPreferredSize(new Dimension(1000,830));
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(null);
		
		//setting up the grid
		grid = new JPanel();
		grid.setBounds(0,25,980,762);
		grid.setLayout(new GridLayout(row, col));
		contentPane.add(grid);
		
		//label shows the  current config
		label = new JLabel();
		label.setBounds(250,5,250,15);
		label.setFont(new Font("Georgia", Font.PLAIN, 18));
		contentPane.add(label);
		
		//2 arrays to keep track of the states
		cells = new Cell[row][col];
		nextGenCells = new Cell[row][col];
		
		//housekeeping code
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		
		play();
		
	}
	
	/*
	 * This method sets up the grid based on the config selected, and initiates the game
	 */
	protected static void play() throws InterruptedException{
		
		//sets up the grid with cells under a random configuration
		if (type.equals("R")) {
			for (int i=0 ; i<row; i++) {
				for (int j=0 ; j<col ; j++) {
				
					label.setText("Random Cofiguration");
					
					cells[i][j] = new Cell(i,j, getRandomState());
					nextGenCells[i][j] =new Cell(i,j, getRandomState());
					
					grid.add(cells[i][j]);
				}
			}
		}
		
		//sets up the grid with cells for the penta-oscillator configuration
		else if (type.equals("P")){
			label.setText("Penta-decathlon Oscillator");
			
			//positions that match the initial config provided on assignment notes
			int[][] pentaConfig = {{4,5},{4,10}, {5,3}, {5,4}, {5,6}, {5,7}, {5,8}, {5,9}, {5,11}, {5,12}, {6,5}, {6,10}};
			
			//initially setting every cell to a state of 0
			for (int i=0 ; i<row; i++) {
				for (int j=0 ; j<col ; j++) {
					cells[i][j] = new Cell(i,j,0);
					nextGenCells[i][j] =new Cell(i,j,0);
					grid.add(cells[i][j]);
				}
			}
				
			//now only updating the states of the few manually selected cells
			for (int i=0 ; i<pentaConfig.length ; i++) {
				cells[pentaConfig[i][0]][pentaConfig[i][1]].makeAlive();
				nextGenCells[pentaConfig[i][0]][pentaConfig[i][1]].makeAlive();
			}
		}
		
		//sets up the grid with cells for a simkin glider gun configuration
		else if (type.equals("S")){
			label.setText("Simkin Glider Gun");
			
			//positions that match the initial config provided on assignment notes
			int[][] simkinGunConfig = {{3,3},{3,4}, {4,3}, {4,4}, {3,10}, {3,11}, {4,10}, {4,11}, {6,7}, {6,8}, {7,7}, {7,8}, {14,27}, {14,28}, {15,27}, {15,28}, {11,30}, {11,31}, {12,30}, {12,31}, {14,34}, {14,35}, {15,34}, {15,35}, {4,21}, {4,22}, {4,23}, {3,23}, {5,21}, {5,23}, {6,21}};
			
			for (int i=0 ; i<row; i++) {
				for (int j=0 ; j<col ; j++) {
					cells[i][j] = new Cell(i,j,0);
					nextGenCells[i][j] =new Cell(i,j,0);
					grid.add(cells[i][j]);
				}
			}
			
			//now only updating the states of the few manually selected cells
			for (int i=0 ; i<simkinGunConfig.length ; i++) {	
				cells[simkinGunConfig[i][0]][simkinGunConfig[i][1]].makeAlive();
				nextGenCells[simkinGunConfig[i][0]][simkinGunConfig[i][1]].makeAlive();
			}	
		}
		
		frame.pack();
		
		for (int t=0 ; t<iterations; t++) {
			Thread.sleep(250);
			update();
			frame.pack();
		}
		
	}
	

	/*
	 * This method updates the cells based on the rules of Game of Life
	 */
	protected static void update() throws InterruptedException  {
	
		//on the start of every new iteration, make the nextGenCells equal to cells array
		for (int i=0 ; i<row ; i++) {
			for (int j=0 ; j<col ; j++) {
				nextGenCells[i][j].updateState(cells[i][j].getState());
			}
		}
		
		for (int i=0 ; i<row ; i++) {
			for (int j=0 ; j<col ; j++) {
				
				Cell current = cells[i][j];
				int currentSum = 0;
				
				//neighbours of the current cell
				ArrayList<Integer> neighbours= new ArrayList<Integer>(Arrays.asList(
						getNeighBourState(i-1, j-1),
						getNeighBourState(i, j-1),
						getNeighBourState(i+1, j-1),
						getNeighBourState(i-1, j),
						getNeighBourState(i+1, j),
						getNeighBourState(i-1, j+1),
						getNeighBourState(i, j+1),
						getNeighBourState(i+1, j+1)
						));
				
				//sum of all neighbour states
				for (int a :neighbours ) {
					currentSum += a;
				}
				
				//implements rules for a currently live cell
				if (current.getState() == 1) {
					if (currentSum < 2) {
						current.makeDead();
					}
					else if ((currentSum >= 2) && (currentSum <=3)){
						current.makeAlive();
					}
					else if (currentSum > 3){
						current.makeDead();
					}
				}
				//implements rules for a currently dead cell
				else if (current.getState() == 0) {
					if (currentSum == 3){
						current.makeAlive();
					}
				}
			}
		}
	}
	
	
	/*
	 * A simple function that generates random numbers
	 * @return int, either 0 or 1, randomly
	 */
	protected static int getRandomState() {
		return randgen.nextInt(2);
	}
	

	/*
	 * This function gets the state a neighbouring cell considering the wrapped grid
	 * @param int i, the x-index of that neighbouring cell
	 * @param int y, the y-index of that neighbouring cell
	 * @return int, the state of that neighbouring cell, adjusted for the wrapped grid
	 */
	protected static int getNeighBourState(int i, int j) {
		//considers the rules for wrapping the grid 
		if (i >=row) {
			i=0;
		}
		if (j >=col) {
			j=0;
		}
		if (i < 0) {
			i = row-1;
		}
		if (j < 0) {
			j = col-1;
		}
		return (nextGenCells[i][j].getState());
	}

	
	
}