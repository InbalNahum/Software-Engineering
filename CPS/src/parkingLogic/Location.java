package parkingLogic;

import java.io.Serializable;

/**
 * Parking Location
 * @author OmerG
 *
 */
public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x; 
	private int y;
	private int z;
	
	/**
	 * C'tor
	 * @param x
	 * @param y
	 * @param z
	 */
	public Location(int x, int y, int z) {

		this.x = x;
		this.y = y;
		this.z = z;
		
	}

	/**
	 * x
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * y
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * z
	 * @return
	 */
	public int getZ() {
		return z;
	}

	/**
	 * 
	 * @param z
	 */
	public void setZ(int z) {
		this.z = z;
	}
}
