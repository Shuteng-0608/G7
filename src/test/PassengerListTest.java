package test;

import static org.junit.jupiter.api.Assertions.*;
import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerListTest {
	
	
	PassengerList passengers = new PassengerList();

	// This method is executed before each test.
	// It sets up a PassengerList object for the test cases by fetching it from a flight managed by a Manager object.
	@BeforeEach
	void testPassengerList() throws InvalidAttributeException {
		Manager manager = new Manager();
		this.passengers = manager.getFlightList().getFlight(0).getList();
	}

	// Test method to verify finding a passenger by their full name.
	// Asserts that the returned passenger's name is correct and does not match an incorrect name.
	@Test
	void testFindByName() {
		Passenger p = this.passengers.findByName("Diane Brewer");
		assertEquals("Diane Brewer", p.getName(), "Equal!");
		assertNotEquals("John Mason", p.getName(), "Error!");
	}

	// Test method to verify fetching a passenger by their index in the list.
	// Asserts that the passenger's name at the given index is correct and does not match an incorrect name.
	@Test
	void testGetByIdx() {
		Passenger p = this.passengers.getByIdx(0);
		assertEquals("Diane Brewer", p.getName(), "Equal!");
		assertNotEquals("John Mason", p.getName(), "Error!");
	}

	// Test method to verify finding a passenger's index by their last name and booking reference.
	// Asserts that the returned index is correct and does not match an incorrect index.
	@Test
	void testFindByLastName() {
		int index = this.passengers.findByLastName("Brewer", "DXBCA3781807232238");
		assertEquals(0, index, "Equal!");
		assertNotEquals(1, index, "Error!");
	}

	// Test method to verify getting the number of entries in the passenger list.
	// Asserts that the number is as expected and not any other number.
	@Test
	void testGetNumberOfEntries() {
		int number = passengers.getNumberOfEntries();
		assertEquals(184, number, "Equal!");
		assertNotEquals(256, number, "Equal!");
	}

	// Test method to verify adding a new passenger to the list.
	// Asserts that the size of the list increases by one after adding the passenger.
	@Test
	    void testAddPassenger() {
	        Passenger newPassenger = new Passenger( "AMSKE6251401240299", "Jane Doe", "CA378", "yes", 23.2, 2.2);
	        int originalSize = passengers.getNumberOfEntries();
	        passengers.addPassenger(newPassenger);
	        assertEquals(originalSize + 1, passengers.getNumberOfEntries(), "Passenger should be added");
	    }

	// Test method to verify the behavior when accessing passengers at out-of-bounds indexes.
	// Asserts that an IndexOutOfBoundsException is thrown for invalid indexes.
	@Test
	    void testGetByIdxOutOfBounds() {
	        assertThrows(IndexOutOfBoundsException.class, () -> passengers.getByIdx(-1));
	        assertThrows(IndexOutOfBoundsException.class, () -> passengers.getByIdx(passengers.getNumberOfEntries()));
	    }


}
