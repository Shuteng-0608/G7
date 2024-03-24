package test;

import static org.junit.Assert.assertTrue;

import checkinSys.*;
import myExceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckInDeskTest {
    private SharedObject sharedObject;
    private CheckInDesk checkInDesk;
    private Thread checkInDeskThread;

    @BeforeEach
    void setUp() throws InvalidAttributeException, InvalidBookRefException{
        sharedObject = new SharedObject(); // Initialize shared object
        checkInDesk = new CheckInDesk("Desk 1", sharedObject); // Initialize CheckInDesk
        checkInDeskThread = new Thread(checkInDesk); // Create a thread to run CheckInDesk
    }

    @Test
    public void testCheckInProcess() {
        // Assuming passenger information and flight information are already loaded into sharedObject

        // Create a few passenger instances and add them to the queue
        Passenger passenger1 = new Passenger("DXBCA3781807232238", "Diane Brewer", "CA378", "180723", "No", 26.42, 1.1);
        Passenger passenger2 = new Passenger("DXBCA3781807236379", "George Hall", "CA378", "180723", "Yes", 31.98, 1.85);
        sharedObject.addQueue1(passenger1);
        sharedObject.addQueue2(passenger2);

        // Start the CheckInDesk thread
        checkInDeskThread.start();

        try {
            Thread.sleep(100); // Wait enough time for CheckInDesk to process passengers
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if passengers have been processed (i.e., their check_in status should be set to "Yes")
        assertTrue("Passenger1 should have been checked in", passenger1.getCheckin().equals("Yes"));
        assertTrue("Passenger2 should have been checked in", passenger2.getCheckin().equals("Yes"));

        // Close CheckInDesk to end thread
        checkInDesk.closeDesk();
        try {
            checkInDeskThread.join(); // Ensure the thread ends safely
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}