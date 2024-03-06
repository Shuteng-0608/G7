package test;

import static org.junit.jupiter.api.Assertions.*;
import checkinSys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import myExceptions.*;

class PassengerTest {

	Passenger passenger = new Passenger();
	
	@BeforeEach
	void testPassenger() throws InvalidAttributeException {
		Manager manager = new Manager();
		this.passenger = manager.getFlightList().getFlight(0).getList().getByIdx(0);
	}

	@Test
	void testGetReference() {
		String reference = this.passenger.getReference();
		assertEquals("DXBCA3781807232238", reference, "Equal!");
		assertNotEquals("JFKCA3191811247604", reference, "Error!");
	}

	@Test
	void testGetName() {
		String name = this.passenger.getName();
		assertEquals("Diane Brewer", name, "Equal!");
		assertNotEquals("John Mason", name, "Error!");
	}

	@Test
	void testGetFlight() {
		String flight_code = this.passenger.getFlight();
		assertEquals("CA378", flight_code, "Equal!");
		assertNotEquals("EK350", flight_code, "Error!");
	}

	@Test
	void testGetCheckin() {
		String result = this.passenger.getCheckin();
		assertEquals("No", result, "Equal!");
		assertNotEquals("Yes", result, "Error!");
	}

	@Test
	void testGetWeight() {
		double weight = this.passenger.getWeight();
		assertEquals(26.42, weight, "Equal!");
		assertNotEquals(16.0, weight, "Error!");
	}

	@Test
	void testGetVolume() {
		double volume = this.passenger.getVolume();
		assertEquals(1.1, volume, "Equal!");
		assertNotEquals(5.0, volume, "Error!");
	}

//	@Test
//	void testExcess_fee() {
//		double excess_fee = this.passenger.excess_fee();
//		assertEquals(3.0, excess_fee, "Equal!");
//		assertNotEquals(5.0, excess_fee, "Error!");
//	}

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
