package test;

import static org.junit.jupiter.api.Assertions.*;

import checkinSys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import myExceptions.*;

class ManagerTest {
	
	
	Manager manager;
	@BeforeEach
	void testPassenger() throws InvalidAttributeException {
		manager = new Manager();
	}
	
	@Test
	void testFindPassenger() throws InvalidBookRefException {
		Passenger p = manager.findPassenger("Brewer", "DXBCA3781807232238");
		assertEquals("Diane Brewer", p.getName(), "Equal!");
		assertNotEquals("Stephanie Gomez", p.getName(), "Error!");
	}

	@Test
	void testExcess_fee() throws InvalidBookRefException {
		double fee = manager.excess_fee("Brewer", "DXBCA3781807232238", 26.42, 1.1);
//		assertEquals(0, p.getName(), "Equal!");
		assertNotEquals(0, fee, "Error!");
	}

	@Test
	void testFindFlight() {
		Flight f = manager.findFlight("EK660");
		assertEquals("EK660", f.getFlight(), "Equal!");
		assertNotEquals("LH887", f.getFlight(), "Error!");
	}
	
}
