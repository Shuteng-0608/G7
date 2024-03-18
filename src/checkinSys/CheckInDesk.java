package checkinSys;

import java.util.List;

public class CheckInDesk implements Runnable {
	private final String deskName;
    private final List<Passenger> queue;
    private boolean isOpen;

    public CheckInDesk(String deskName, List<Passenger> queue) {
        this.deskName = deskName;
        this.queue = queue;
        this.isOpen = true;
    }

    public void closeDesk() {
        isOpen = false;
    }

    @Override
    public void run() {
        while (isOpen) {
            if (!queue.isEmpty()) {
                Passenger nextPassenger = queue.remove(0);
                Logger.log(deskName + " processing passenger: " + nextPassenger.getName());
                // Simulate processing time
                try {
                    Thread.sleep(1000); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.log(deskName + " finished processing passenger: " + nextPassenger.getName());
            }
        }
    }

}
