package models;

/**
 * The Passenger class represents an individual passenger with personal and baggage details.
 */
public class passenger {
    // Passenger details
    private String referenceCode;
    private String name;
	private String flight_code;
    private double weight;
    private double volume;
    private String isCheckedIn;

	/**
	 * Constructor to initialize a passenger with all details.
	 * @param reference_code The reference code of the passenger.
	 * @param name           The name of the passenger.
	 * @param flight_code    The code of the flight the passenger is booked on.
	 * @param isCheckedIn    The check-in status of the passenger.
	 * @param weight         The weight of the passenger's baggage.
	 * @param volume         The volume of the passenger's baggage.
	 */
	public passenger(String reference_code, String name, String flight_code, String check_in, double weight, double volume) {
		this.referenceCode = reference_code.trim();
		this.name = name.trim();
		this.flight_code = flight_code.trim();
		this.isCheckedIn = check_in;
		this.weight = weight;
		this.volume = volume;

	}

    // Baggage details setter
    public void set(double weight, double length, double width, double height) {
        this.weight = weight;
        this.volume = length * width * height;
    }

    // Check-in method
    public void checkIn() {
        this.isCheckedIn = "Yes";
    }

    // Getters for each field
    public String getReference() {
        return referenceCode;
    }

    public String getName() {
        return name;
    }

    public double getBaggageWeight() {
        return weight;
    }

    public double getBaggageVolume() {
        return volume;
    }

    public boolean isCheckedIn() {
    	if (this.isCheckedIn == "Yes"){
    			return true;
    	}else {
    		return false;
    	}
    }

    // Additional methods such as toString() for printing passenger details
    @Override
    public String toString() {
        return "Passenger{" +
                "reference code='" + referenceCode + '\'' +
                ", name='" + name + '\'' +
                ", baggageWeight=" + weight +
                ", baggageVolume=" + volume +
                ", isCheckedIn=" + isCheckedIn +
                '}';
    }
}
