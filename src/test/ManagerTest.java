package test;

import static org.junit.jupiter.api.Assertions.*;

import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerTest {
	
	private Manager manager;
	
	@BeforeEach
	void setUp() throws InvalidAttributeException, InvalidBookRefException {
		this.manager = new Manager();
	}

	@Test
	void testFindPassenger() {
		Passenger p = this.manager.findPassenger("Brewer", "DXBCA3781807232238");
		assertEquals("Diane Brewer", p.getName(), "Equal!");
		assertNotEquals("Stephanie Gomez", p.getName(), "Error!");
	}

	@Test
	void testExcess_fee() {
		double fee = this.manager.excess_fee("Brewer", "DXBCA3781807232238", 26.42, 1.1);
		assertEquals(19.26, fee, "Equal!");
		assertNotEquals(19.12, fee, "Error!");
	}

	@Test
	void testFindFlight() {
		Flight f = this.manager.findFlight("EK660");
		assertEquals("EK660", f.getFlight(), "Equal!");
		assertNotEquals("LH887", f.getFlight(), "Error!");
	}

	@Test
	void testCheck_rc() {
		boolean result = this.manager.check_rc("George Hall");
		assertEquals(true, result, "Equal!");
		assertNotEquals(false, result, "Error!");
	}

	@Test
	void testCheck_in() {
		boolean result = this.manager.check_in("Hale", "DXBCA3781807233286");
		assertEquals(true, result, "Equal!");
		assertNotEquals(false, result, "Error!");
		result = this.manager.check_in("Mullins", "DXBCA3781807237802");
		assertEquals(false, result, "Equal!");
		assertNotEquals(true, result, "Error!");
	}
}
