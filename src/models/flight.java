package models;

import java.math.BigDecimal;

/**
 * The Flight class represents a flight with its details and is responsible
 * for maintaining the information about passengers, total capacity, and baggage information.
 */
public class flight {
    // Flight details
    private String flightCode;
    private String destination;
    private String date;
    private int capacity;
    private String carrier;
    private int checkedInPassengers;
    private passengerList passengerList;
    private double weight;
    private double volume;
    private double maxWeight;
    private double maxVolume;
    
    // Constructor
    
    public flight(){}
    
    public flight(String flightCode, String date, String destination, String carrier, int capacity,
                  double maxWeight, double maxVolume) {
        this.flightCode = flightCode;
        this.date = date;
        this.destination = destination;
        this.carrier = carrier;
        this.capacity = capacity;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.passengerList = new passengerList();
        this.checkedInPassengers = 0;
        this.weight = 0.0;
        this.volume = 0.0;
    }

    
    /**
     * Method to check in a passenger to this flight.
     * @param baggageWeight The weight of the passenger's baggage.
     * @param baggageVolume The volume of the passenger's baggage.
     * @return true if the passenger can be checked in; false otherwise.
     */
    public synchronized boolean checkInPassenger(double baggageWeight, double baggageVolume) {
        if (checkedInPassengers < capacity &&
                weight + baggageWeight <= maxWeight &&
                this.volume + baggageVolume <= maxVolume) {
            checkedInPassengers++;
            this.weight += baggageWeight;
            this.volume += baggageVolume;
            return true;
        }
        return false;
    }
    
	/**
	 * @return The number of passengers checked-in.
	 */
	public int numberOfCheckIn() {
		int cnt = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			passenger p = passengerList.getByIdx(i);
			if (p.getCheckin().equals("Yes"))
				cnt++;
		}
		return cnt;
	}

	/**
	 * @return The total weight of the baggage.
	 */
	public double totalWeight() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			passenger p = passengerList.getByIdx(i);
			if (p.getCheckin().equals("Yes"))
				sum += p.getBaggageWeight();
		}
		return sum;
	}

	/**
	 * @return The total volume of the baggage.
	 */
	public double totalVolume() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			passenger p = passengerList.getByIdx(i);
			if ((p.getCheckin().equals("Yes")))
				sum += p.getBaggageVolume();
		}
		return sum;
	}

	/**
	 * @return The total excess baggage fees.
	 */
	public double totalFees() {
		double sum = 0;
		for (int i = 0; i < passengerList.getNumberOfEntries(); i++) {
			passenger p = passengerList.getByIdx(i);
			if ((p.getCheckin().equals("Yes")))
				sum += p.excess_fee();
		}
		return sum;
	}

	/**
	 * Determine if the flight is overweight.
	 */
	public boolean check_weight() {
		if (totalWeight() > maxWeight)
			return false;
		return true;
	}

	/**
	 * Determine if the flight exceeds its maximum volume.
	 */
	public boolean check_volume() {
		if (totalVolume() > maxVolume)
			return false;
		return true;
	}

	/**
	 * Test for content equality between two flights.
	 * 
	 * @param other The object to compare to this one.
	 * @return true if the argument object has the same flight code.
	 */
	public boolean equals(Object other) {
		if (other instanceof flight) {
			flight otherFlight = (flight) other;
			return flightCode.equals(otherFlight.getFlightCode());
		} else {
			return false;
		}
	}

	/**
	 * Compare this Flight object against another, for the purpose of sorting. The
	 * fields are compared by flight code.
	 * 
	 * @param otherFlight The flight to be compared against.
	 * @return a negative integer if this flight_code comes before the parameter's
	 *         flight_code, zero if they are equal and a positive integer if this
	 *         comes after the other.
	 */

	public int compareTo(flight otherFlight) {
		return flightCode.compareTo(otherFlight.getFlightCode());
	}

	/**
	 * @return A string containing all details.
	 */
	public String toString() {
		return String.format("%-8s", flightCode) + String.format("%-8s", date) + String.format("%-5s", destination)
				+ String.format("%-5s", carrier) + String.format("%d", capacity) + String.format("%f", maxWeight)
				+ String.format("%f", maxVolume);
	}
    
    
	/**
     * Calculates the baggage volume percentage used of the flight.
     * @return The percentage of baggage volume used.
     */
    public double calculateBaggageVolumePercentage() {
        return (volume / maxVolume) * 100;
    }
    
    /**
     * Calculates the baggage weight percentage used of the flight.
     * @return The percentage of baggage weight used.
     */
    public double calculateBaggageWeightPercentage() {
    	////利用BigDecimal来实现四舍五入.保留一位小数,四舍五入
    	double result = new BigDecimal((weight / maxWeight) * 100).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
    
    public double calculatePassengerPercentage() {
    	////利用BigDecimal来实现四舍五入.保留一位小数,四舍五入
    	double checked = this.checkedInPassengers;
    	double result = new BigDecimal((checked / this.capacity) * 100).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
    
    public static void main(String[] args) {

    }
    // more to go in the future work
    // ...
    
    
    // Getters and setters for each field
    /**
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}

	/**
	 * @param flightCode the flightCode to set
	 */
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the checkedInPassengers
	 */
	public int getCheckedInPassengers() {
		return checkedInPassengers;
	}

	/**
	 * @param checkedInPassengers the checkedInPassengers to set
	 */
	public void setCheckedInPassengers(int checkedInPassengers) {
		this.checkedInPassengers = checkedInPassengers;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}

	/**
	 * @return the maxWeight
	 */
	public double getMaxWeight() {
		return maxWeight;
	}

	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @return the maxVolume
	 */
	public double getMaxVolume() {
		return maxVolume;
	}

	/**
	 * @param maxVolume the maxVolume to set
	 */
	public void setMaxVolume(double maxVolume) {
		this.maxVolume = maxVolume;
	}


}
