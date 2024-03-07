package test;

import static org.junit.jupiter.api.Assertions.*;
import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerListTest {
	
	PassengerList passengers = new PassengerList();
	
	@BeforeEach
	void testPassengerList() throws InvalidAttributeException {
		Manager manager = new Manager();
		this.passengers = manager.getFlightList().getFlight(0).getList();
	}

	@Test
	void testFindByName() {
		Passenger p = this.passengers.findByName("Diane Brewer");
		assertEquals("Diane Brewer", p.getName(), "Equal!");
		assertNotEquals("John Mason", p.getName(), "Error!");
	}

	@Test
	void testGetByIdx() {
		Passenger p = this.passengers.getByIdx(0);
		assertEquals("Diane Brewer", p.getName(), "Equal!");
		assertNotEquals("John Mason", p.getName(), "Error!");
	}
	
	@Test
	void testFindByLastName() {
		int index = this.passengers.findByLastName("Brewer", "DXBCA3781807232238");
		assertEquals(0, index, "Equal!");
		assertNotEquals(1, index, "Error!");
	}

	@Test
	void testGetNumberOfEntries() {
		int number = passengers.getNumberOfEntries();
		assertEquals(184, number, "Equal!");
		assertNotEquals(256, number, "Equal!");
	}

}
