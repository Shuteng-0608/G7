package checkInSimulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

import GUI.AirportGUI;
// Controller class for handling the interaction between the GUI and the simulation logic
public class Controller {
	// Constructor initializes the controller with the airport GUI and the shared object
	public Controller(AirportGUI airport, SharedObject so) {
		airport.setSharedObject(so);// Sets the shared object used for synchronization
		startSpeedSlider(airport);// Initializes the slider to adjust simulation speed
		new Thread(airport).start();// Starts the GUI in a separate thread
		// Initializes each check-in desk with a toggle functionality
		for(CheckInDesk desk: airport.getDesk())
			startCheckInDeskBox(desk);
	}

	public void startCheckInDeskBox(CheckInDesk desk) {
			desk.getButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JCheckBox jcb = (JCheckBox) e.getSource(); // µÃµ½²úÉúµÄÊÂ¼þ
					if (jcb.isSelected()) {
						desk.getButtonPanel().setBorder(BorderFactory.createLineBorder(Color.RED)); 
						jcb.setText(desk.getDeskType()+ ' ' + desk.getDeskName() + " Close"); 
						jcb.setForeground(Color.red);
						desk.getText().setText("");
						Logger.log(Logger.LogLevel.INFO, desk.getDeskType()+ ' ' + desk.getDeskName() + " Close"); 
						desk.closeDesk();
					}
					else {
						desk.getButtonPanel().setBorder(BorderFactory.createLineBorder(Color.GREEN)); 
						jcb.setText(desk.getDeskType()+ ' ' + desk.getDeskName() + " Open");
						jcb.setForeground(Color.black);
						Logger.log(Logger.LogLevel.INFO, desk.getDeskType()+ ' ' + desk.getDeskName() + " Open"); 
						desk.restartDesk();
					}
				}
			});
		}

	public void startSpeedSlider(AirportGUI airport) {
		airport.getSpeedSlider().addChangeListener(e -> {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				airport.setTimerSpeed(1000000 / source.getValue());

				// Adjust all timers according to the new speed
				airport.adjustTimerSpeeds();
			}
		});
	}

	public static void main(String[] args) {
		Logger logger = Logger.getInstance();

		SharedObject so = new SharedObject();
		AirportGUI airport = new AirportGUI(so);
		Controller c = new Controller(airport, so);
	}

}
