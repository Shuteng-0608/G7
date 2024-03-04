//maintains a list of Passenger objects as an ArrayList

import java.util.ArrayList;

public class PassengerList {
	// Storage for an arbitrary number of details.
	private ArrayList<Passenger> passengerList;

	/**
	 * Perform any initialization for the text file.
	 */
	public PassengerList() {
		passengerList = new ArrayList<Passenger>();
	}

	/**
	 * Look up a name and return the corresponding passenger details.
	 * 
	 * @param name The name to be looked up.
	 * @return The details corresponding to the name, null if none
	 */
	public Passenger findByName(String name) {
		for (Passenger p : passengerList) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Add a new set of details to the list
	 * 
	 * @param passenger The details of the passenger
	 */
	public void addPassenger(Passenger passenger) {
		passengerList.add(passenger);
	}

	public Passenger getByIdx(int idx) {
		return passengerList.get(idx);
	}

//	/**
//	 * remove Passenger object identified by this name
//	 * 
//	 * @param name the name identifying the person to be removed
//	 */
//	public void removePassenger(String name) {
//		int index = findIndex(name);
//		if (index != -1) {
//			passengerList.remove(index);
//		}
//	}    

	/**
	 * Look up a name and return index
	 * 
	 * @param name The name to be looked up.
	 * @return The index, -1 if none
	 */
	public int findByLastName(String lastName, String reference_code) {
		int size = passengerList.size();
		for (int i = 0; i < size; i++) {
			Passenger p = passengerList.get(i);
			String name = p.getName();
			String last = name.split(" ")[1];
			if (last.equals(lastName) && p.getReference().equals(reference_code)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @return The number of entries currently in the flight.
	 */
	public int getNumberOfEntries() {
		return passengerList.size();
	}

	/**
	 * @return All the passenger details
	 */
	public String listDetails() {
		StringBuffer allEntries = new StringBuffer();
		for (Passenger details : passengerList) {
			allEntries.append(details);
			allEntries.append('\n');
		}
		return allEntries.toString();
	}
}
