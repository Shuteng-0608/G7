package models;
import java.util.ArrayList;

public class flightList {

	// Storage for an arbitrary number of flights.
		private ArrayList<flight> flightList;

		/**
		 * Perform any initialization for the flight list.
		 */
		public flightList() {
			flightList = new ArrayList<flight>();
		}

		/**
		 * Add a new set of details to the list
		 * 
		 * @param flight The object of the flight
		 */
		public void addFlight(flight flight) {
			flightList.add(flight);
		}

		/**
		 * Look up an index and return the corresponding flight object.
		 * 
		 * @param idx The index to be looked up.
		 * @return The flight object corresponding to the index.
		 */
		public flight getFlight(int idx) {
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
		public flight findByCode(String flight_code) {
			for (flight f : flightList) {
				if (f.getFlightCode().equals(flight_code)) {
					return f;
				}
			}
			return null;
		}
	}