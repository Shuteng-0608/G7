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
	 * Constructor to initialize flight details.
	 * @param flight_code Flight identifier code.
	 * @param destination Destination of the flight.
	 * @param carrier Airline carrier.
	 * @param capacity Maximum capacity of passengers.
	 * @param weight Maximum weight limit.
	 * @param volume Maximum volume limit.
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

	// Counts the number of passengers who have checked in
	public int numberOfCheckIn() {
		int cnt = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			Passenger p = passengerList.getByIdx(i);
			if (p.getCheckin().equals("Yes")) {
				cnt++;
			}
		}
		return cnt;
	}

	// Calculates the total weight of checked-in baggage.
	public double totalWeight() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			Passenger p = passengerList.getByIdx(i);
			if (p.getCheckin().equals("Yes")) {
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
			if (p.getCheckin().equals("Yes")) {
				sum += p.getVolume();
			}
		}
		return sum;
	}

	//Calculates the total excess fees from all passengers.
	public double totalFees() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			Passenger p = passengerList.getByIdx(i);
			if (p.getCheckin().equals("Yes")) {
				sum += p.excess_fee();
			}
		}
		return sum;
	}

	public boolean check_weight() {
		if(totalWeight() > weight) return false;
		return true;
	}
	
	public boolean check_volume() {
		if(totalVolume() > volume) return false;
		return true;
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
	 * Provides a string representation of all flight details.
	 * This includes formatted output of flight code, destination, carrier, capacity, weight, and volume.
	 * @return A string containing all details.
	 */
	@Override
	public String toString() {
		return String.format("%-8s", flight_code) + String.format("%-20s", destination)
				+ String.format("%-20s", carrier) + String.format("%d", capacity) + String.format("%f", weight)
				+ String.format("%f", volume);
	}
}
