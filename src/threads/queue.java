package threads;


import models.passenger;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The queue class is responsible for managing the queue of passengers in the airport.
 */
public class queue implements Runnable {
    // Thread for the queue management
    private Thread queueThread;
    private boolean isRunning;
    private Queue<passenger> passengerQueue = null;
    private int queueSize;

    // Constructor
    public queue(int queueSize) {
        this.passengerQueue = new LinkedList<>();
        this.isRunning = true;
        this.queueSize = queueSize;
        // Thread initialization
        queueThread = new Thread(this);
        queueThread.start();
    }

    // Method to stop the queue management
    public void stopQueueManager() {
        isRunning = false;
        queueThread.interrupt();
    }

    // Method to add a passenger to the queue
    public synchronized void addPassengerToQueue(passenger passenger) {
        passengerQueue.offer(passenger);
        // Notify any waiting threads that a new passenger has arrived
        notifyAll();
    }

    // Method to get the next passenger from the queue
    public synchronized passenger getNextPassenger() {
        return passengerQueue.poll();
    }

    // Runnable method to simulate the arrival of passengers
    @Override
    public void run() {
        while (isRunning) {
            try {
                // Simulate passenger arrival with some delay
                Thread.sleep(1);

                // Add a passenger to the queue
                // this should read from the .csv file
                passenger passenger = new passenger(null, null, null, null, 0, 0);
                addPassengerToQueue(passenger);
                // showing in the GUI

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }



}
