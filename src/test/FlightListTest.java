package test;

import static org.junit.jupiter.api.Assertions.*;

import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightListTest {
	
	private FlightList flightList = new FlightList();	

	@BeforeEach
	void testFlightList() throws InvalidAttributeException, InvalidBookRefException {
		Manager manager = new Manager();
		this.flightList = manager.getFlightList();
	}
//flight(flight_code, destination, carrier, capacity, weight, volume)
	@Test
	void testFindByCode() {
		Flight f = this.flightList.findByCode("CA378");
		assertEquals("CA378", f.getFlight(), "Equal!");
		assertNotEquals("KL653", f.getFlight(), "Error!");
	}
	
	@Test
	void testGetNumberOfEntries() {
		int number = flightList.getNumberOfEntries();
		assertEquals(100, number, "Equal!");
		assertNotEquals(101, number, "Equal!");
	}


    @Test
    void testFindByCodeNotFound() {
        Flight f = flightList.findByCode("OO999");
        assertNull(f, "Should return null for non-existing code");
    }

    @Test
    void testInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            flightList.getFlight(-1); // 测试无效索引
        });
    }

}
