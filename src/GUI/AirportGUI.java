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
    private JSlider speedSlider;
    JTextArea queue1Text;
    JTextArea queue2Text;
    private Random timeSetter = new Random();
    private final int deskWidth = 150; // 设置柜台窗口固定宽度
    private final int deskHeight = 150; // 设置柜台窗口固定高度
    private int queueCount1 = 0;  
    private int queueCount2 = 0;  
    private int queueNum = 2;
    private int deskNum = 5;
    private SharedObject so;
    private List<Thread> checkInThreads = new ArrayList<>();
    private List<Thread> queueThreads = new ArrayList<>();
    private final int queuePanelHeight = 200; // 设置队列面板固定高度

    private int timerSpeed = 1000; // 初始速度设置为1000毫秒，即1秒

    // 存储每个航班计时器的引用，以便可以根据滑动条的变化调整计时器速度
    private Map<Integer, Timer> flightTimers = new HashMap<>();
    public AirportGUI() {
    	so = new SharedObject();
        setTitle("Airport Check-in System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // 设置布局管理器间隔
        
        JPanel queuePanel = new JPanel(new GridLayout(1, 2, 10, 10));
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

        createQueuePanel(); // 创建队列显示区
        deskPanel = createDeskPanel();   // 创建柜台操作区
        flightPanel = createFlightPanel(); // 创建航班信息区
        speedSlider = createSpeedSlider();

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(flightPanel, BorderLayout.CENTER);
        bottomPanel.add(speedSlider, BorderLayout.SOUTH);

        add(queuePanel, BorderLayout.NORTH);
        add(deskPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack(); // 调整窗口以适应组件大小
        setVisible(true);
//        for (Thread thread : queueThreads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
                
    private void createQueuePanel() {
    	for (int i = 1; i <= queueNum; i++) {
			PassengerQueue queue = new PassengerQueue("economy " + i, so);
			Thread thread = new Thread(queue);
			queueThreads.add(thread);
	        thread.start();
		}
    }

    private JPanel createDeskPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加边距
//        for (int i = 1; i <= deskNum; i++) {
//			CheckInDesk queue = new CheckInDesk("Desk " + i, so);
//			Thread thread = new Thread(queue);
//			checkInThreads.add(thread);
//	        thread.start();
//		}
//        
//        for (Thread thread : checkInThreads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        for (int i = 1; i <= 5; i++) {
            final int deskNumber = i; // 使用final变量以便在匿名类中使用
            JPanel desk = new JPanel();
            desk.setPreferredSize(new Dimension(deskWidth, deskHeight)); // 设置柜台固定大小
            desk.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 初始边框设置为绿色
            JCheckBox checkBox = new JCheckBox("Desk " + deskNumber + " Close");
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    if (cb.isSelected()) {
                        desk.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // 勾选时边框变绿
                        cb.setText("Desk " + deskNumber + " Open"); // 更新复选框文本为Open
                    } else {
                        desk.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 未勾选时边框变回黑色
                        cb.setText("Desk " + deskNumber + " Close"); // 更新复选框文本为初始状态
                    }
                }
            });

            desk.add(checkBox);
            panel.add(desk);
        }

        return panel;
    }


    private JPanel createFlightPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(getWidth(), queuePanelHeight)); // 假设已经定义了queuePanelHeight

        for (int i = 1; i <= 6; i++) {
            final int flightNumber = i;
            JPanel flightPanel = new JPanel(new BorderLayout());
            flightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel countdownLabel = new JLabel("", SwingConstants.CENTER);
            flightPanel.add(countdownLabel, BorderLayout.CENTER);

            // 设置倒计时时间为1分钟
            final int[] timeLeft = {60 + timeSetter.nextInt(200)}; // 以秒为单位

            Timer timer = new Timer(1000, new ActionListener() { // 计时器每秒触发一次
                public void actionPerformed(ActionEvent e) {
                    timeLeft[0]--;
                    countdownLabel.setText("Flight " + flightNumber + " - " + timeLeft[0]/60 + "M" + timeLeft[0]%60 + "S");

                    if (timeLeft[0] <= 0) {
                        flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // 边框变红
                        countdownLabel.setText("Flight " + flightNumber + " Closed"); // 文本更新为Closed
                        ((Timer)e.getSource()).stop(); // 停止计时器
                    }
                }
            });
            timer.start(); // 启动计时器
            flightTimers.put(flightNumber, timer); // Store the timer reference

            panel.add(flightPanel);
        }

        return panel;
    }

    private JSlider createSpeedSlider() {
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 250, 4000, timerSpeed);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(250);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(250, new JLabel("X0.25"));
        labelTable.put(500, new JLabel("X0.5"));
        labelTable.put(1000, new JLabel("X1"));
        labelTable.put(2000, new JLabel("X2"));
        labelTable.put(3000, new JLabel("X3"));
        labelTable.put(4000, new JLabel("X4"));
        speedSlider.setLabelTable(labelTable); // 设置滑动条的标签
        speedSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                timerSpeed = 1000000/source.getValue() ;
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
			Thread.sleep(2000);//Thread.Sleep()方法用于将当前线程休眠一定时间，单位为毫秒。这里为每1000毫秒休眠一次线程。
			queueCount1 = so.getQueue1().size();
            Queue<Passenger> q1 = so.getQueue1();
            queue1Text.setText("There are currently "+ queueCount1 +" people in queue1");
            for (Passenger p : q1) {
            	queue1Text.append("\n" + p.getFlight() + "\t" +  p.getName()+ "         " + "\t" + p.getWeight() + "\t" + p.getVolume() );
            	queue1Text.paintImmediately(queue1Text.getBounds());
            }
            
            queueCount2 = so.getQueue2().size();
            queue2Text.setText("There are currently "+ queueCount2 +" people in queue2");
            Queue<Passenger> q2 = so.getQueue2();
            for (Passenger p : q2) {
            	queue2Text.append("\n" + p.getFlight() + "\t" +  p.getName() + "         " + "\t" + p.getWeight() + "\t" + p.getVolume() );
            	queue2Text.paintImmediately(queue2Text.getBounds());
            }
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