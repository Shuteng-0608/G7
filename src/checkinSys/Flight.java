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
	 * Constructor for creating a Flight object with detailed parameters.
	 * @param flight_code The unique code for the flight.
	 * @param destination The destination of the flight.
	 * @param carrier The carrier or airline operating the flight.
	 * @param capacity The maximum number of passengers that can be accommodated.
	 * @param weight The maximum weight capacity of the flight.
	 * @param volume The maximum volume capacity of the flight.
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

	// Default constructor
	public Flight() {}

	//getter
	public String getDestination() {
		return destination;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getFlight() {return flight_code;}
	public int getCapacity() {
		return capacity;
	}
	public double getWeight() {
		return weight;
	}
	public double getVolume() {
		return volume;
	}
	public PassengerList getList() {
		return passengerList;
	}

	/**
	 * Overrides the equals method to compare flights based on their flight code.
	 * @param other The object to be compared.
	 * @return true if the flight codes are equal.
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

	// Additional methods to compute aggregated data from passenger list
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
	 * Implements compareTo for sorting flights based on flight code.
	 * @param otherDetails The Flight object to be compared against.
	 * @return a negative integer, zero, or a positive integer as this object
	 *         is less than, equal to, or greater than the specified object.
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
