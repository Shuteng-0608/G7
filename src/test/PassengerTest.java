package test;

import static org.junit.jupiter.api.Assertions.*;
import checkinSys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import myExceptions.*;

class PassengerTest {

	Passenger passenger = new Passenger();

	// This method sets up the Passenger object before each test.
	// It initializes the passenger object by retrieving it from a flight managed by a Manager object.
	@BeforeEach
	void testPassenger() throws InvalidAttributeException {
		Manager manager = new Manager();
		this.passenger = manager.getFlightList().getFlight(0).getList().getByIdx(0);
	}

	// Test for getting the reference of the passenger.
	// It checks if the actual reference matches the expected one and that it does not match a wrong reference.
	@Test
	void testGetReference() {
		String reference = this.passenger.getReference();
		assertEquals("DXBCA3781807232238", reference, "Equal!");
		assertNotEquals("JFKCA3191811247604", reference, "Error!");
	}

	// Test for getting the name of the passenger.
	// It checks if the actual name matches the expected one and that it does not match a wrong name.
	@Test
	void testGetName() {
		String name = this.passenger.getName();
		assertEquals("Diane Brewer", name, "Equal!");
		assertNotEquals("John Mason", name, "Error!");
	}

	// Test for getting the flight code associated with the passenger.
	// It checks if the actual flight code matches the expected one and that it does not match a wrong flight code.
	@Test
	void testGetFlight() {
		String flight_code = this.passenger.getFlight();
		assertEquals("CA378", flight_code, "Equal!");
		assertNotEquals("EK350", flight_code, "Error!");
	}

	// Test for getting the check-in status of the passenger.
	// It checks if the actual check-in status matches the expected status and that it does not match a wrong status.
	@Test
	void testGetCheckin() {
		String result = this.passenger.getCheckin();
		assertEquals("No", result, "Equal!");
		assertNotEquals("Yes", result, "Error!");
	}

	// Test for getting the weight of the passenger's luggage.
	// It checks if the actual weight matches the expected weight and that it does not match a wrong weight.
	@Test
	void testGetWeight() {
		double weight = this.passenger.getWeight();
		assertEquals(26.42, weight, "Equal!");
		assertNotEquals(16.0, weight, "Error!");
	}

	// Test for getting the volume of the passenger's luggage.
	// It checks if the actual volume matches the expected volume and that it does not match a wrong volume.
	@Test
	void testGetVolume() {
		double volume = this.passenger.getVolume();
		assertEquals(1.1, volume, "Equal!");
		assertNotEquals(5.0, volume, "Error!");
	}

	// Test for calculating the excess fee based on the passenger's luggage.
	// It checks if the calculated fee matches the expected fee and that it does not match a wrong fee.
	@Test
	void testExcess_fee() {
		double excess_fee = this.passenger.excess_fee();
		assertEquals(19.26, excess_fee, "Equal!");
		assertNotEquals(15.36, excess_fee, "Error!");
	}

	// Test for verifying the equality of Passenger objects.
	// It checks if the object is equal to itself and not equal to null.
	@Test
	void testEqualsObject() {
		boolean result1 = this.passenger.equals(this.passenger);
		boolean result2 = this.passenger.equals(null);
		assertEquals(true, result1, "Equal!");
		assertNotEquals(false, result1, "Error!");
		assertEquals(false, result2, "Equal!");
		assertNotEquals(true, result2, "Error!");
	}
	
}
