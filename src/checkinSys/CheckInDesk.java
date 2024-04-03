package checkinSys;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CheckInDesk implements Runnable {
	private final String deskName;
	private final String deskType;
	private SharedObject so;
	private boolean isOpen;
	private Passenger client;
	private int timer;
	private JTextArea deskText;
	private JCheckBox deskButton;
	private JPanel buttonPanel;

	public CheckInDesk(String deskName, String deskType, SharedObject so, JTextArea deskText, JCheckBox deskButton, JPanel buttonPanel) {
		this.deskName = deskName;
		this.deskType = deskType;
		this.so = so;
		this.isOpen = true;
		this.timer = 1000;
		this.deskText = deskText;
		this.deskButton = deskButton;
		this.buttonPanel = buttonPanel;
	}

	public void closeDesk() {
		isOpen = false;
	}
	
	public void restartDesk() {
		isOpen = true;
	}
	
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
	
	

	@Override
	public void run() {
		while (true) {
//			System.out.println(1111);
			try {
				Thread.sleep(timer);
			} catch (InterruptedException e) {
			}
			if(!isOpen) continue;
			if(so.getQueue1().isEmpty() && so.getQueue2().isEmpty() && so.getAllPassenger().getNumberOfEntries() == 0) {
				closeDesk();
				client = null;
				Logger.log("Desk " + getDeskName() + " closed");
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
				Logger.log("Desk " + getDeskName() + " get passenger : " + getClient().getName());
				if (client.getFlight().equals("CA378")) {
					so.addF1B(client);
					so.addF1P();
				}
				if (client.getFlight().equals("EK216")) {
					so.addF2B(client);
					so.addF2P();
				}
				if (client.getFlight().equals("EK660")) {
					so.addF3B(client);
					so.addF3P();
				}
				so.check_in(client);
				Logger.log("Desk " + getDeskName() + " check in passenger : " + getClient().getName());	
			}
		}
	}

}
