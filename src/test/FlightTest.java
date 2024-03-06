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
		assertEquals(266, capacity, "Equal!");
		assertNotEquals(255, capacity, "Error!");
	}

	@Test
	void testGetWeight() {
		double weight = this.flight.getWeight();
		assertEquals(9310.0, weight, "Equal!");
		assertNotEquals(9302.0, weight, "Error!");
	}

	@Test
	void testGetVolume() {
		double volume = this.flight.getVolume();
		assertEquals(665.0, volume, "Equal!");
		assertNotEquals(660.0, volume, "Error!");
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
		assertEquals(130, number, "Equal!");
		assertNotEquals(126, number, "Error!");
	}

	@Test
	void testTotalWeight() {
		double number = this.flight.totalWeight();
		assertEquals(2784.13, number, "Equal!");
		assertNotEquals(2782.13, number, "Error!");
	}

	@Test
	void testTotalVolume() {
		double number = this.flight.totalVolume();
		assertEquals(188.91, number, "Equal!");
		assertNotEquals(145.43, number, "Error!");
	}

	@Test
	void testTotalFees() {
		double number = this.flight.totalFees();
		assertEquals(1941.49, number, "Equal!");
		assertNotEquals(1941.50, number, "Error!");
	}

	@Test
	void testCheck_weight() {
		boolean result = this.flight.check_weight();
		assertEquals(true, result, "Equal!");
		assertNotEquals(false, result, "Error!");
	}	

	@Test
	void testCheck_volume() {
		boolean result = this.flight.check_volume();
		assertEquals(true, result, "Equal!");
		assertNotEquals(false, result, "Error!");
	}
}
