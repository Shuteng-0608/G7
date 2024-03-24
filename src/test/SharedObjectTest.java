package test;

import static org.junit.jupiter.api.Assertions.*;
import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SharedObjectTest {
	
	private SharedObject sharedObject;
	
	@BeforeEach
	void setUp() throws InvalidAttributeException, InvalidBookRefException {
		this.sharedObject = new SharedObject();
	}

	@Test
	void testExcess_fee() {
		double fee = this.sharedObject.excess_fee("Brewer", "DXBCA3781807232238", 26.42, 1.1);
		assertEquals(19.26, fee, "Equal!");
		assertNotEquals(19.12, fee, "Error!");
	}

	@Test
	void testCheck_rc() {
		boolean result = this.sharedObject.check_rc("George Hall");
		assertEquals(true, result, "Equal!");
		assertNotEquals(false, result, "Error!");
	}

//	@Test
//	void testCheck_in() {
//        Passenger passenger1 = new Passenger("DXBCA3781807232238", "Diane Brewer", "CA378", "180723", "No", 26.42, 1.1);
//        Passenger passenger2 = new Passenger("DXBCA3781807236379", "George Hall", "CA378", "180723", "Yes", 31.98, 1.85);
//		boolean result = this.sharedObject.check_in(passenger1);
//		assertEquals(true, result, "Equal!");
//		assertNotEquals(false, result, "Error!");
//		result = this.sharedObject.check_in("Mullins", "DXBCA3781807237802");
//		assertEquals(false, result, "Equal!");
//		assertNotEquals(true, result, "Error!");
//	}

	@Test
	void testFindFlight() {
		Flight f = this.sharedObject.findFlight("EK660");
		assertEquals("EK660", f.getFlight(), "Equal!");
		assertNotEquals("LH887", f.getFlight(), "Error!");
	}

//	@Test
//	void testGetQueue1() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetQueue2() {
//		fail("Not yet implemented");
//	}

//	@Test
//	void testGetFromQueue() {
//		fail("Not yet implemented");
//	}

}
