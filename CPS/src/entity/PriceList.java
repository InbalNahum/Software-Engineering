package entity;

import java.io.Serializable;

/**
 * Price List Details
 * @author OmerG
 *
 */
public class PriceList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String casualParking;
	private String preOrderParking;
	private String monthlySubscription;

	/**
	 * C'tor
	 * @param casualParking
	 * @param preOrderParking
	 * @param monthlySubscription
	 */
	public PriceList(String casualParking, String preOrderParking, String monthlySubscription) {
		this.casualParking = casualParking;
		this.preOrderParking = preOrderParking;
		this.monthlySubscription = monthlySubscription;
	}

	/**
	 * empty C'tor
	 */
	public PriceList() {}

	
	/**
	 * 
	 * @return casualParking
	 */
	public String getCasualParking() {
		return casualParking;
	}

	/**
	 * 
	 * @param casualParking
	 */
	public void setCasualParking(String casualParking) {
		this.casualParking = casualParking;
	}

	/**
	 * 
	 * @return preOrderParking
	 */
	public String getPreOrderParking() {
		return preOrderParking;
	}

	/**
	 * 
	 * @param preOrderParking
	 */
	public void setPreOrderParking(String preOrderParking) {
		this.preOrderParking = preOrderParking;
	}

	/**
	 * 
	 * @return monthlySubscription
	 */
	public String getMonthlySubscription() {
		return monthlySubscription;
	}

	/**
	 * 
	 * @param monthlySubscription
	 */
	public void setMonthlySubscription(String monthlySubscription) {
		this.monthlySubscription = monthlySubscription;
	}

	/**
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
