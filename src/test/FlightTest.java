package test;

import static org.junit.jupiter.api.Assertions.*;

import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightTest {
	
	Flight flight = new Flight();
	
	@BeforeEach
	void testFlight() throws InvalidAttributeException {
		Manager manager = new Manager();
		manager.readFromFile("data/flight_details_data.csv", "data/passenger_data.csv");
		this.flight = manager.getFlightList().getFlight(1);
	}

	@Test
	void testGetDestination() {
		String destination = this.flight.getDestination();
		assertEquals("LHR", destination, "Equal!");
		assertNotEquals("SIN", destination, "Error!");
	}

	@Test
	void testGetCarrier() {
		String carrier = this.flight.getCarrier();
		assertEquals("EK", carrier, "Equal!");
		assertNotEquals("KL", carrier, "Error!");
	}

	@Test
	void testGetFlight() {
		String flight_code = this.flight.getFlight();
		assertEquals("EK216", flight_code, "Equal!");
		assertNotEquals("BA524", flight_code, "Error!");
	}

	@Test
	void testGetCapacity() {
		int capacity = this.flight.getCapacity();
		assertEquals(256, capacity, "Equal!");
		assertNotEquals(255, capacity, "Error!");
	}

	@Test
	void testGetWeight() {
		double weight = this.flight.getWeight();
		assertEquals(3988.0, weight, "Equal!");
		assertNotEquals(3920.0, weight, "Error!");
	}

	@Test
	void testGetVolume() {
		double volume = this.flight.getVolume();
		assertEquals(6468.0, volume, "Equal!");
		assertNotEquals(6420.0, volume, "Error!");
	}

	@Test
	void testEqualsObject() {
		boolean result1 = this.flight.equals(this.flight);
		boolean result2 = this.flight.equals(null);
		assertEquals(true, result1, "Equal!");
		assertNotEquals(false, result1, "Error!");
		assertEquals(false, result2, "Equal!");
		assertNotEquals(true, result2, "Error!");
		
	}

	@Test
	void testNumberOfCheckIn() {
		int number = this.flight.numberOfCheckIn();
		assertEquals(125, number, "Equal!");
		assertNotEquals(126, number, "Error!");
	}

	@Test
	void testTotalWeight() {
		double number = this.flight.totalWeight();
		assertEquals(2025.0, number, "Equal!");
		assertNotEquals(2026.5, number, "Error!");
	}

	@Test
	void testTotalVolume() {
		double number = this.flight.totalVolume();
		assertEquals(3237.0, number, "Equal!");
		assertNotEquals(3235.0, number, "Error!");
	}

	@Test
	void testTotalFees() {
		double number = this.flight.totalFees();
		assertEquals(5262.0, number, "Equal!");
		assertNotEquals(5260.0, number, "Error!");
	}

}
