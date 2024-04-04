package checkInSimulation;
// Represents a passenger with details relevant to a flight.
public class Passenger implements Comparable<Passenger> {

	private String name;// Passenger's name.
    private double weight;// Weight of the passenger's baggage.
    private String reference_code;// Unique reference code for the passenger.
	private String flight_code;// Code of the flight the passenger is booked on.
	private String date;// Date of the flight.
	private String check_in;// Check-in status of the passenger.
	private double volume;// Volume of the passenger's baggage.
	private String cabin;// Cabin class of the passenger.

	// Getter and setter
	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public Passenger(){}
	
	/**
	 * Set up the passengers details.
	 */
	public Passenger(String reference_code, String name, String flight_code, String date, String check_in,
			double weight, double volume, String cabin) {
		this.reference_code = reference_code.trim();
		this.name = name.trim();
		this.flight_code = flight_code.trim();
		this.date = date.trim();
		this.check_in = check_in.trim();
		this.weight = weight;
		this.volume = volume;
		this.cabin = cabin;
	}

	/**
	 * @return The reference code.
	 */
	public String getReference() {
		return reference_code;
	}

	/**
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The flight code.
	 */
	public String getFlight() {
		return flight_code;
	}

	/**
	 * @return The date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return The check-in status.
	 */
	public String getCheckin() {
		return check_in;
	}

	/**
	 * @return The baggage weight.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return The baggage volume.
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * @return true if checked in, false otherwise.
	 */
	public boolean check_in() {
		if (check_in.equals("Yes")) {
			return true;
		} else {
			check_in = "Yes";
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
		if (other instanceof Passenger) {
			Passenger otherPassenger = (Passenger) other;
			return reference_code.equals(otherPassenger.getReference());
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

	public int compareTo(Passenger otherDetails) {
		return reference_code.compareTo(otherDetails.getReference());
	}

	/**
	 * @return A string containing all details.
	 */
	public String toString() {
		return String.format("%-20s", reference_code) + String.format("%-20s", name)
				+ String.format("%-8s", flight_code) + String.format("%-8s", date) + String.format("%-3s", check_in)
				+ String.format("%f", weight) + String.format("%f", volume);
	}
	
}
