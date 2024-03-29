package checkInSimulation;
import java.util.List;

public class CheckInDesk implements Runnable {
	private final String deskName;
	private SharedObject so;
	private boolean isOpen;
	private Passenger client;
	private int timer;

	public CheckInDesk(String deskName, SharedObject so) {
		this.deskName = deskName;
		this.so = so;
		this.isOpen = true;
		this.timer = 1000;
	}

	public void closeDesk() {
		isOpen = false;
	}
	
	public Passenger getClient() {
		return client;
	}
	
	public String getDeskName() {
		return deskName;
	}
	
	public void setTimer(int timer) {
		this.timer = 3 * timer;
	}
	
	public boolean states() {
		return isOpen;
	}

	@Override
	public void run() {
		while (isOpen) {
			try {
				Thread.sleep(timer);
			} catch (InterruptedException e) {
			}
			
			if(so.getQueue1().isEmpty() && so.getQueue2().isEmpty() && so.getAllPassenger().getNumberOfEntries() == 0) {
				closeDesk();
				System.out.println("Desk " + getDeskName() + " closed");
			} 
				
			while(!so.getQueue1().isEmpty() || !so.getQueue2().isEmpty()) {
				client = so.getFromQueue();
				if (client == null) continue;
				System.out.println("Desk " + getDeskName() + " get passenger : " + getClient().getName());
				so.check_in(client);
			}
		}
	}

}