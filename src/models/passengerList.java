package models;
import java.util.ArrayList;

public class passengerList {
	// Storage for an arbitrary number of details.
		private ArrayList<passenger> passengerList;

		/**
		 * Perform any initialization for the text file.
		 */
		public passengerList() {
			passengerList = new ArrayList<passenger>();
		}

		/**
		 * Look up a name and return the corresponding passenger details.
		 * 
		 * @param name The name to be looked up.
		 * @return The details corresponding to the name, null if none
		 */
		public passenger findByName(String name) {
			for (passenger p : passengerList) {
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
		public void addPassenger(passenger passenger) {
			passengerList.add(passenger);
		}
		
		/**
		 * Remove Passenger object
		 * 
		 * @param passenger The passenger object
		 */
		public void removePassenger(passenger passenger) {
			passengerList.remove(passenger);
		}
		
		/**
		 * Retrieves a Passenger by their index in the list.
		 *
		 * @param idx The index of the passenger in the list.
		 * @return Passenger object at the specified index.
		 */
		public passenger getByIdx(int idx) {
			return passengerList.get(idx);
		}

		/**
		 * Finds the index of a Passenger by their last name and reference code.
		 * @param lastName       The last name of the passenger.
		 * @param reference_code The reference code of the passenger.
		 * @return Index of the Passenger in the list, -1 if not found.
		 */
		public int findByLastName(String lastName, String reference_code) {
			int size = passengerList.size();
			for (int i = 0; i < size; i++) {
				passenger p = passengerList.get(i);
				String name = p.getName();
				String last = name.split(" ")[1];
				if (last.equals(lastName) && p.getReference().equals(reference_code)) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * @return Returns the number of Passengers in the list.
		 */
		public int getNumberOfEntries() {
			return passengerList.size();
		}

		/**
		 * @return All the passenger details
		 */
		public String listDetails() {
			StringBuffer allEntries = new StringBuffer();
			for (passenger details : passengerList) {
				allEntries.append(details);
				allEntries.append('\n');
			}
			return allEntries.toString();
		}
	}
