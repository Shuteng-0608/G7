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
	
	
	public Controller(AirportGUI airport, SharedObject so) {
		airport.setSharedObject(so);
		startSpeedSlider(airport);
		startCheckInDeskBox(airport);
		new Thread(airport).start();
	}
	
	
	public void startCheckInDeskBox(AirportGUI airport) {
		for (int i = 1; i <= 5; i++) {
            final int deskNumber = i; // 使用final变量以便在匿名类中使用
           

			airport.getJBoxs()[i].addActionListener(new ActionListener() {
		          @Override
		          public void actionPerformed(ActionEvent e) {
		              JCheckBox cb = (JCheckBox) e.getSource();
//		              System.out.println(airport.getDeskNum());
		              if (cb.isSelected()) {
		            	  airport.getJDeskButs()[deskNumber].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 未勾选时边框变回黑色
		              	
			              if (airport.getDeskNum() == 1 || airport.getDeskNum() == 2 || airport.getDeskNum() == 3) {
			            	  cb.setText("Business Desk " + airport.getDeskNum() + " Close"); // 更新复选框文本为初始状态
			              } else {
			            	  cb.setText("Economy Desk " + airport.getDeskNum() + " Close"); // 更新复选框文本为初始状态
			              }
		              	
		                  
			              for (CheckInDesk desk_ : airport.getDesk()) {
			            	  if (desk_.getDeskName().equals("Desk " + airport.getDeskNum())) {
			            		  System.out.println("Desk " + airport.getDeskNum() + " Close");
			            		  desk_.closeDesk();
			            	  }
			              }
		              } else {

		            	  airport.getJDeskButs()[deskNumber].setBorder(BorderFactory.createLineBorder(Color.GREEN)); // 勾选时边框变绿
		                  cb.setText("Desk " + airport.getDeskNum() + " Open"); // 更新复选框文本为Open
		              }
		          }
		      });
		}
		
		
	}
	
	
	public void startSpeedSlider(AirportGUI airport) {
		airport.getSpeedSlider().addChangeListener(e -> {
			JSlider source = (JSlider)e.getSource();
			if (!source.getValueIsAdjusting()) {
				airport.setTimerSpeed(1000000/source.getValue());
         
				// Adjust all timers according to the new speed
				airport.adjustTimerSpeeds();
			}
		});
	}
	
	
	
	
	public static void main(String[] args) {
		
		SharedObject so = new SharedObject();
		AirportGUI airport = new AirportGUI(so);
		Controller c = new Controller(airport, so);
	}

}
