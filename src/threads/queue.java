package threads;

import models.passenger;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages the queue of passengers in a thread-safe manner without using Java's built-in thread-safe classes.
 * This class demonstrates manual synchronization to ensure that adding to and removing from the queue
 * are atomic operations and can safely be called from multiple threads.
 */
public class queue implements Runnable{
	private String queueType;
    private List<passenger> passengerQueue;
    private boolean isOpen;
    public queue(String queueType, List<passenger> queue) {
        this.passengerQueue = new LinkedList<>();
        this.queueType = queueType;
		this.passengerQueue = queue;
		this.isOpen = true;
    }

    /**
     * Synchronized method to add a passenger to the queue.
     *
     * @param passenger the passenger to be added to the queue
     */
    public synchronized void addPassengerToQueue(passenger passenger) {
        passengerQueue.addLast(passenger);
        // Notify any threads waiting for passengers to be added to the queue
        notify();
    }

    /**
     * Synchronized method to remove and return the first passenger in the queue.
     * If the queue is empty, the method will block until a passenger is added.
     *
     * @return the first passenger in the queue
     * @throws InterruptedException if any thread interrupted the current thread before or
     *                              while the current thread was waiting for a notification.
     */
    public synchronized passenger getNextPassenger() throws InterruptedException {
        while (passengerQueue.isEmpty()) {
            wait(); // Wait for a passenger to be added if the queue is empty
        }
        return passengerQueue.removeFirst();
    }

    public static void main(String[] args) {
    	String queueType = "1";
    	List<passenger> passengerQueue = new LinkedList<>();
    	passengerQueue.add(new passenger("1", queueType, queueType, queueType, 0, 0));
        queue queueManager = new queue(queueType, passengerQueue);

     // Producer thread to add passengers to the queue
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                passenger passenger = new passenger("Ref" + i, "Name" + i, "Flight" + i, "Yes", 20.5 * i, 10.5 * i);
                System.out.println("Create passenger: "+passenger);
                queueManager.addPassengerToQueue(passenger);
                try {
                    Thread.sleep(1000); // Simulating time delay between each passenger
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Consumer thread to process passengers from the queue
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    passenger passenger = queueManager.getNextPassenger();
                    System.out.println("Processed from queue: " + passenger);
                    Thread.sleep(1000); // Simulating processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
