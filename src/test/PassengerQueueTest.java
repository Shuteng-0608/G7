package test;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import checkinSys.PassengerQueue;
import checkinSys.SharedObject;

import static org.junit.Assert.*;

public class PassengerQueueTest {
    private SharedObject sharedObject;
    private Thread passengerQueueThread;
    private PassengerQueue passengerQueue;

    @Before
    public void setUp() {
        sharedObject = new SharedObject(); // Assume this object has been correctly initialized and configured
        // Assume there is already passenger data available in FlightList for selection
    }

    @Test
    public void testPassengerQueueEconomy1() throws InterruptedException {
        passengerQueue = new PassengerQueue("economy 1", sharedObject);
        passengerQueueThread = new Thread(passengerQueue);
        passengerQueueThread.start();

        // Give the thread some time to process tasks
        Thread.sleep(200); // Adjust this value as needed based on actual situations

        passengerQueue.queueClose();  // Notify the thread to close
        passengerQueueThread.join(); // Wait for the thread to end safely

        assertFalse("Queue 1 should not be empty after running", sharedObject.getQueue1().isEmpty());
        assertTrue("Queue 2 should be empty when only queue 1 is being used", sharedObject.getQueue2().isEmpty());
    }

    @Test
    public void testPassengerQueueEconomy2() throws InterruptedException {
        passengerQueue = new PassengerQueue("economy 2", sharedObject);
        passengerQueueThread = new Thread(passengerQueue);
        passengerQueueThread.start();

        // Give the thread some time to process tasks
        Thread.sleep(200); // Adjust this value as needed based on actual situations

        passengerQueue.queueClose(); // Notify the thread to close
        passengerQueueThread.join(); // Wait for the thread to end safely

        assertTrue("Queue 1 should be empty when only queue 2 is being used", sharedObject.getQueue1().isEmpty());
        assertFalse("Queue 2 should not be empty after running", sharedObject.getQueue2().isEmpty());
    }

    @After
    public void tearDown() {
    	// Clean up resources, such as closing threads, etc.
        if (passengerQueue != null) {
            passengerQueue.queueClose(); // Try to close, in case the test fails
        }
    }
}
