package entity;

import java.io.Serializable;

/**
 * Branch Park Parameters Details
 * @author OmerG
 *
 */
public class BranchParkParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int floor;
	private int raw;
	private int column[];
	
	/**
	 * C'tor
	 * @param floor
	 * @param raw
	 * @param column
	 */
	public BranchParkParameters(int floor, int raw, int column[]) {
		this.floor = floor;
		this.raw = raw;
		this.column = column;
	}

	/**
	 * 
	 * @return floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * 
	 * @param floor
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}

	/**
	 * 
	 * @return raw
	 */
	public int getRaw() {
		return raw;
	}

	/**
	 * 
	 * @param raw
	 */
	public void setRaw(int raw) {
		this.raw = raw;
	}

	/**
	 * 
	 * @return column
	 */
	public int[] getColumn() {
		return column;
	}

	/**
	 * 
	 * @param column
	 */
	public void setColumn(int[] column) {
		this.column = column;
	}

}
