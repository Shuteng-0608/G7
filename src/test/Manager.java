package checkinSys;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Manager {
	private HashMap<String, Flight> flights = new HashMap<String, Flight>();
	private HashMap<String, Passenger> passengers = new HashMap<String, Passenger>();
	private FlightList flightList = new FlightList();
	
	public Manager() throws InvalidAttributeException {
		readFromFile("data/flight_details_data.csv", "data/passenger_data.csv");
	};
	
	
	public void readFromFile(String file_flights, String file_passengers) throws InvalidAttributeException{
		try {
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
				String destination = line.split(",")[2];
				String carrier = line.split(",")[3];
				int capacity = Integer.parseInt(line.split(",")[4]);
				double weight = Double.parseDouble(line.split(",")[5]);
				double volume = Double.parseDouble(line.split(",")[6]);
				Flight flight = new Flight(flight_code, destination, carrier, capacity, weight, volume);
				flightList.addFlight(flight);
				flights.put(flight_code, flight);
			}

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
				String check_in = line.split(",")[4];
				double weight = Double.parseDouble(line.split(",")[5]);
				double volume = Double.parseDouble(line.split(",")[6]);
				Passenger p = new Passenger(reference_code, name, flight_code, check_in, weight, volume);
				passengers.put(name, p);
				Flight objFlight = findFlight(flight_code);
				objFlight.getList().addPassenger(p);

			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// allow the passenger to enter their last name and booking reference
	public Passenger findPassenger(String last_name, String br) {
		String flight_code = br.substring(3, 8);
		Flight flight = flights.get(flight_code);
		int idx = flight.getList().findByLastName(last_name, br);
		if (idx != -1)
			return flight.getList().getByIdx(idx);
		return null;
	}

	// ask for the dimensions and weight of the passenger’s baggage
	// and indicate any excess baggage fee that needs to be paid
	public double excess_fee(String last_name, String br, double weight, double volume) {
		Passenger p = findPassenger(last_name, br);
		if (p == null)
			return -1;
		p.set(weight, volume);
		return p.excess_fee(); //////////////////////////////////////////////////////////
	}

	// check that the booking reference code is correct according to our rules.
	public boolean check_rc(String name) {
		Passenger p = passengers.get(name);
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

	// true 表示checkin成功,反之失败
	public boolean check_in(String last_name, String br) {
		Passenger p = findPassenger(last_name, br);
		if (p == null)
			return false;
		p.check_in();
		return true;
	}

	// This should indicate, for each flight,
	// the number of passengers checked-in, the total weight of their baggage, the
	// total volume of
	// their baggage, and the total excess baggage fees collected. It should also
	// indicate whether
	// the capacity of the flight is exceeded in any way.
	public void report() {
		for (int i = 0; i < flightList.getNumberOfEntries(); i++) {
			Flight f = flightList.getFlight(i);
			System.out.println("*******************************************");
			System.out.println("For flight " + f.getCarrier() + ":");
			System.out.println("The number of passengers checked-in is " + f.numberOfCheckIn());
			System.out.println("The total weight of their baggage is " + f.totalWeight());
			System.out.println("The total volume of their baggage is " + f.totalVolume());
			System.out.println("The total excess baggage fees collected is " + f.totalVolume());
			System.out.println("The maximum baggage weight of this flight is " + f.getWeight());
			System.out.println("The maximum baggage volume of this flight is " + f.getVolume());
			if (f.totalWeight() <= f.getWeight() && f.totalVolume() <= f.getVolume()) {
				System.out.println("The capacity of the flight is not exceeded");
			} else
				System.out.println("The capacity of the flight is exceeded");
			System.out.println("*******************************************");
		}
	}

	// 在flights map查找某一个flight
	public Flight findFlight(String flight_code) {
		return flights.get(flight_code);
	}

	public HashMap<String, Flight> getFlights() {
		return flights;
	}

	public HashMap<String, Passenger> getPassengers() {
		return passengers;
	}

	public FlightList getFlightList() {
		return flightList;
	}

	public void validateFlightData(String line) throws InvalidAttributeException {
		// ====== For Flight information ======
		// "Flight Code", "Date", "Destination Airport", "Carrier", "Passengers", "Total Baggage Weight (kg)", "Total Baggage Volume (m^3)"
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
		if (flight_code.isEmpty()) throw new InvalidAttributeException("Flight code cannot be empty");
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
			if (weight_ < 0) throw new InvalidAttributeException("Weight must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Weight must be a valid integer");
		}
		// for volume
		try {
			double volume_ = Double.parseDouble(volume);
			if (volume_ < 0) throw new InvalidAttributeException("Volume must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Volume must be a valid integer");
		}
	}

	public void validatePassengerData(String line) throws InvalidAttributeException {
		// ====== For Passenger information ======
		// "Booking Code", "Name", "Flight Code", "Date", "Checked In", "Baggage Weight (kg)", "Baggage Volume (m^3)"
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
		if (reference_code.isEmpty()) throw new InvalidAttributeException("Reference code cannot be empty");
		// for name
		if (name.isEmpty()) throw new InvalidAttributeException("Name cannot be empty");
		// for flight_code
		if (flight_code.isEmpty()) throw new InvalidAttributeException("Flight code cannot be empty");
		// for check_in
		if (check_in.isEmpty()) throw new InvalidAttributeException("Check-in cannot be empty");

		// for weight
		try {
			double weight_ = Double.parseDouble(weight);
			if (weight_ < 0) throw new InvalidAttributeException("Weight must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Weight must be a valid integer");
		}
		// for volume
		try {
			double volume_ = Double.parseDouble(volume);
			if (volume_ < 0) throw new InvalidAttributeException("Volume must be a non-negative integer");
		} catch (NumberFormatException e) {
			throw new InvalidAttributeException("Volume must be a valid integer");
		}

	}

}


