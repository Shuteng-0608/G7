package checkInSimulation;

import java.util.List;

public class PassengerQueue implements Runnable {
	
	private final String queueType;
    private final List<Passenger> queue;
    private final List<Passenger> list;
    private boolean isOpen;
    
	public PassengerQueue(String queueType, List<Passenger> queue, List<Passenger> list) {
		this.queueType = queueType;
		this.queue = queue;
		this.list = list;
		this.isOpen = true;
	}
	
	public void closeQueue() {
        isOpen = false;
    }
	
	@Override
    public void run() {
        while (isOpen) {
            if (!list.isEmpty()) {
                Passenger nextPassenger = list.remove(0);
                queue.add(nextPassenger);
                Logger.log(queueType + " adding passenger: " + nextPassenger.getName());
                // Simulate processing time
                try {
                    Thread.sleep(1000); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.log(queueType + " finished adding passenger: " + nextPassenger.getName());
            }
        }
    }

}
