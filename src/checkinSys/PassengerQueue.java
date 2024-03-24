package checkinSys;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PassengerQueue implements Runnable {
	
	private final String queueType;
	private SharedObject so;
    private boolean isOpen;
    
	public PassengerQueue(String queueType, SharedObject so) {
		this.queueType = queueType;
		this.so = so;
		this.isOpen = true;
	}
	
	public void queueClose() {
        isOpen = false;
    }
	
	@Override
    public void run() {
		while(isOpen) {
			Passenger p = so.getFlightList().randomSelect();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(queueType.equals("economy 1")) so.addQueue1(p);
			if(queueType.equals("economy 2")) so.addQueue2(p);
		}
    }

}
