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
	
	 @Test
	    void testAddPassenger() {
	        Passenger newPassenger = new Passenger( "AMSKE6251401240299", "Jane Doe", "CA378", "yes", 23.2, 2.2);
	        int originalSize = passengers.getNumberOfEntries();
	        passengers.addPassenger(newPassenger);
	        assertEquals(originalSize + 1, passengers.getNumberOfEntries(), "Passenger should be added");
	    }

	    @Test
	    void testGetByIdxOutOfBounds() {
	        assertThrows(IndexOutOfBoundsException.class, () -> passengers.getByIdx(-1));
	        assertThrows(IndexOutOfBoundsException.class, () -> passengers.getByIdx(passengers.getNumberOfEntries()));
	    }


}
