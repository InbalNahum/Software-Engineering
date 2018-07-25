package parkingLogic;

import java.io.Serializable;

import common.CpsGlobals.parkingState;

/**
 * Branch Park State Picture
 * @author OmerG
 *
 */
public class BranchParkState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	parkingState[][] floorOne;
	parkingState[][] floorTwo;
	parkingState[][] floorThree;
	private int rows = 3; 
	private int columns[];
	
	/**
	 * C'tor
	 * @param park
	 * @param columns
	 */
	public BranchParkState(ParkingFloor[] park, int columns[]) {
		this.columns = new int[3];
		for(int i=0; i<3; i++) {
			this.columns[i] = columns[i];
		}
		floorOne = park[0].getFloorState();
		floorTwo = park[1].getFloorState();
		floorThree = park[2].getFloorState();
	}

	/**
	 * print State
	 */
	public void printState() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<columns[0]; j++) {
				System.out.print(floorOne[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		for(int i=0; i<3; i++) {
			for(int j=0; j<columns[1]; j++) {
				System.out.print(floorTwo[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		for(int i=0; i<3; i++) {
			for(int j=0; j<columns[2]; j++) {
				System.out.print(floorThree[i][j]+" ");
			}
			System.out.println();
		}	
	}
	
	/**
	 * 
	 * @return floorOne
	 */
	public parkingState[][] getFloorOne() {
		return floorOne;
	}

	/**
	 * 
	 * @param floorOne
	 */
	public void setFloorOne(parkingState[][] floorOne) {
		this.floorOne = floorOne;
	}

	/**
	 * 
	 * @return floorTwo
	 */
	public parkingState[][] getFloorTwo() {
		return floorTwo;
	}

	/**
	 * 
	 * @param floorTwo
	 */
	public void setFloorTwo(parkingState[][] floorTwo) {
		this.floorTwo = floorTwo;
	}

	/**
	 * 
	 * @return floorThree
	 */
	public parkingState[][] getFloorThree() {
		return floorThree;
	}

	/**
	 * 
	 * @param floorThree
	 */
	public void setFloorThree(parkingState[][] floorThree) {
		this.floorThree = floorThree;
	}

	/**
	 * 
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * 
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 
	 * @return columns
	 */
	public int[] getColumns() {
		return columns;
	}

	/**
	 * 
	 * @param columns
	 */
	public void setColumns(int[] columns) {
		this.columns = columns;
	}


}
