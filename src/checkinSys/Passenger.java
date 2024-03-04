// a simple class to contain and manage Passengers details

public class Passenger implements Comparable<Passenger> {
	private String reference_code;
	private String name;
	private String flight_code;
	private String check_in;
	private double weight;
	private double volume;

	/**
	 * Set up the contact details.
	 */
	public Passenger(String reference_code, String name, String flight_code, String check_in) {
		this.reference_code = reference_code.trim();
		this.name = name.trim();
		this.flight_code = flight_code.trim();
		this.check_in = check_in;
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
	 * @return The check_in status.
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

	public void check_in() {
		check_in = "Yes";
	}

	public void set(double weight, double volume) {
		this.weight = weight;
		this.volume = volume;
	}

	public double excess_fee() {
		return weight + volume;
	}

	/**
	 * Test for content equality between two objects.
	 * 
	 * @param other The object to compare to this one.
	 * @return true if the argument object has same reference code
	 */
	public boolean equals(Object other) {
		if (other instanceof Passenger) {
			Passenger otherStaff = (Passenger) other;
			return reference_code.equals(otherStaff.getReference());
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
		return String.format("%-8s", reference_code) + String.format("%-20s", name) + String.format("%-8s", flight_code)
				+ String.format("%-3s", check_in);
	}
}