package test;

import static org.junit.jupiter.api.Assertions.*;

import flightSystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightListTest {
	
	private FlightList flightList = new FlightList();	

	@BeforeEach
	void testFlightList() throws InvalidAttributeException {
		Manager manager = new Manager();
		manager.readFromFile("D:\\Users\\Desktop\\flight_details_data.csv", "D:\\Users\\Desktop\\passenger_data.csv");
		this.flightList = manager.getFlightList();
	}

	@Test
	void testFindByCode() {
		Flight f = this.flightList.findByCode("CA319");
		assertEquals("CA319", f.getFlight(), "Equal!");
		assertNotEquals("KL653", f.getFlight(), "Error!");
	}
	
	@Test
	void testGetNumberOfEntries() {
		int number = flightList.getNumberOfEntries();
		assertEquals(100, number, "Equal!");
		assertNotEquals(101, number, "Equal!");
	}
	
}
