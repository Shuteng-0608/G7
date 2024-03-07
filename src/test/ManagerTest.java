package test;

import static org.junit.jupiter.api.Assertions.*;

import checkinSys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import myExceptions.*;

class ManagerTest {
	
	
	Manager manager;

	// This method is executed before each test.
	// It sets up a Manager object for use in the following test cases.
	@BeforeEach
	void testManager() throws InvalidAttributeException {
		manager = new Manager();
	}

	// Test method to verify finding a passenger by surname and booking reference.
	// Asserts that the returned passenger's name is correct and does not match an incorrect name.
	@Test
	void testFindPassenger() throws InvalidBookRefException {
		Passenger p = manager.findPassenger("Brewer", "DXBCA3781807232238");
		assertEquals("Diane Brewer", p.getName(), "Equal!");
		assertNotEquals("Stephanie Gomez", p.getName(), "Error!");
	}

	// Test method to verify the calculation of excess baggage fee.
	// Asserts that the calculated fee is as expected and does not match an incorrect value.
	@Test
	void testExcess_fee() throws InvalidBookRefException {
		double fee = manager.excess_fee("Brewer", "DXBCA3781807232238", 26.42, 1.1);
		assertEquals(19.26, fee, "Equal!");
		assertNotEquals(19.12, fee, "Error!");
	}

	// Test method to verify finding a flight by its flight code.
	// Asserts that the returned flight's code is correct and does not match an incorrect code.
	@Test
	void testFindFlight() {
		Flight f = manager.findFlight("EK660");
		assertEquals("EK660", f.getFlight(), "Equal!");
		assertNotEquals("LH887", f.getFlight(), "Error!");
	}
	
}
