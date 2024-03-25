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
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Passenger p = so.randomSelect();
			if(p == null) {
				queueClose();
				continue;
			}
			if(queueType.equals("economy 1")) so.addQueue1(p);
			if(queueType.equals("economy 2")) so.addQueue2(p);
		}
    }

}
