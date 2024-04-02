package checkinSys;

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
            final int deskNumber = i; // ʹ��final�����Ա�����������ʹ��
           

			airport.getJBoxs()[i].addActionListener(new ActionListener() {
		          @Override
		          public void actionPerformed(ActionEvent e) {
		              JCheckBox cb = (JCheckBox) e.getSource();
//		              System.out.println(airport.getDeskNum());
		              if (cb.isSelected()) {
		            	  airport.getJDeskButs()[deskNumber].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // δ��ѡʱ�߿��غ�ɫ
		              	
			              if (airport.getDeskNum() == 1 || airport.getDeskNum() == 2 || airport.getDeskNum() == 3) {
			            	  cb.setText("Business Desk " + airport.getDeskNum() + " Close"); // ���¸�ѡ���ı�Ϊ��ʼ״̬
		            		  Logger.log("Business Desk " + airport.getDeskNum() + " Close");

			              } else {
			            	  cb.setText("Economy Desk " + airport.getDeskNum() + " Close"); // ���¸�ѡ���ı�Ϊ��ʼ״̬
		            		  Logger.log("Economy Desk " + airport.getDeskNum() + " Close");

			              }
		              	
		                  
			              for (CheckInDesk desk_ : airport.getDesk()) {
			            	  if (desk_.getDeskName().equals("Desk " + airport.getDeskNum())) {
			            		  System.out.println("Desk " + airport.getDeskNum() + " Close");
			            		  Logger.log("Desk " + airport.getDeskNum() + " Close");
			            		  desk_.closeDesk();
			            	  }
			              }
		              } else {

		            	  airport.getJDeskButs()[deskNumber].setBorder(BorderFactory.createLineBorder(Color.GREEN)); // ��ѡʱ�߿����
		                  cb.setText("Desk " + airport.getDeskNum() + " Open"); // ���¸�ѡ���ı�ΪOpen
	            		  Logger.log("Desk " + airport.getDeskNum() + " Open");
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
