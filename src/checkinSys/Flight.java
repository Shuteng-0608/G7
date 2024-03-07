package checkinSys;
// a simple class to contain and manage Flight details
public class Flight implements Comparable<Flight> {
	private String destination;
	private String carrier;
	private String flight_code;
	private int capacity;
	private double weight;
	private double volume;
	private PassengerList passengerList;

	/**
	 * Set up the contact details.
	 */
	public Flight(String flight_code, String destination, String carrier, int capacity, double weight, double volume) {
		this.destination = destination;
		this.carrier = carrier;
		this.flight_code = flight_code;
		this.capacity = capacity;
		this.weight = weight;
		this.volume = volume;
		this.passengerList = new PassengerList();
	}
	
	public Flight() {}

	/**
	 * @return The destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @return The carrier.
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @return The flight code.
	 */
	public String getFlight() {
		return flight_code;
	}

	/**
	 * @return The capacity.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return The maximum weight.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return The maximum volume.
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * @return The passenger list.
	 */
	public PassengerList getList() {
		return passengerList;
	}

	/**
	 * Test for content equality between two objects.
	 *
	 * @param other The object to compare to this one.
	 * @return true if the argument object has same flight code
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Flight) {
			Flight otherStaff = (Flight) other;
			return flight_code.equals(otherStaff.getFlight());
		} else {
			return false;
		}
	}

	// The number of passengers checked-in
	public int numberOfCheckIn() {
		int cnt = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			Passenger p = passengerList.getByIdx(i);
			if (p.getCheckin() == "Yes") {
				cnt++;
			}
		}
		return cnt;
	}

	// The total weight of the baggage
	public double totalWeight() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			Passenger p = passengerList.getByIdx(i);
			if (p.getCheckin() == "Yes") {
				sum += p.getWeight();
			}
		}
		return sum;
	}

	// The total volume of the baggage
	public double totalVolume() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			Passenger p = passengerList.getByIdx(i);
			if (p.getCheckin() == "Yes") {
				sum += p.getVolume();
			}
		}
		return sum;
	}

	public double totalFees() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			Passenger p = passengerList.getByIdx(i);
			if (p.getCheckin() == "Yes") {
				sum += p.excess_fee();
			}
		}
		return sum;
	}

	/**
	 * Compare this Flight object against another, for the purpose of sorting. The
	 * fields are compared by flight code.
	 *
	 * @param otherDetails The details to be compared against.
	 * @return a negative integer if this flight_code comes before the parameter's
	 *         flight_code, zero if they are equal and a positive integer if this
	 *         comes after the other.
	 */

	@Override
	public int compareTo(Flight otherDetails) {
		return flight_code.compareTo(otherDetails.getFlight());
	}

	/**
	 * @return A string containing all details.
	 */
	@Override
	public String toString() {
		return String.format("%-8s", flight_code) + String.format("%-20s", destination)
				+ String.format("%-20s", carrier) + String.format("%d", capacity) + String.format("%f", weight)
				+ String.format("%f", volume);
	}
}
