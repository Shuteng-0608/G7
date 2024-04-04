package checkInSimulation;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
// Represents a check-in desk at the airport, responsible for handling passengers.
public class CheckInDesk implements Runnable {
	private final String deskName; // The name of the desk.
	private final String deskType; // The type of desk (e.g., Economy or Business).
	private SharedObject so; // A shared object for coordinating between desks.
	private boolean isOpen; // Indicates whether the desk is open.
	private Passenger client; // The current passenger being serviced.
	private int timer; // Timer for the thread to control processing speed.
	private JTextArea deskText; // Text area for displaying desk status.
	private JCheckBox deskButton; // Checkbox to toggle the desk open or closed.
	private JPanel buttonPanel; // Panel containing the desk's button.
	// Constructor to initialize a CheckInDesk object with the provided parameters.
	public CheckInDesk(String deskName, String deskType, SharedObject so, JTextArea deskText, JCheckBox deskButton, JPanel buttonPanel) {
		this.deskName = deskName;
		this.deskType = deskType;
		this.so = so;
		this.isOpen = true;
		this.timer = 1000;// Default timer speed.
		this.deskText = deskText;
		this.deskButton = deskButton;
		this.buttonPanel = buttonPanel;
	}
 	// Method to close the desk, setting its state to not open.
	public void closeDesk() {
		isOpen = false;
	}
	// Method to reopen the desk, setting its state to open.
	public void restartDesk() {
		isOpen = true;
	}
	// Getters and setters
	public Passenger getClient() {
		return client;
	}
	
	public String getDeskType() {
		return deskType;
	}
	
	public String getDeskName() {
		return deskName;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public JTextArea getText() {
		return deskText;
	}
	
	public JCheckBox getButton() {
		return deskButton;
	}
	
	public JPanel getButtonPanel() {
		return buttonPanel;
	}
	
	public boolean states() {
		return isOpen;
	}
	
	
 	// The run method that contains the logic executed by the thread associated with this desk.
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(timer);// Pause for the specified timer duration.
			} catch (InterruptedException e) {
			}
			if(!isOpen) {
				// If the desk is closed, skip the rest of the loop and check again.
				System.out.println(" ");
				continue;
			}
			if(so.getQueue1().isEmpty() && so.getQueue2().isEmpty() && so.getAllPassenger().getNumberOfEntries() == 0) {
				closeDesk();
				client = null;
				Logger.log(Logger.LogLevel.INFO, getDeskName() + " closed");
			} 
				
			while(!so.getQueue1().isEmpty() || !so.getQueue2().isEmpty()) {
				if (deskName.equals("Desk 1") || deskName.equals("Desk 2") || deskName.equals("Desk 3")) {
					client = so.getFromQueue1();
				} else {
					client = so.getFromQueue2();
				}
				if (client == null) continue;
				if (!so.isF1() && client.getFlight().equals("CA378")) continue;
				if (!so.isF2() && client.getFlight().equals("EK216")) continue;
				if (!so.isF3() && client.getFlight().equals("EK660")) continue;
				
//				System.out.println("Desk " + getDeskName() + " get passenger : " + getClient().getName());
				Logger.log(Logger.LogLevel.INFO, getDeskName() + " get passenger : " + getClient().getName());
				if (client.getFlight().equals("CA378")) {
//					System.out.println(deskName);
					so.addF1B(client);
					so.addF1P();
				}
				if (client.getFlight().equals("EK216")) {
//					System.out.println(deskName);

					so.addF2B(client);
					so.addF2P();
				}
				if (client.getFlight().equals("EK660")) {
//					System.out.println(deskName);

					so.addF3B(client);
					so.addF3P();
				}
				so.check_in(client);
				Logger.log(Logger.LogLevel.INFO, getDeskName() + " check in passenger : " + getClient().getName());	
			}
			
		}
	}

}
