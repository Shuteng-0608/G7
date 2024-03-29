package checkinSys;
// This class manages the airport check-in system functionality, including managing flight and passenger data.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import myExceptions.*;

public class Manager {
	// Maps to store flights and passengers data
	private HashMap<String, Flight> flights = new HashMap<String, Flight>(); // flight code -> flight object
	private HashMap<String, Passenger> passengers = new HashMap<String, Passenger>(); // name -> passenger object
	private FlightList flightList = new FlightList();

	/**
	 * Constructor for Manager. Initializes the class and reads data from files.
	 */
	public Manager() {
	    try {
	        readFromFile("src/data/flight_details_data.csv", 
	                     "src/data/passenger_data.csv");
	    } catch (InvalidAttributeException | IOException | InvalidBookRefException e) {
	        e.printStackTrace();// Print the stack trace in case of an exception
		}
	}

	/**
	 * Reads flight and passenger data from files and populates data structures.
	 *
	 * @param file_flights    Path to the flight data file.
	 * @param file_passengers Path to the passenger data file.
	 * @throws IOException                   If there is an error in file handling.
	 * @throws InvalidAttributeException     If there is invalid flight data.
	 * @throws InvalidBookRefException       If there is invalid booking reference data.
	 */
	public void readFromFile(String file_flights, String file_passengers) throws IOException, InvalidAttributeException, InvalidBookRefException {
		try (BufferedReader brFlights = new BufferedReader(new FileReader(file_flights))) {
		    System.out.println("Reading Flights Information"); 
		    String line= brFlights.readLine();
		    line= brFlights.readLine();
		    while (line != null) {
		        try {
		            validateFlightData(line);
//		            System.out.println("Valid Flight data");
		        } catch (InvalidAttributeException e) {
		            System.out.println("Invalid Flight data: " + e.getMessage());
		            line = brFlights.readLine();
		            continue; // Skip this line and proceed with the next one
		        }
		
		        String[] flightData = line.split(",");
		        String flight_code = flightData[0];
		        String date = flightData[1];
		        String destination = flightData[2];
		        String carrier = flightData[3];
		        int capacity = Integer.parseInt(flightData[4]);
		        double weight = Double.parseDouble(flightData[5]);
		        double volume = Double.parseDouble(flightData[6]);
		        Flight flight = new Flight(flight_code, date, destination, carrier, capacity, weight, volume);
		        flightList.addFlight(flight);
		        flights.put(flight_code, flight);
		        line = brFlights.readLine();
		    }
		    
		}

	    try (BufferedReader brPassengers = new BufferedReader(new FileReader(file_passengers))) {
	        String line = brPassengers.readLine();
	        System.out.println("Reading Passenger Information");
	        while ((line = brPassengers.readLine()) != null) {
	            try {
	                validatePassengerData(line);
	            } catch (InvalidBookRefException |InvalidAttributeException e) {
	                System.out.println("Invalid Passenger data: " + e.getMessage());
	                continue; // Skip this line and proceed with the next one
	            }
	        }
	        
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

	/**
	 * validate Flight Data legal or not
	 * 
	 * @param line The reading-line of flight data (excepted length 7)
	 * @exception InvalidAttributeException Invalid input attribute
	 */
	public void validateFlightData(String line) throws InvalidAttributeException {
		// ====== For Flight information ======
		// "Flight Code", "Date", "Destination Airport", "Carrier", "Passengers", "Total Baggage Weight (kg)", "Total Baggage Volume (m^3)"
		String[] fields = line.split(",");
		if (fields.length != 7) {
			throw new InvalidAttributeException("Invalid number of Flight data");
		}

		String flight_code = fields[0];
		String date = fields[1];
		String destination = fields[2];
		String carrier = fields[3];
		String capacity = fields[4];
		String weight = fields[5];
		String volume = fields[6];
		// for flight_code
		if (flight_code.isEmpty()) throw new InvalidAttributeException("Flight code cannot be empty");
		// for date
		if (date.isEmpty()) throw new InvalidAttributeException("Flight date cannot be empty");
		// for destination
		if (destination.isEmpty()) throw new InvalidAttributeException("Destination cannot be empty");
		// for carrier
		if (carrier.isEmpty()) throw new InvalidAttributeException("Carrier cannot be empty");
		// for capacity
		try {
			int capacity_ = Integer.parseInt(capacity);
			if (capacity_ < 0) throw new InvalidAttributeException("Capacity must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Capacity must be a valid integer");
		}
		// for weight
		try {
			double weight_ = Double.parseDouble(weight);
			if (weight_ < 0) throw new InvalidAttributeException("Weight must be a non-negative Double");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Weight must be a valid Double");
		}
		// for volume
		try {
			double volume_ = Double.parseDouble(volume);
			if (volume_ < 0) throw new InvalidAttributeException("Volume must be a non-negative double");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Volume must be a valid double");
		}
	}

	/**
	 * validate Passenger Data legal or not
	 * 
	 * @param fields The reading-line of flight data (excepted length 7)
	 * @exception InvalidAttributeException Invalid input attribute
	 * @exception InvalidBookRefException Invalid booking reference
	 */
	public void validatePassengerData(String line) throws InvalidAttributeException, InvalidBookRefException {
		// ====== For Passenger information ======
		// "Booking Code", "Name", "Flight Code", "Date", "Checked In", "Baggage Weight (kg)", "Baggage Volume (m^3)"
		String[] fields = line.split(",");
		if (fields.length != 7) {
			throw new InvalidAttributeException("Invalid number of Passenger data");
		}

		String reference_code = fields[0];
		String name = fields[1];
		String flight_code = fields[2];
		String date = fields[3];
		String check_in = fields[4];
		String weight = fields[5];
		String volume = fields[6];
		
		// for reference_code
		if (reference_code.isEmpty()) throw new InvalidAttributeException("Reference code cannot be empty");
		
		// for name
		if (name.isEmpty()) throw new InvalidAttributeException("Name cannot be empty");
		// for flight_code
		if (flight_code.isEmpty()) throw new InvalidAttributeException("Flight code cannot be empty");
		// for flight_code
		if (date.isEmpty()) throw new InvalidAttributeException("date cannot be empty");
		// for check_in
		if (check_in.isEmpty()) throw new InvalidAttributeException("Check-in cannot be empty");

		// for weight
		try {
			double weight_ = Double.parseDouble(weight);
			if (weight_ < 0) throw new InvalidAttributeException("Weight must be a non-negative Double");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Weight must be a valid Double");
		}
		// for volume
		try {
			double volume_ = Double.parseDouble(volume);
			if (volume_ < 0) throw new InvalidAttributeException("Volume must be a non-negative Double");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Volume must be a valid Double");
		}
		Passenger p = new Passenger(reference_code, name, flight_code, date, check_in, Double.parseDouble(weight), Double.parseDouble(volume));
        passengers.put(name, p);
        // reference code legal check
        if (!check_rc(name)) {
			passengers.remove(name, p);
			throw new InvalidBookRefException("Reference code doesn't match, it's illegal!");
		} else {
			Flight objFlight = findFlight(flight_code);
            objFlight.getList().addPassenger(p);
		}

	}

	/**
	 * When the application exits, it should generate a report. This should indicate, for each flight,
	 * the number of passengers checked-in, the total weight of their baggage, the total volume of
	 * their baggage, and the total excess baggage fees collected. It should also indicate whether
	 * the capacity of the flight is exceeded in any way.
	 */
	public void report() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/report.txt"))) {
			for (int i = 0; i < flightList.getNumberOfEntries(); i++) {
				Flight f = flightList.getFlight(i);
				writer.append("\r\n*******************************************");
				writer.append("\r\nFor flight " + f.getFlight() + ":");
				writer.append("\r\nThe flight carrier is: " + f.getCarrier());
				writer.append("\r\nThe number of passengers due to arrive is " + f.getList().getNumberOfEntries());
				writer.append("\r\nThe number of passengers checked-in is " + f.numberOfCheckIn());
				writer.append("\r\nThe total weight of their baggage is " + f.totalWeight());
				writer.append("\r\nThe total volume of their baggage is " + f.totalVolume());
				writer.append("\r\nThe total excess baggage fees collected is " + f.totalVolume());
				writer.append("\r\nThe maximum baggage weight of this flight is " + f.getWeight());
				writer.append("\r\nThe maximum baggage volume of this flight is " + f.getVolume());
				if (f.totalWeight() <= f.getWeight() && f.totalVolume() <= f.getVolume() && f.numberOfCheckIn() <= f.getCapacity()) {
					writer.append("\r\nThe capacity of the flight is not exceeded");
				} else
					writer.append("\r\nThe capacity of the flight is exceeded");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


