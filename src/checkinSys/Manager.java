/* A class to implement specific functions of GUI and manage all flight data. */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import myExceptions.*;

public class Manager {
	private HashMap<String, Flight> flights = new HashMap<String, Flight>(); // flight code -> flight object
	private HashMap<String, Passenger> passengers = new HashMap<String, Passenger>(); // name -> passenger object
	private FlightList flightList = new FlightList();

	/**
	 * Perform any initialization for the class.
	 */
	public Manager() throws InvalidAttributeException {
		readFromFile("D:\\1125115069\\FileRecv\\flight_details_data.csv",
				"D:\\1125115069\\FileRecv\\passenger_data.csv");
	};

	/**
	 * Read flight and passenger data from text files.
	 * 
	 * @param file_flights    The text file containing flight data.
	 * @param file_passengers The text file containing passenger data.
	 */
	public void readFromFile(String file_flights, String file_passengers) throws InvalidAttributeException {
		try {
			// Process flights file
			FileReader fr = new FileReader(file_flights);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			while ((line = br.readLine()) != null) {
				try {
					validateFlightData(line);
				} catch (InvalidAttributeException e) {
					System.out.println("Invalid Flight data: " + e.getMessage());
				}
				String flight_code = line.split(",")[0];
				String date = line.split(",")[1];
				String destination = line.split(",")[2];
				String carrier = line.split(",")[3];
				int capacity = Integer.parseInt(line.split(",")[4]);
				double maxWeight = Double.parseDouble(line.split(",")[5]);
				double maxVolume = Double.parseDouble(line.split(",")[6]);
				Flight flight = new Flight(flight_code, date, destination, carrier, capacity, maxWeight, maxVolume);
				flightList.addFlight(flight);
				flights.put(flight_code, flight);
			}

			// Process passengers file
			fr = new FileReader(file_passengers);
			br = new BufferedReader(fr);
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				try {
					validatePassengerData(line);
				} catch (InvalidAttributeException e) {
					System.out.println("Invalid Passenger data: " + e.getMessage());
				}
				String reference_code = line.split(",")[0];
				String name = line.split(",")[1];
				String flight_code = line.split(",")[2];
				String date = line.split(",")[3];
				String check_in = line.split(",")[4];
				double weight = Double.parseDouble(line.split(",")[5]);
				double volume = Double.parseDouble(line.split(",")[6]);
				Passenger p = new Passenger(reference_code, name, flight_code, date, check_in, weight, volume);
				Flight objFlight = findFlight(flight_code);
				objFlight.getList().addPassenger(p);
				passengers.put(name, p);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateFlightData(String line) throws InvalidAttributeException {
		// ====== For Flight information ======
		// "Flight Code", "Date", "Destination Airport", "Carrier", "Passengers", "Total
		// Baggage Weight (kg)", "Total Baggage Volume (m^3)"
		String[] fields = line.split(",");
		if (fields.length != 6) {
			throw new InvalidAttributeException("Invalid number of Flight data");
		}

		String flight_code = fields[0];
		String destination = fields[2];
		String carrier = fields[3];
		String capacity = fields[4];
		String weight = fields[5];
		String volume = fields[6];
		// for flight_code
		if (flight_code.isEmpty())
			throw new InvalidAttributeException("Flight code cannot be empty");
		// for destination
		if (destination.isEmpty())
			throw new InvalidAttributeException("Destination cannot be empty");
		// for carrier
		if (carrier.isEmpty())
			throw new InvalidAttributeException("Carrier cannot be empty");
		// for capacity
		try {
			int capacity_ = Integer.parseInt(capacity);
			if (capacity_ < 0)
				throw new InvalidAttributeException("Capacity must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Capacity must be a valid integer");
		}
		// for weight
		try {
			double weight_ = Double.parseDouble(weight);
			if (weight_ < 0)
				throw new InvalidAttributeException("Weight must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Weight must be a valid integer");
		}
		// for volume
		try {
			double volume_ = Double.parseDouble(volume);
			if (volume_ < 0)
				throw new InvalidAttributeException("Volume must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Volume must be a valid integer");
		}
	}

	public void validatePassengerData(String line) throws InvalidAttributeException {
		// ====== For Passenger information ======
		// "Booking Code", "Name", "Flight Code", "Date", "Checked In", "Baggage Weight
		// (kg)", "Baggage Volume (m^3)"
		String[] fields = line.split(",");
		if (fields.length != 6) {
			throw new InvalidAttributeException("Invalid number of Passenger data");
		}

		String reference_code = line.split(",")[0];
		String name = line.split(",")[1];
		String flight_code = line.split(",")[2];
		String check_in = line.split(",")[4];
		String weight = line.split(",")[5];
		String volume = line.split(",")[6];
		// for reference_code
		if (reference_code.isEmpty())
			throw new InvalidAttributeException("Reference code cannot be empty");
		// for name
		if (name.isEmpty())
			throw new InvalidAttributeException("Name cannot be empty");
		// for flight_code
		if (flight_code.isEmpty())
			throw new InvalidAttributeException("Flight code cannot be empty");
		// for check_in
		if (check_in.isEmpty())
			throw new InvalidAttributeException("Check-in cannot be empty");

		// for weight
		try {
			double weight_ = Double.parseDouble(weight);
			if (weight_ < 0)
				throw new InvalidAttributeException("Weight must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Weight must be a valid integer");
		}
		// for volume
		try {
			double volume_ = Double.parseDouble(volume);
			if (volume_ < 0)
				throw new InvalidAttributeException("Volume must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Volume must be a valid integer");
		}
	}

	/**
	 * Look up a passenger by last name and reference code.
	 * 
	 * @param last_name Passenger's last name
	 * @param br        Passenger's reference code
	 * @return The passenger object, null if none.
	 */
	public Passenger findPassenger(String last_name, String br) {
		if(br.length() < 8) return null;
		String flight_code = br.substring(3, 8);
		Flight flight = findFlight(flight_code);
		int idx = flight.getList().findByLastName(last_name, br);
		if (idx != -1)
			return flight.getList().getByIdx(idx);
		return null;
	}

	/**
	 * Calculate the excess baggage fee that needs to be paid.
	 * 
	 * @param last_name Passenger's last name
	 * @param br        Passenger's reference code
	 * @param weight    Baggage weight
	 * @param volume    Baggage volume
	 * @return The excess baggage fee
	 */
	public double excess_fee(String last_name, String br, double weight, double volume) {
		Passenger p = findPassenger(last_name, br);
		p.set(weight, volume);
		return p.excess_fee();
	}

	/**
	 * Check that the booking reference code is correct according to our rules.
	 * 
	 * @param name Passenger's name
	 * @return true if correct, false otherwise.
	 */
	public boolean check_rc(String name) {
		Passenger p = findPassenger(name);
		String rc = p.getReference();
		String fc = p.getFlight();
		Flight f = flights.get(fc);
		String des = f.getDestination();
		String tmp1 = des + fc;
		String tmp2 = rc.substring(0, 8);
		if (tmp1.equals(tmp2))
			return true;
		else
			return false;
	}

	/**
	 * Help passengers check in.
	 * 
	 * @param last_name Passenger's last name
	 * @param br        Passenger's reference code
	 * @return true if checked-in, false otherwise.
	 */
	public boolean check_in(String last_name, String br) {
		Passenger p = findPassenger(last_name, br);
		return p.check_in();
	}

	/**
	 * Look up a flight in the HashMap.
	 * 
	 * @param flight_code The flight code.
	 * @return The flight object.
	 */
	public Flight findFlight(String flight_code) {
		return flights.get(flight_code);
	}

	/**
	 * Look up a passenger in the HashMap.
	 * 
	 * @param name The passenger's name.
	 * @return The passenger object.
	 */
	public Passenger findPassenger(String name) {
		return passengers.get(name);
	}

	/**
	 * @return A hash table of flight information.
	 */
	public HashMap<String, Flight> getFlights() {
		return flights;
	}

	/**
	 * @return A hash table of passenger information.
	 */
	public HashMap<String, Passenger> getPassengers() {
		return passengers;
	}

	/**
	 * @return A list of flights.
	 */
	public FlightList getFlightList() {
		return flightList;
	}
}
