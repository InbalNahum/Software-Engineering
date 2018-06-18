package parkingLogic;

import common.CpsGlobals.parkingState;

public class BranchParkState {
	parkingState[][] floorOne;
	parkingState[][] floorTwo;
	parkingState[][] floorThree;
	private int rows = 3; 
	private int columns[];
	
	public BranchParkState(ParkingFloor[] park, int columns[]) {
		this.columns = new int[3];
		for(int i=0; i<3; i++) {
			this.columns[i] = columns[i];
		}
		floorOne = park[0].getFloorState();
		floorTwo = park[1].getFloorState();
		floorThree = park[2].getFloorState();
	}

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
	
	public parkingState[][] getFloorOne() {
		return floorOne;
	}

	public void setFloorOne(parkingState[][] floorOne) {
		this.floorOne = floorOne;
	}

	public parkingState[][] getFloorTwo() {
		return floorTwo;
	}

	public void setFloorTwo(parkingState[][] floorTwo) {
		this.floorTwo = floorTwo;
	}

	public parkingState[][] getFloorThree() {
		return floorThree;
	}

	public void setFloorThree(parkingState[][] floorThree) {
		this.floorThree = floorThree;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int[] getColumns() {
		return columns;
	}

	public void setColumns(int[] columns) {
		this.columns = columns;
	}


}
