package GUI;

import javax.swing.*;

import checkinSys.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class AirportGUI extends JFrame implements Runnable{
    private JPanel queuePanel;
    private JPanel deskPanel;
    private JPanel flightPanel;
    private JPanel flightInfo;
    private JPanel deskControlPanel;
    private JSlider speedSlider;
    JPanel deskBut;
    JTextArea queue1Text;
    JTextArea queue2Text;
    JTextArea desk1Text;
    JTextArea desk2Text;
    JTextArea desk3Text;
    JTextArea desk4Text;
    JTextArea desk5Text;
    JTextArea flight1Text;
    JTextArea flight2Text;
    JTextArea flight3Text;
	JCheckBox checkBox;
    

    
    private Random timeSetter = new Random();
    private final int deskWidth = 150; // ���ù�̨���ڹ̶����
    private final int deskHeight = 50; // ���ù�̨���ڹ̶��߶�
    private int queueCount1 = 0;  
    private int queueCount2 = 0;  
    private int queueNum = 2;
    private int deskNum = 5;
    final int[] timeLeft1 = {20 + timeSetter.nextInt(100)};
    final int[] timeLeft2 = {20 + timeSetter.nextInt(100)};
    final int[] timeLeft3 = {20 + timeSetter.nextInt(100)};
    
    
    private SharedObject so;
    
    private List<Thread> checkInThreads = new ArrayList<>();
    private List<CheckInDesk> Desk = new ArrayList<>();
    private List<PassengerQueue> passengerQueueDesk = new ArrayList<>();
    private List<JCheckBox> JBox = new ArrayList<>();
    private List<JPanel> JPDesk = new ArrayList<>();
    private List<JPanel> JDeskBut = new ArrayList<>();
    
    private JCheckBox[] JBoxs = new JCheckBox[6];
    private JPanel[] JDeskButs = new JPanel[6];
    
    
    private List<Thread> queueThreads = new ArrayList<>();
    private final int queuePanelHeight = 200; // ���ö������̶��߶�
    private List<Passenger> curPassengerList = new ArrayList<>();
    private boolean q1state = true;
    private boolean q2state = true;
    private int timerSpeed = 1000; // ��ʼ�ٶ�����Ϊ1000���룬��1��

    // �洢ÿ�������ʱ�������ã��Ա���Ը��ݻ������ı仯������ʱ���ٶ�
    private Map<Integer, Timer> flightTimers = new HashMap<>();
    
    
    
    
    
    public AirportGUI(SharedObject so) {
//    	so = new SharedObject();
    	this.so = so;
        setTitle("Airport Check-in System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // ���ò��ֹ��������
        
        // For passenger queue
        queuePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        queuePanel.setPreferredSize(new Dimension(getWidth(), queuePanelHeight)); // ���ù̶��߶�
        queuePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        queue1Text = new JTextArea("There are currently "+ queueCount1 +" people in queue1");
        queue1Text.setEditable(false);
        JScrollPane queue1Scroll = new JScrollPane(queue1Text);
        queuePanel.add(queue1Scroll);
        
        queue2Text = new JTextArea("There are currently "+ queueCount2 +" people in queue2");
        queue2Text.setEditable(false);
        JScrollPane queue2Scroll = new JScrollPane(queue2Text);
        queuePanel.add(queue2Scroll);
        
        
        // For check-in desk
        deskControlPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        deskControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // ��ӱ߾�
        
        deskPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        deskPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // ��ӱ߾�
        
        desk1Text = new JTextArea("desk1");
        desk1Text.setEditable(false);
        desk1Text.setPreferredSize(new Dimension(150, 100));
        desk1Text.setLineWrap(true);
        desk1Text.setWrapStyleWord(true);
        deskPanel.add(desk1Text);
        
        desk2Text = new JTextArea("desk2");
        desk2Text.setEditable(false);
        desk2Text.setPreferredSize(new Dimension(150, 100));
        desk2Text.setLineWrap(true);
        desk2Text.setWrapStyleWord(true);
        deskPanel.add(desk2Text);
        
        desk3Text = new JTextArea("desk3");
        desk3Text.setEditable(false);
        desk3Text.setPreferredSize(new Dimension(150, 100));
        desk3Text.setLineWrap(true);
        desk3Text.setWrapStyleWord(true);
        deskPanel.add(desk3Text);
        
        desk4Text = new JTextArea("desk4");
        desk4Text.setEditable(false);
        desk4Text.setPreferredSize(new Dimension(150, 100));
        desk4Text.setLineWrap(true);
        desk4Text.setWrapStyleWord(true);
        deskPanel.add(desk4Text);
        
        desk5Text = new JTextArea("desk5");
        desk5Text.setEditable(false);
        desk5Text.setPreferredSize(new Dimension(150, 100));
        desk5Text.setLineWrap(true);
        desk5Text.setWrapStyleWord(true);
        deskPanel.add(desk5Text);
        
        // for flight
        
        flightInfo = new JPanel(new GridLayout(1, 3, 10, 10));
        flightInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // ��ӱ߾�
        
        flight1Text = new JTextArea(" ");
        flight1Text.setEditable(false);
        flight1Text.setPreferredSize(new Dimension(150, 100));
        flight1Text.setLineWrap(true);
        flight1Text.setWrapStyleWord(true);
        flightInfo.add(flight1Text);
        
        flight2Text = new JTextArea(" "); 
        flight2Text.setEditable(false);
        flight2Text.setPreferredSize(new Dimension(150, 100));
        flight2Text.setLineWrap(true);
        flight2Text.setWrapStyleWord(true);
        flightInfo.add(flight2Text);
        
        flight3Text = new JTextArea(" ");
        flight3Text.setEditable(false);
        flight3Text.setPreferredSize(new Dimension(150, 100));
        flight3Text.setLineWrap(true);
        flight3Text.setWrapStyleWord(true);
        flightInfo.add(flight3Text);
        
        
        
        
        // Open queue and desk threads
        createQueue(); 
        createDesk();
        
        deskControlPanel = createControlDeskPanel();   // ������̨������
        flightPanel = createFlightPanel(); // ����������Ϣ��
        speedSlider = createSpeedSlider();
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(deskControlPanel, BorderLayout.CENTER);
        centerPanel.add(deskPanel, BorderLayout.SOUTH);
        

        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        bottomPanel.add(flightPanel, BorderLayout.NORTH);
        bottomPanel.add(flightInfo, BorderLayout.CENTER);
        bottomPanel.add(speedSlider, BorderLayout.SOUTH);
        
        

        add(queuePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack(); 
        setVisible(true);

    }
    
    
    public void setTimerSpeed(int timerspeed) {
    	this.timerSpeed = timerspeed;
    }
    
    public JSlider getSpeedSlider() {
    	return speedSlider;
    }
    
    public void setSharedObject(SharedObject so) {
    	this.so = so;
    }
    
    public JCheckBox getCheckBox() {
		return checkBox;
	}

	public JPanel getDeskBut() {
		return deskBut;
	}
	
	
    
    
    
    public List<JPanel> getJDeskBut() {
		return JDeskBut;
	}


	public void setJDeskBut(List<JPanel> jDeskBut) {
		JDeskBut = jDeskBut;
	}


	private void createQueue() {
    	for (int i = 1; i <= queueNum; i++) {
			PassengerQueue queue = new PassengerQueue("economy " + i, so);
			Thread thread = new Thread(queue);
			queueThreads.add(thread);
			passengerQueueDesk.add(queue);
	        thread.start();
		}
    }
    
    public JCheckBox[] getJBoxs() {
		return JBoxs;
	}


	public void setJBoxs(JCheckBox[] jBoxs) {
		JBoxs = jBoxs;
	}


	public JPanel[] getJDeskButs() {
		return JDeskButs;
	}


	public void setJDeskButs(JPanel[] jDeskButs) {
		JDeskButs = jDeskButs;
	}


	private void createDesk() {
    	for (int i = 1; i <= deskNum; i++) {
			CheckInDesk queue = new CheckInDesk("Desk " + i, so);
			Thread thread = new Thread(queue);
			checkInThreads.add(thread);
			Desk.add(queue);
	        thread.start();
		}
    }

    private JPanel createControlDeskPanel() {
        
        for (int i = 1; i <= 5; i++) {
            final int deskNumber = i; // ʹ��final�����Ա�����������ʹ��
            deskBut = new JPanel();
            JPDesk.add(deskBut);
            JDeskButs[i] = deskBut;
            deskBut.setPreferredSize(new Dimension(deskWidth, deskHeight)); // ���ù�̨�̶���С
            deskBut.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // ��ʼ�߿�����Ϊ��ɫ
//            JCheckBox checkBox;
            if (deskNumber == 1 || deskNumber == 2 || deskNumber == 3) {
            	checkBox = new JCheckBox("Business Desk " + deskNumber + " Open");
            	
        	} else {
        		checkBox = new JCheckBox("Economy Desk " + deskNumber + " Open");
        	}
            JBoxs[i] = checkBox;
            JBox.add(checkBox);
        	
//            checkBox.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    JCheckBox cb = (JCheckBox) e.getSource();
//                    
//                    if (cb.isSelected()) {
//                    	desk.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // δ��ѡʱ�߿��غ�ɫ
//                    	
//                    	if (deskNumber == 1 || deskNumber == 2 || deskNumber == 3) {
//                    		cb.setText("Business Desk " + deskNumber + " Close"); // ���¸�ѡ���ı�Ϊ��ʼ״̬
//                    	} else {
//                    		cb.setText("Economy Desk " + deskNumber + " Close"); // ���¸�ѡ���ı�Ϊ��ʼ״̬
//                    	}
//                    	
//                        
//                        for (CheckInDesk desk_ : Desk) {
//                        	if (desk_.getDeskName().equals("Desk " + deskNumber)) {
//                        		System.out.println("Desk " + deskNumber + " Close");
//                        		desk_.closeDesk();
//                        	}
//                        }
//                    } else {
//
//                        desk.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // ��ѡʱ�߿����
//                        cb.setText("Desk " + deskNumber + " Open"); // ���¸�ѡ���ı�ΪOpen
//                    }
//                }
//            });
            deskBut.add(checkBox);
            
            deskControlPanel.add(deskBut);
        }
        return deskControlPanel;
    }
    

    public int getDeskNum() {
		return deskNum;
	}


	public void setDeskNum(int deskNum) {
		this.deskNum = deskNum;
	}


	public List<CheckInDesk> getDesk() {
		return Desk;
	}


	public void setDesk(List<CheckInDesk> desk) {
		Desk = desk;
	}


	public List<JCheckBox> getJBox() {
		return JBox;
	}


	public void setJBox(List<JCheckBox> jBox) {
		JBox = jBox;
	}


	private JPanel createFlightPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(getWidth(), 70)); 

        for (int i = 1; i <= 3; i++) {
            final int flightNumber = i;
            JPanel flightPanel = new JPanel(new BorderLayout());
            flightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel countdownLabel = new JLabel("", SwingConstants.CENTER);
            flightPanel.add(countdownLabel, BorderLayout.CENTER);


            Timer timer = new Timer(1000, new ActionListener() { // ��ʱ��ÿ�봥��һ��
                public void actionPerformed(ActionEvent e) {
                    
                    
                    
                    if (flightNumber == 1) {
                    	timeLeft1[0]--;
                    	countdownLabel.setText("Flight " + "CA378" + " - " + timeLeft1[0]/60 + "M" + timeLeft1[0]%60 + "S");
                    	if (timeLeft1[0] <= 0) {
                            flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // �߿���
                            countdownLabel.setText("Flight " + flightNumber + " Closed"); // �ı�����ΪClosed
		            		Logger.log("Flight " + flightNumber + " Closed");
                            ((Timer)e.getSource()).stop(); // ֹͣ��ʱ��
                            so.closef1();
                            
                        }
                    }
                    if (flightNumber == 2) {
                    	timeLeft2[0]--;
                    	countdownLabel.setText("Flight " + "EK216" + " - " + timeLeft2[0]/60 + "M" + timeLeft2[0]%60 + "S");
                    	if (timeLeft2[0] <= 0) {
                            flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // �߿���
                            countdownLabel.setText("Flight " + flightNumber + " Closed"); // �ı�����ΪClosed
                            ((Timer)e.getSource()).stop(); // ֹͣ��ʱ��
                            so.closef2();
                        }
                    }
                    if (flightNumber == 3) {
                    	timeLeft3[0]--;
                    	countdownLabel.setText("Flight " + "EK660" + " - " + timeLeft3[0]/60 + "M" + timeLeft3[0]%60 + "S");
                    	if (timeLeft3[0] <= 0) {
                            flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // �߿���
                            countdownLabel.setText("Flight " + flightNumber + " Closed"); // �ı�����ΪClosed
                            ((Timer)e.getSource()).stop(); // ֹͣ��ʱ��
                            so.closef3();
                        }
                    }
                    // countdownLabel.setText("Flight " + flightNumber + " - " + timeLeft[0]/60 + "M" + timeLeft[0]%60 + "S");

                    
                    
                    
                }
            });
            timer.start(); // ������ʱ��
            flightTimers.put(flightNumber, timer); // Store the timer reference
            panel.add(flightPanel);
        }

        return panel;
    }
    
    
    
    
    private JSlider createSpeedSlider() {
        speedSlider = new JSlider(JSlider.HORIZONTAL, 250, 4000, timerSpeed);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setMinorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(250, new JLabel("X0.25"));
        labelTable.put(500, new JLabel("X0.5"));
        labelTable.put(1000, new JLabel("X1"));
        labelTable.put(2000, new JLabel("X2"));
        labelTable.put(3000, new JLabel("X3"));
        labelTable.put(4000, new JLabel("X4"));
        speedSlider.setLabelTable(labelTable); // ���û������ı�ǩ
//        speedSlider.addChangeListener(e -> {
//            JSlider source = (JSlider)e.getSource();
//            if (!source.getValueIsAdjusting()) {
//                timerSpeed = 1000000/source.getValue() ;
//               
//                // Adjust all timers according to the new speed
//                adjustTimerSpeeds();
//            }
//        });

        return speedSlider;
    }

    public void adjustTimerSpeeds() {
        for (Map.Entry<Integer, Timer> entry : flightTimers.entrySet()) {
            Timer timer = entry.getValue();
            if (timer != null) {
                timer.setDelay(timerSpeed);
                // Note: This does not restart the timer; it only adjusts the delay for future ticks.
            }
        }
    }
    
    @Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(timerSpeed);//Thread.Sleep()�������ڽ���ǰ�߳�����һ��ʱ�䣬��λΪ���롣����Ϊÿ1000��������һ���̡߳�
				
            	
				// for passenger queue 1
				queueCount1 = so.getQueue1().size();
	            Queue<Passenger> q1 = so.getQueue1();
	            if (q1state == false && q2state == false) {
	            	for (JPanel d: JPDesk) {
	            		d.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // ��ʼ�߿�����Ϊ��ɫ
	            	}
	            	for (JCheckBox b: JBox) {
	            		b.setText("Close");
	            		b.setSelected(true);
	            	}
	            	desk1Text.setText("All Passengers Have Checked-in");
	            	desk2Text.setText("All Passengers Have Checked-in");
	            	desk3Text.setText("All Passengers Have Checked-in");
	            	desk4Text.setText("All Passengers Have Checked-in");
	            	desk5Text.setText("All Passengers Have Checked-in");
	            	Logger.log("All Passengers Have Checked-in");
	            }
	            if (!so.isF1() && !so.isF2() && !so.isF3()) {
	            	for (JPanel d: JPDesk) {
	            		d.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // ��ʼ�߿�����Ϊ��ɫ
	            	}
	            	for (JCheckBox b: JBox) {
	            		b.setText("Close");
	            		b.setSelected(true);
	            	}
	            	desk1Text.setText("All Flights Have Departured");
	            	desk2Text.setText("All Flights Have Departured");
	            	desk3Text.setText("All Flights Have Departured");
	            	desk4Text.setText("All Flights Have Departured");
	            	desk5Text.setText("All Flights Have Departured");
	            	Logger.log("All Flights Have Departured");
	            	
	            }
	            for (PassengerQueue queue : passengerQueueDesk) {
	            	if (!queue.states() && queue.getName().equals("economy 1")) {
	            		q1state = false;
	            	}
	            	if (!queue.states() && queue.getName().equals("economy 2")) {
	            		q2state = false;
	            	}
	            	queue.setTimer(timerSpeed);
	            }
	            
	            if (!q1.isEmpty() && q1state == true) {
	            	queue1Text.setText("There are currently "+ queueCount1 +" people in VIP Membership queue");
		            for (Passenger p : q1) {
		            	queue1Text.append("\n" + p.getFlight() + "\t" +  p.getName()+ "          " + "\t" + p.getWeight() + "\t" + p.getVolume() );
//		            	queue1Text.paintImmediately(queue1Text.getBounds());
		            }
	            } else {
//	            	System.out.println("There are currently "+ 0 +" people in queue1");
	            	queue1Text.setText("There are currently "+ 0 +" people in VIP Membership queue ");
	            }
	            
	            // for passenger queue 2
	            queueCount2 = so.getQueue2().size();
	            Queue<Passenger> q2 = so.getQueue2();
	            if (!q2.isEmpty() && q2state == true) {
	            	queue2Text.setText("There are currently "+ queueCount2 +" people in queue2");
		            
		            for (Passenger p : q2) {
		            	
		            	queue2Text.append("\n" + p.getFlight() + "\t" +  p.getName() + "          " + "\t" + p.getWeight() + "\t" + p.getVolume() );
//		            	queue2Text.paintImmediately(queue2Text.getBounds());
		            }
	            } else {
//	            	System.out.println("There are currently "+ 0 +" people in queue2");
	            	queue2Text.setText("There are currently "+ 0 +" people in queue2");
	            }
	            
	            
	            
	            // for check-in desk
	            
	            for (CheckInDesk desk : Desk) {
	            	
	            	desk.setTimer(timerSpeed);
	            	Passenger p = desk.getClient();
	            	
	            	if (p == null) {
	            		
	            		continue;
	            	}
	            	if (so.isF1() || so.isF2() || so.isF3()) {
	            		
	            	
		            	if (desk.getDeskName().equals("Desk 1")) {
		            		desk1Text.setText("[VIP Menmbership] " + p.getName() + " is dropping off 1 bag of " + p.getWeight() + "kg");
		            		if (p.excess_fee() != 0) {
		            			desk1Text.append(" A buggage fee of " + p.excess_fee() + "$ is due");
	//	            			desk1Text.paintImmediately(desk1Text.getBounds());
		            		} else {
		            			desk1Text.append("\n" + "No baggage fee is due.");
	//	            			desk1Text.paintImmediately(desk1Text.getBounds());
		            		}
		            	}
						if (desk.getDeskName().equals("Desk 2") ) {
							desk2Text.setText("[VIP Menmbership] " + p.getName() + " is dropping off 1 bag of " + p.getWeight() + "kg");
		            		if (p.excess_fee() != 0) {
		            			desk2Text.append(" A buggage fee of " + p.excess_fee() + "$ is due");
	//	            			desk2Text.paintImmediately(desk2Text.getBounds());
		            		} else {
		            			desk2Text.append("\n" + "No baggage fee is due.");
	//	            			desk2Text.paintImmediately(desk2Text.getBounds());
		            		}
		            	}
						if (desk.getDeskName().equals("Desk 3")) {
							desk3Text.setText("[VIP Menmbership] " + p.getName() + " is dropping off 1 bag of " + p.getWeight() + "kg");
		            		if (p.excess_fee() != 0) {
		            			desk3Text.append(" A buggage fee of " + p.excess_fee() + "$ is due");
	//	            			desk3Text.paintImmediately(desk3Text.getBounds());
		            		} else {
		            			desk3Text.append("\n" + "No baggage fee is due.");
	//	            			desk3Text.paintImmediately(desk3Text.getBounds());
		            		}
						}
						if (desk.getDeskName().equals("Desk 4")) {
							desk4Text.setText(p.getName() + " is dropping off 1 bag of " + p.getWeight() + "kg");
		            		if (p.excess_fee() != 0) {
		            			desk4Text.append(" A buggage fee of " + p.excess_fee() + "$ is due");
	//	            			desk4Text.paintImmediately(desk4Text.getBounds());
		            		} else {
		            			desk4Text.append("\n" + "No baggage fee is due.");
	//	            			desk4Text.paintImmediately(desk4Text.getBounds());
		            		}
						}
						if (desk.getDeskName().equals("Desk 5")) {
							desk5Text.setText(p.getName() + " is dropping off 1 bag of " + p.getWeight() + "kg");
		            		if (p.excess_fee() != 0) {
		            			desk5Text.append(" A buggage fee of " + p.excess_fee() + "$ is due");
	//	            			desk5Text.paintImmediately(desk5Text.getBounds());
		            		} else {
		            			desk5Text.append("\n" + "No baggage fee is due.");
	//	            			desk5Text.paintImmediately(desk5Text.getBounds());
		            		}
						}
	            	}
					
	            	
	            }
	            flight1Text.setText(so.getPassengerNum1() + " checked in of " + "184");
        		flight1Text.append("\n" + " Hold is " + String.format("%.2f", 100 * (so.getBaggageNum1() / 6440)) + "%");
        		flight2Text.setText(so.getPassengerNum2() + " checked in of " + "266");
        		flight2Text.append("\n" + " Hold is " + String.format("%.2f", 100 * (so.getBaggageNum2() / 9310)) + "%");
        		flight3Text.setText(so.getPassengerNum3() + " checked in of " + "190");
        		flight3Text.append("\n" + " Hold is " + String.format("%.2f", 100 * (so.getBaggageNum3() / 6650)) + "%");
	            
	            
	            
	            
	            
	            
			} catch (InterruptedException e) {
				e.printStackTrace();//�׳��쳣
			}
		}
	}

//    public static void main(String[] args) {
//    	AirportGUI a = new AirportGUI();
//    	new Thread(a).start();//�����߳�
//    }
}
