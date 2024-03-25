import java.util.List;

public class CheckInDesk implements Runnable {
	private final String deskName;
	private SharedObject so;
	private boolean isOpen;
	private Passenger client;

	public CheckInDesk(String deskName, SharedObject so) {
		this.deskName = deskName;
		this.so = so;
		this.isOpen = true;
	}

	public void closeDesk() {
		isOpen = false;
	}
	
	public Passenger getClient() {
		return client;
	}

	@Override
	public void run() {
		while (isOpen) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			if(so.getQueue1().isEmpty() && so.getQueue2().isEmpty() && so.getAllPassenger().getNumberOfEntries() == 0)
				closeDesk();
			while(!so.getQueue1().isEmpty() || !so.getQueue2().isEmpty()) {
				client = so.getFromQueue();
				so.check_in(client);
			}
		}
	}

}
