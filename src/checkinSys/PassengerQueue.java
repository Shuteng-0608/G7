package checkinSys;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PassengerQueue implements Runnable {
	
	private final String queueType;
	private SharedObject so;
    private boolean isOpen;
    private int timer;
    
	public PassengerQueue(String queueType, SharedObject so) {
		this.queueType = queueType;
		this.so = so;
		this.isOpen = true;
	}
	
	public void queueClose() {
        isOpen = false;
    }
	
	public boolean states() {
		return isOpen;
	}
	
	public String getName() {
		return queueType;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	
	@Override
    public void run() {
		while(true) {
			if(!isOpen) continue;
			try {
				Thread.sleep(timer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Passenger p = so.randomSelect();
			if(p == null) {
				queueClose();
				System.out.println("Queue " + queueType + " is closed");
				Logger.log("Queue " + queueType + " is closed");
				continue;
			}
			if(queueType.equals("economy 1")) so.addQueue1(p);
			if(queueType.equals("economy 2")) so.addQueue2(p);
		}
    }

}
