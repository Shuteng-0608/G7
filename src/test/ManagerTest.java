package test;

import static org.junit.jupiter.api.Assertions.*;

import checkinSys.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerTest {
	
	Manager manager = new Manager();
	
	@BeforeEach
	void testPassenger() throws InvalidAttributeException {
		manager.readFromFile("D:\\Users\\Desktop\\flight_details_data.csv", "D:\\Users\\Desktop\\passenger_data.csv");
	}
	
	@Test
	void testFindPassenger() {
		Passenger p = manager.findPassenger("Wright", "JFKCA3191811246621");
		assertEquals("Crystal Wright", p.getName(), "Equal!");
		assertNotEquals("Stephanie Gomez", p.getName(), "Error!");
	}

	@Test
	void testExcess_fee() {
		double fee = manager.excess_fee("Wright", "JFKCA3191811246621", 22, 3);
//		assertEquals(0, p.getName(), "Equal!");
		assertNotEquals(0, fee, "Error!");
	}

//	@Test
//	void testReport() {
//		fail("Not yet implemented");
//	}

	@Test
	void testFindFlight() {
		Flight f = manager.findFlight("BA524");
		assertEquals("BA524", f.getFlight(), "Equal!");
		assertNotEquals("LH887", f.getFlight(), "Error!");
	}

//	@Test
//	void testValidateFlightData() {
//		fail("Not yet implemented");
//	}

//	@Test
//	void testValidatePassengerData() {
//		fail("Not yet implemented");
//	}

}
