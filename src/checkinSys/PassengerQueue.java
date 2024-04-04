package checkInSimulation;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Represents a queue for passengers awaiting processing in a simulation.
public class PassengerQueue implements Runnable {
	
	private final String queueType;// Type of the queue (e.g., "Business" or "Economy").
	private SharedObject so;// Shared object for coordinating between different parts of the simulation.
    private boolean isOpen;// Flag to indicate whether the queue is open for processing.
    private int timer;// Timer interval to simulate processing delay.
        // Constructor to initialize the queue.
	public PassengerQueue(String queueType, SharedObject so) {
		this.queueType = queueType;
		this.so = so;
		this.isOpen = true;// Queue starts in an open state.
		this.timer = 1000;// Default processing time set to 1000 milliseconds.

	}
	// Method to close the queue, stopping the processing of new passengers.
	public void queueClose() {
        isOpen = false;
    }
	// Method to check the open status of the queue.
	public boolean states() {
		return isOpen;
	}
	// Getter for the queue's type.
	public String getName() {
		return queueType;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	// The run method contains the logic to simulate passenger processing.
	@Override
    public void run() {
		while(true) {// Continuously process passengers until the queue is closed.
			if(!isOpen) continue;// Skip processing if the queue is closed.
			try {
				Thread.sleep(timer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Randomly select a passenger from the shared list to process.
			Passenger p = so.randomSelect();
			if(p == null) {// If no passengers are left, close the queue.
				queueClose();
				Logger.log(Logger.LogLevel.INFO, "Queue is closed");
				continue;
			}
			// Add the passenger to the appropriate queue based on their cabin class.
			if(p.getCabin().equals("Business")) so.addQueue1(p);
			if(p.getCabin().equals("Economy")) so.addQueue2(p);
		}
    }

}
