package models;

/**
 * The Passenger class represents an individual passenger with personal and baggage details.
 */
public class passenger {
    // Passenger details
    private String referenceCode;
    private String name;
	private String flight_code;
	private String date;
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
	public passenger(String reference_code, String name, String flight_code, String date, String check_in, double weight, double volume) {
		this.referenceCode = reference_code.trim();
		this.name = name.trim();
		this.flight_code = flight_code.trim();
		this.date = date.trim();
		this.isCheckedIn = check_in.trim();
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
    
	/**
	 * @return The date
	 */
	public String getDate() {
		return date;
	}
    
    public double getBaggageWeight() {
        return weight;
    }

    public double getBaggageVolume() {
        return volume;
    }

	/**
	 * @return The check-in status.
	 */
	public String getCheckin() {
		return isCheckedIn;
	}

	/**
	 * @return true if checked in, false otherwise.
	 */
	public boolean check_in() {
		if (isCheckedIn.equals("Yes")) {
			return true;
		} else {
			isCheckedIn = "Yes";
			return false;
		}
	}

	public void set(double weight, double volume) {
		this.weight = weight;
		this.volume = volume;
	}

	/**
	 * @return The excess baggage fee
	 */
	public double excess_fee() {
		double w = 0;
		double v = 0;
		if(weight > 20) w = 3 * (weight - 20);
		if(volume > 1.5) v = 10 * (volume - 1.5);
		double sum = Math.round((w + v) * 100) * 0.01d;
		return sum;
	}

	/**
	 * Test for content equality between two objects.
	 * 
	 * @param other The object to compare to this one.
	 * @return true if the argument object has same reference code
	 */
	public boolean equals(Object other) {
		if (other instanceof passenger) {
			passenger otherPassenger = (passenger) other;
			return referenceCode.equals(otherPassenger.getReference());
		} else {
			return false;
		}
	}

	/**
	 * Compare this Passenger object against another, for the purpose of sorting.
	 * The fields are compared by reference code.
	 * 
	 * @param otherDetails The details to be compared against.
	 * @return a negative integer if this reference_code comes before the
	 *         parameter's reference_code, zero if they are equal and a positive
	 *         integer if this comes after the other.
	 */

	public int compareTo(passenger otherDetails) {
		return referenceCode.compareTo(otherDetails.getReference());
	}

	/**
	 * @return A string containing all details.
	 */
	public String toString() {
		return String.format("%-20s", referenceCode) + String.format("%-20s", name)
				+ String.format("%-8s", flight_code) + String.format("%-8s", date) + String.format("%-3s", isCheckedIn)
				+ String.format("%f", weight) + String.format("%f", volume);
	}
}
