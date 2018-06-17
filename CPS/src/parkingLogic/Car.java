package parkingLogic;

public class Car {
	
	private int ownnerId;
	private int carNumber; 
	
	public Car(int ownnerId, int carNumber) {
		
		this.ownnerId = ownnerId;
		this.carNumber = carNumber;
	}

	public int getOwnnerId() {
		return ownnerId;
	}

	public void setOwnnerId(int ownnerId) {
		this.ownnerId = ownnerId;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

}
