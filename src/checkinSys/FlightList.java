package checkinSys;

/* Maintains a list of Flight objects as an ArrayList. */
import java.util.ArrayList;
import java.util.Random;

public class FlightList {
	// Storage for an arbitrary number of flights.
	private ArrayList<Flight> flightList;

	/**
	 * Perform any initialization for the flight list.
	 */
	public FlightList() {
		flightList = new ArrayList<Flight>();
	}

	/**
	 * Add a new set of details to the list
	 * 
	 * @param flight The object of the flight
	 */
	public void addFlight(Flight flight) {
		flightList.add(flight);
	}

	/**
	 * Look up an index and return the corresponding flight object.
	 * 
	 * @param idx The index to be looked up.
	 * @return The flight object corresponding to the index.
	 */
	public Flight getFlight(int idx) {
		return flightList.get(idx);
	}

	/**
	 * @return The number of entries currently in the flightList.
	 */
	public int getNumberOfEntries() {
		return flightList.size();
	}

	/**
	 * Look up a flight code and return the corresponding flight object.
	 * 
	 * @param flight_code The flight code to be looked up.
	 * @return The object corresponding to the flight code, null if none.
	 */
	public Flight findByCode(String flight_code) {
		for (Flight f : flightList) {
			if (f.getFlight().equals(flight_code)) {
				return f;
			}
		}
		return null;
	}

	public Passenger randomSelect() {
		Random rand = new Random();
		int idx1 = rand.nextInt(getNumberOfEntries());
		
		while(flightList.get(idx1).getList().getNumberOfEntries() == flightList.get(idx1).numberOfCheckIn()) 
			idx1 = rand.nextInt(getNumberOfEntries());
		
		Flight flight = getFlight(idx1);
		int idx2 = rand.nextInt(flight.getList().getNumberOfEntries());
		
		while(flight.getList().getByIdx(idx2).getCheckin() == "Yes")
			idx2 = rand.nextInt(flight.getList().getNumberOfEntries());
		
		return flight.getList().getByIdx(idx2);
	}
}