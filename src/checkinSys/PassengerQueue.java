package checkInSimulation;
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
		this.timer = 1000;
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
//				System.out.println("Queue " + queueType + " is closed");
				
				Logger.log(Logger.LogLevel.INFO, "Queue is closed");
				continue;
			}
			if(p.getCabin().equals("Business")) so.addQueue1(p);
			if(p.getCabin().equals("Economy")) so.addQueue2(p);
		}
    }

}
