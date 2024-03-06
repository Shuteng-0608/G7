package test;

import static org.junit.jupiter.api.Assertions.*;
import checkinSys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerListTest {
	
	
	PassengerList passengers = new PassengerList();
	
	@BeforeEach
	void testPassengerList() throws InvalidAttributeException {
		Manager manager = new Manager();
		manager.readFromFile("D:\\Users\\Desktop\\flight_details_data.csv", "D:\\Users\\Desktop\\passenger_data.csv");
		this.passengers = manager.getFlightList().getFlight(0).getList();
	}

	@Test
	void testFindByName() {
		Passenger p = this.passengers.findByName("Crystal Wright");
		assertEquals("Crystal Wright", p.getName(), "Equal!");
		assertNotEquals("John Mason", p.getName(), "Error!");
	}

	@Test
	void testGetByIdx() {
		Passenger p = this.passengers.getByIdx(0);
		assertEquals("Crystal Wright", p.getName(), "Equal!");
		assertNotEquals("John Mason", p.getName(), "Error!");
	}
	
	@Test
	void testFindByLastName() {
		int index = this.passengers.findByLastName("Wright", "JFKCA3191811246621");
		assertEquals(0, index, "Equal!");
		assertNotEquals(1, index, "Error!");
	}

	@Test
	void testGetNumberOfEntries() {
		int number = passengers.getNumberOfEntries();
		assertEquals(255, number, "Equal!");
		assertNotEquals(256, number, "Equal!");
	}

}
