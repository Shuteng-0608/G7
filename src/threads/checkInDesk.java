package threads;

import models.passenger;
import controllers.flightManager;
import utils.logSingleton;

/**
 * The CheckInDesk class simulates a check-in desk at an airport.
 */
public class checkInDesk implements Runnable {
    private flightManager flightManager;
    private queue queue;
    private boolean isOpen;
    private String deskId;

    // Constructor
    public checkInDesk(String deskId, flightManager flightManager, queue queue) {
        this.deskId = deskId;
        this.flightManager = flightManager;
        this.queue = queue;
        this.isOpen = true;
    }

    // Method to close the check-in desk
    public void closeDesk() {
        this.isOpen = false;
    }

    // The run method will process passengers from the queue
    @Override
    public void run() {
        while (isOpen) {

        }

    }

}