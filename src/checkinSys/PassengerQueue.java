package checkInSimulation;

import java.util.List;

public class PassengerQueue implements Runnable {
	
	private final String queueType;
    private final List<Passenger> queue;
    private boolean isOpen;
    
	public PassengerQueue(String queueType, List<Passenger> queue) {
		this.queueType = queueType;
		this.queue = queue;
		this.isOpen = true;
	}
	
	public void closeQueue() {
        isOpen = false;
    }
	
	@Override
    public void run() {
        while (isOpen) {
            if (!queue.isEmpty()) {
                Passenger nextPassenger = queue.remove(0);
                Logger.log(queueType + " processing passenger: " + nextPassenger.getName());
                // Simulate processing time
                try {
                    Thread.sleep(1000); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.log(queueType + " finished processing passenger: " + nextPassenger.getName());
            }
        }
    }

}
