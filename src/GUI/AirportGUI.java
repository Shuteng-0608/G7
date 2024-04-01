package GUI;

import javax.swing.*;

import checkInSimulation.*;

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
    

    
    private Random timeSetter = new Random();
    private final int deskWidth = 150; // 设置柜台窗口固定宽度
    private final int deskHeight = 50; // 设置柜台窗口固定高度
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
    
    
    private List<Thread> queueThreads = new ArrayList<>();
    private final int queuePanelHeight = 200; // 设置队列面板固定高度
    private List<Passenger> curPassengerList = new ArrayList<>();
    private boolean q1state = true;
    private boolean q2state = true;
    private int timerSpeed = 100; // 初始速度设置为1000毫秒，即1秒

    // 存储每个航班计时器的引用，以便可以根据滑动条的变化调整计时器速度
    private Map<Integer, Timer> flightTimers = new HashMap<>();
    public AirportGUI() {
    	so = new SharedObject();
        setTitle("Airport Check-in System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // 设置布局管理器间隔
        
        // For passenger queue
        queuePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        queuePanel.setPreferredSize(new Dimension(getWidth(), queuePanelHeight)); // 设置固定高度
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
        deskControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加边距
        
        deskPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        deskPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加边距
        
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
        flightInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加边距
        
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
        
        deskControlPanel = createControlDeskPanel();   // 创建柜台操作区
        flightPanel = createFlightPanel(); // 创建航班信息区
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
                
    private void createQueue() {
    	for (int i = 1; i <= queueNum; i++) {
			PassengerQueue queue = new PassengerQueue("economy " + i, so);
			Thread thread = new Thread(queue);
			queueThreads.add(thread);
			passengerQueueDesk.add(queue);
	        thread.start();
		}
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
            final int deskNumber = i; // 使用final变量以便在匿名类中使用
            JPanel desk = new JPanel();
            JPDesk.add(desk);
            desk.setPreferredSize(new Dimension(deskWidth, deskHeight)); // 设置柜台固定大小
            desk.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // 初始边框设置为绿色
            JCheckBox checkBox;
            if (deskNumber == 1 || deskNumber == 2 || deskNumber == 3) {
            	checkBox = new JCheckBox("Business Desk " + deskNumber + " Open");
        	} else {
        		checkBox = new JCheckBox("Economy Desk " + deskNumber + " Open");
        	}
            
            JBox.add(checkBox);
        	
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    
                    if (cb.isSelected()) {
                    	desk.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 未勾选时边框变回黑色
                    	
                    	if (deskNumber == 1 || deskNumber == 2 || deskNumber == 3) {
                    		cb.setText("Business Desk " + deskNumber + " Close"); // 更新复选框文本为初始状态
                    	} else {
                    		cb.setText("Economy Desk " + deskNumber + " Close"); // 更新复选框文本为初始状态
                    	}
                    	
                        
                        for (CheckInDesk desk_ : Desk) {
                        	if (desk_.getDeskName().equals("Desk " + deskNumber)) {
                        		System.out.println("Desk " + deskNumber + " Close");
                        		desk_.closeDesk();
                        	}
                        }
                    } else {

                        desk.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // 勾选时边框变绿
                        cb.setText("Desk " + deskNumber + " Open"); // 更新复选框文本为Open
                    }
                }
            });
            desk.add(checkBox);
            
            deskControlPanel.add(desk);
        }
        return deskControlPanel;
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


            Timer timer = new Timer(1000, new ActionListener() { // 计时器每秒触发一次
                public void actionPerformed(ActionEvent e) {
                    
                    
                    
                    if (flightNumber == 1) {
                    	timeLeft1[0]--;
                    	countdownLabel.setText("Flight " + "CA378" + " - " + timeLeft1[0]/60 + "M" + timeLeft1[0]%60 + "S");
                    	if (timeLeft1[0] <= 0) {
                            flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // 边框变红
                            countdownLabel.setText("Flight " + flightNumber + " Closed"); // 文本更新为Closed
                            ((Timer)e.getSource()).stop(); // 停止计时器
                            so.closef1();
                            
                        }
                    }
                    if (flightNumber == 2) {
                    	timeLeft2[0]--;
                    	countdownLabel.setText("Flight " + "EK216" + " - " + timeLeft2[0]/60 + "M" + timeLeft2[0]%60 + "S");
                    	if (timeLeft2[0] <= 0) {
                            flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // 边框变红
                            countdownLabel.setText("Flight " + flightNumber + " Closed"); // 文本更新为Closed
                            ((Timer)e.getSource()).stop(); // 停止计时器
                            so.closef2();
                        }
                    }
                    if (flightNumber == 3) {
                    	timeLeft3[0]--;
                    	countdownLabel.setText("Flight " + "EK660" + " - " + timeLeft3[0]/60 + "M" + timeLeft3[0]%60 + "S");
                    	if (timeLeft3[0] <= 0) {
                            flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // 边框变红
                            countdownLabel.setText("Flight " + flightNumber + " Closed"); // 文本更新为Closed
                            ((Timer)e.getSource()).stop(); // 停止计时器
                            so.closef3();
                        }
                    }
                    // countdownLabel.setText("Flight " + flightNumber + " - " + timeLeft[0]/60 + "M" + timeLeft[0]%60 + "S");

                    
                    
                    
                }
            });
            timer.start(); // 启动计时器
            flightTimers.put(flightNumber, timer); // Store the timer reference
            panel.add(flightPanel);
        }

        return panel;
    }

    private JSlider createSpeedSlider() {
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 25, 400, timerSpeed);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setMinorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(25, new JLabel("X0.25"));
        labelTable.put(50, new JLabel("X0.5"));
        labelTable.put(100, new JLabel("X1"));
        labelTable.put(200, new JLabel("X2"));
        labelTable.put(300, new JLabel("X3"));
        labelTable.put(400, new JLabel("X4"));
        speedSlider.setLabelTable(labelTable); // 设置滑动条的标签
        speedSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                timerSpeed = 100000/source.getValue() ;
               
                // Adjust all timers according to the new speed
                adjustTimerSpeeds();
            }
        });

        return speedSlider;
    }

    private void adjustTimerSpeeds() {
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
				Thread.sleep(10 * timerSpeed);//Thread.Sleep()方法用于将当前线程休眠一定时间，单位为毫秒。这里为每1000毫秒休眠一次线程。
				
            	
				// for passenger queue 1
				queueCount1 = so.getQueue1().size();
	            Queue<Passenger> q1 = so.getQueue1();
	            if (q1state == false && q2state == false) {
	            	for (JPanel d: JPDesk) {
	            		d.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 初始边框设置为绿色
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
	            }
	            if (!so.isF1() && !so.isF2() && !so.isF3()) {
	            	for (JPanel d: JPDesk) {
	            		d.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 初始边框设置为绿色
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
				e.printStackTrace();//抛出异常
			}
		}
	}

    public static void main(String[] args) {
    	AirportGUI a = new AirportGUI();
    	new Thread(a).start();//启动线程
    }
}