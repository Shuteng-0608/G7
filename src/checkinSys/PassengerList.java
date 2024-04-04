package checkinSys;

import java.util.ArrayList;


public class PassengerList {
	// Storage for an arbitrary number of passengers.
		private ArrayList<Passenger> passengerList;

		/**
		 * Perform any initialization for the list.
		 */
		public PassengerList() {
			passengerList = new ArrayList<Passenger>();
		}

		/**
		 * Add a new set of details to the list
		 * 
		 * @param passenger The details of the passenger
		 */
		public void addPassenger(Passenger passenger) {
			passengerList.add(passenger);
		}

		/**
		 * Remove Passenger object
		 * 
		 * @param passenger The passenger object
		 */
		public void removePassenger(Passenger passenger) {
			passengerList.remove(passenger);
		}

		/**
		 * Look up an index and return the corresponding passenger details.
		 * 
		 * @param idx The index to be looked up.
		 * @return The passenger details corresponding to the index
		 */
		public Passenger getByIdx(int idx) {
			return passengerList.get(idx);
		}

		/**
		 * @return The number of entries currently in the flight.
		 */
		public int getNumberOfEntries() {
			return passengerList.size();
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
		 * Look up a last name and reference code
		 * 
		 * @param lastName       The last name to be looked up.
		 * @param reference_code The reference code to be looked up.
		 * @return The index, -1 if none
		 */
		public int findByLastName(String lastName, String reference_code) {
			for (int i = 0; i < passengerList.size(); i++) {
				Passenger p = getByIdx(i);
				String name = p.getName();
				String last_name = name.split(" ")[1];
				if (last_name.equals(lastName) && p.getReference().equals(reference_code)) {
					return i;
				}
			}
			return -1;
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
