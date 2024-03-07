package test;

import static org.junit.jupiter.api.Assertions.*;

import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightListTest {
	
	private FlightList flightList = new FlightList();

	// This method sets up the environment before each test.
	// It initializes 'flightList' with the data from a 'Manager' object.
	@BeforeEach
	void testFlightList() throws InvalidAttributeException, InvalidBookRefException {
		Manager manager = new Manager();
		this.flightList = manager.getFlightList();
	}
//flight(flight_code, destination, carrier, capacity, weight, volume)
	// Test method to verify finding a flight by its code.
	// It asserts that the flight code matches the expected value and does not match an incorrect value.
	@Test
	void testFindByCode() {
		Flight f = this.flightList.findByCode("CA378");
		assertEquals("CA378", f.getFlight(), "Equal!");
		assertNotEquals("KL653", f.getFlight(), "Error!");
	}

	// Test method to verify the number of entries in the flight list.
	// It checks if the number of entries is exactly as expected and not any different number.
	@Test
	void testGetNumberOfEntries() {
		int number = flightList.getNumberOfEntries();
		assertEquals(100, number, "Equal!");
		assertNotEquals(101, number, "Equal!");
	}


	// Test method to check the behavior when a flight code is not found in the list.
	// It asserts that the method should return null for a non-existing flight code.
	@Test
    void testFindByCodeNotFound() {
        Flight f = flightList.findByCode("OO999");
        assertNull(f, "Should return null for non-existing code");
    }

	// Test method to verify the behavior when an invalid index is used to get a flight.
	// It asserts that an IndexOutOfBoundsException is thrown for an invalid index.
	@Test
    void testInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            flightList.getFlight(-1); // test invalid index
        });
    }

}
