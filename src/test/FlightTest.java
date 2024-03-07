package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightTest {
	
	Flight flight = new Flight();

	// This method is executed before each test. It sets up the Flight object
	// by reading data from files and fetching a specific flight from the manager.
	@BeforeEach
	void testFlight() throws InvalidAttributeException, IOException, InvalidBookRefException {
		Manager manager = new Manager();
		manager.readFromFile("data/flight_details_data.csv", "data/passenger_data.csv");
		this.flight = manager.getFlightList().getFlight(1);
	}

	// Test for getting the destination of the flight.
	@Test
	void testGetDestination() {
		String destination = this.flight.getDestination();
		assertEquals("LHR", destination, "Equal!");
		assertNotEquals("SIN", destination, "Error!");
	}

	// Test for getting the carrier of the flight.
	@Test
	void testGetCarrier() {
		String carrier = this.flight.getCarrier();
		assertEquals("EK", carrier, "Equal!");
		assertNotEquals("KL", carrier, "Error!");
	}

	// Test for getting the flight code.
	@Test
	void testGetFlight() {
		String flight_code = this.flight.getFlight();
		assertEquals("EK216", flight_code, "Equal!");
		assertNotEquals("BA524", flight_code, "Error!");
	}

	// Test for getting the capacity of the flight.
	@Test
	void testGetCapacity() {
		int capacity = this.flight.getCapacity();
		assertEquals(266, capacity, "Equal!");
		assertNotEquals(255, capacity, "Error!");
	}

	// Test for getting the weight of the flight.
	@Test
	void testGetWeight() {
		double weight = this.flight.getWeight();
		assertEquals(9310.0, weight, "Equal!");
		assertNotEquals(9302.0, weight, "Error!");
	}

	// Test for getting the volume of the flight.
	@Test
	void testGetVolume() {
		double volume = this.flight.getVolume();
		assertEquals(665.0, volume, "Equal!");
		assertNotEquals(660.0, volume, "Error!");
	}

	// Test for checking equality of Flight objects.
	@Test
	void testEqualsObject() {
		boolean result1 = this.flight.equals(this.flight);
		boolean result2 = this.flight.equals(null);
		assertEquals(true, result1, "Equal!");
		assertNotEquals(false, result1, "Error!");
		assertEquals(false, result2, "Equal!");
		assertNotEquals(true, result2, "Error!");
		
	}

	// Test for getting the number of checked-in passengers.
	@Test
	void testNumberOfCheckIn() {
		int number = this.flight.numberOfCheckIn();
		assertEquals(130, number, "Equal!");
		assertNotEquals(126, number, "Error!");
	}

	// Test for calculating the total weight of checked-in luggage.
	@Test
	void testTotalWeight() {
		double number = this.flight.totalWeight();
		assertEquals(2784.13, number, "Equal!");
		assertNotEquals(2782.13, number, "Error!");
	}

	// Test for calculating the total volume of checked-in luggage.
	@Test
	void testTotalVolume() {
		double number = this.flight.totalVolume();
		assertEquals(188.91, number, "Equal!");
		assertNotEquals(145.43, number, "Error!");
	}

	// Test for calculating the total fees based on checked-in luggage.
	@Test
	void testTotalFees() {
		double number = this.flight.totalFees();
		assertEquals(1941.49, number, "Equal!");
		assertNotEquals(1941.50, number, "Error!");
	}

	// Test to check if the total weight of checked-in luggage is within acceptable limits.
	@Test
	void testCheck_weight() {
		boolean result = this.flight.check_weight();
		assertEquals(true, result, "Equal!");
		assertNotEquals(false, result, "Error!");
	}

	// Test to check if the total volume of checked-in luggage is within acceptable limits.
	@Test
	void testCheck_volume() {
		boolean result = this.flight.check_volume();
		assertEquals(true, result, "Equal!");
		assertNotEquals(false, result, "Error!");
	}
}
