import java.util.List;

public class CheckInDesk implements Runnable {
	private final String deskName;
	private SharedObject so;
	private boolean isOpen;

	public CheckInDesk(String deskName, SharedObject so) {
		this.deskName = deskName;
		this.so = so;
		this.isOpen = true;
	}

	public void closeDesk() {
		isOpen = false;
	}

	@Override
	public void run() {
		while (isOpen) {
			while(!so.getQueue1().isEmpty() || !so.getQueue2().isEmpty()) {
				Passenger p = so.getFromQueue();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				so.check_in(p);
			}
		}
	}

}
