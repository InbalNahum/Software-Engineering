package entity;

import java.io.Serializable;

public class PriceList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String casualParking;
	private String preOrderParking;
	private String monthlySubscription;

	public PriceList(String casualParking, String preOrderParking, String monthlySubscription) {
		this.casualParking = casualParking;
		this.preOrderParking = preOrderParking;
		this.monthlySubscription = monthlySubscription;
	}

	public PriceList() {}

	public String getCasualParking() {
		return casualParking;
	}

	public void setCasualParking(String casualParking) {
		this.casualParking = casualParking;
	}

	public String getPreOrderParking() {
		return preOrderParking;
	}

	public void setPreOrderParking(String preOrderParking) {
		this.preOrderParking = preOrderParking;
	}

	public String getMonthlySubscription() {
		return monthlySubscription;
	}

	public void setMonthlySubscription(String monthlySubscription) {
		this.monthlySubscription = monthlySubscription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
