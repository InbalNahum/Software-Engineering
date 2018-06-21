package entity;

import java.io.Serializable;

public class BranchParkParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int floor;
	private int raw;
	private int column[];
	
	public BranchParkParameters(int floor, int raw, int column[]) {
		this.floor = floor;
		this.raw = raw;
		this.column = column;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getRaw() {
		return raw;
	}

	public void setRaw(int raw) {
		this.raw = raw;
	}

	public int[] getColumn() {
		return column;
	}

	public void setColumn(int[] column) {
		this.column = column;
	}

}
