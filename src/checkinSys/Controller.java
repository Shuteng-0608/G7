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

public class Controller {
	public Controller(AirportGUI airport) {
		startSpeedSlider(airport);
		for(CheckInDesk desk: airport.getDesks())
			startCheckInDeskBox(desk);
		new Thread(airport).start();
	}
	
	/**
	 * Open desk states (open or close) controller 
	 */
	public void startCheckInDeskBox(CheckInDesk desk) {
			desk.getButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JCheckBox jcb = (JCheckBox) e.getSource(); // 得到产生的事件
					if (jcb.isSelected()) {
						desk.getButtonPanel().setBorder(BorderFactory.createLineBorder(Color.RED)); 
						jcb.setText(desk.getDeskType()+ ' ' + desk.getDeskName() + " Close"); 
						desk.getText().setText("");
						Logger.log(Logger.LogLevel.INFO, desk.getDeskType()+ ' ' + desk.getDeskName() + " Close"); 
						desk.closeDesk();
					}
					else {
						desk.getButtonPanel().setBorder(BorderFactory.createLineBorder(Color.GREEN)); 
						jcb.setText(desk.getDeskType()+ ' ' + desk.getDeskName() + " Open"); 
						Logger.log(Logger.LogLevel.INFO, desk.getDeskType()+ ' ' + desk.getDeskName() + " Open"); 
						desk.restartDesk();
					}
				}
			});
		}
	
	/**
	 * Open simulation timer speed controller
	 */
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

}