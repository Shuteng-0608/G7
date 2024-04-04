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

public class AirportGUI extends JFrame implements Runnable {
	private JPanel queuePanel;
	private JPanel deskPanel;
	private JPanel flightPanel;
	private JPanel flightInfo;
	private JPanel deskControlPanel;
	private JSlider speedSlider;

	private Random timeSetter = new Random();
	private final int deskWidth = 200;
	private final int deskHeight = 50;
	private final int queueNum = 2;
	private final int deskNum = 5;
	private final int flightNum = 3;
	private final int queuePanelHeight = 200;
	private int timerSpeed = 1000;
	final int[] timeLeft1 = { 20 + timeSetter.nextInt(100) };
	final int[] timeLeft2 = { 20 + timeSetter.nextInt(100) };
	final int[] timeLeft3 = { 20 + timeSetter.nextInt(100) };

	private SharedObject so;
	private ArrayList<Thread> checkInThreads = new ArrayList<>();
	private ArrayList<Thread> queueThreads = new ArrayList<>();

	private ArrayList<CheckInDesk> desks = new ArrayList<>();
	private ArrayList<PassengerQueue> queues = new ArrayList<>();
	private ArrayList<JPanel> deskPanels = new ArrayList<>();
	private ArrayList<JCheckBox> deskButtons = new ArrayList<>();
	private Map<Integer, Timer> flightTimers = new HashMap<>();

	private Font fontS = new Font("Comic Sans MS", Font.BOLD, 12);
	private Font fontM = new Font("Comic Sans MS", Font.BOLD, 14);
	private Font fontL = new Font("Comic Sans MS", Font.BOLD, 18);

	public AirportGUI() {
		so = new SharedObject();
		setTitle("Airport Check-in System");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		// For passenger queue
		passengerQueue();

		// For check-in desk
		checkInDesk();

		// for flight
		flightInfo();

		flightPanel = createFlightPanel();
		createSpeedSlider();

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
	
	/**
	 * PassengerQueue panel initialization
	 */
	public void passengerQueue() {
		queuePanel = new JPanel(new GridLayout(1, 2, 10, 10));
		queuePanel.setPreferredSize(new Dimension(getWidth(), queuePanelHeight));
		queuePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JTextArea queue1Text = new JTextArea("There are currently " + 0 + " people in Business Queue");
		PassengerQueue tmp1 = new PassengerQueue("Business", so, queue1Text);
		queues.add(tmp1);
		Thread thread1 = new Thread(tmp1);
		thread1.start();
		queueThreads.add(thread1);
		queue1Text.setEditable(false);
		JScrollPane queue1Scroll = new JScrollPane(queue1Text);
		queuePanel.add(queue1Scroll);

		JTextArea queue2Text = new JTextArea("There are currently " + 0 + " people in Economy Queue");
		PassengerQueue tmp2 = new PassengerQueue("Economy", so, queue2Text);
		queues.add(tmp2);
		Thread thread2 = new Thread(tmp2);
		thread2.start();
		queueThreads.add(thread2);
		queue2Text.setEditable(false);
		JScrollPane queue2Scroll = new JScrollPane(queue2Text);
		queuePanel.add(queue2Scroll);
	}
	
	/**
	 * check-in desk initialization
	 */
	public void checkInDesk() {
		deskControlPanel = new JPanel(new GridLayout(1, 5, 10, 10));
		deskControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		deskPanel = new JPanel(new GridLayout(1, 5, 10, 10));
		deskPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// check-in desk initialization
		for (int i = 0; i < deskNum; i++) {
			JTextArea deskText = new JTextArea("Desk " + (i + 1));
			deskText.setFont(fontS);
			deskText.setEditable(false);
			deskText.setPreferredSize(new Dimension(150, 100));
			deskText.setLineWrap(true);
			deskText.setWrapStyleWord(true);
			deskPanel.add(deskText);

			JPanel deskPanel = new JPanel();
			deskPanels.add(deskPanel);
			deskPanel.setPreferredSize(new Dimension(deskWidth, deskHeight));
			deskPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			JCheckBox deskButton;

			String deskType;
			if (i == 0 || i == 1 || i == 2) {
				deskButton = new JCheckBox("Business Desk " + (i + 1) + " Open");
				deskType = "Business";
			} else {
				deskButton = new JCheckBox("Economy Desk " + (i + 1) + " Open");
				deskType = "Economy";
			}
			deskButton.setFont(fontM);
			deskButtons.add(deskButton);
			deskPanel.add(deskButton);
			deskControlPanel.add(deskPanel);

			CheckInDesk desk = new CheckInDesk("Desk " + (i + 1), deskType, so, deskText, deskButton, deskPanel);
			desk.setTimer(timerSpeed);
			Thread thread = new Thread(desk);
			checkInThreads.add(thread);
			desks.add(desk);
			thread.start();
		}
	}
	
	/**
	 * flight info initialization
	 */
	public void flightInfo() {
		flightInfo = new JPanel(new GridLayout(1, 3, 10, 10));
		flightInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (int i = 0; i < flightNum; i++) {
			JTextArea flightText = new JTextArea(" ");
			flightText.setFont(fontL);
			flightText.setEditable(false);
			flightText.setPreferredSize(new Dimension(150, 100));
			flightText.setLineWrap(true);
			flightText.setWrapStyleWord(true);
			flightInfo.add(flightText);

			Flight flight = new Flight();
			flight.setText(flightText);
			so.addFlightDesk(flight);
		}
	}
	
	/**
	 * @return The Flight Panel
	 */
	public JPanel createFlightPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setPreferredSize(new Dimension(getWidth(), 70));

		for (int i = 1; i <= flightNum; i++) {
			final int flightNumber = i;
			JPanel flightPanel = new JPanel(new BorderLayout());
			flightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JLabel countdownLabel = new JLabel("", SwingConstants.CENTER);
			countdownLabel.setFont(fontL);
			flightPanel.add(countdownLabel, BorderLayout.CENTER);

			Timer timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (flightNumber == 1) {
						timeLeft1[0]--;
						countdownLabel.setText(
								"Flight " + "CA378" + " - " + timeLeft1[0] / 60 + "M" + timeLeft1[0] % 60 + "S");
						if (timeLeft1[0] <= 0) {
							flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
							countdownLabel.setText("Flight " + flightNumber + " Closed");
							Logger.log(Logger.LogLevel.INFO, "Flight " + flightNumber + " Closed");
							countdownLabel.setForeground(Color.RED);
							((Timer) e.getSource()).stop();
							so.getFlightDesk().get(0).close();

						}
					}
					if (flightNumber == 2) {
						timeLeft2[0]--;
						countdownLabel.setText(
								"Flight " + "EK216" + " - " + timeLeft2[0] / 60 + "M" + timeLeft2[0] % 60 + "S");
						if (timeLeft2[0] <= 0) {
							flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
							countdownLabel.setText("Flight " + flightNumber + " Closed");
							countdownLabel.setForeground(Color.RED);
							((Timer) e.getSource()).stop();
							so.getFlightDesk().get(1).close();
						}
					}
					if (flightNumber == 3) {
						timeLeft3[0]--;
						countdownLabel.setText(
								"Flight " + "EK660" + " - " + timeLeft3[0] / 60 + "M" + timeLeft3[0] % 60 + "S");
						if (timeLeft3[0] <= 0) {
							flightPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
							countdownLabel.setText("Flight " + flightNumber + " Closed");
							countdownLabel.setForeground(Color.RED);
							((Timer) e.getSource()).stop();
							so.getFlightDesk().get(2).close();
						}
					}

				}
			});
			timer.start();
			flightTimers.put(flightNumber, timer); // Store the timer reference
			panel.add(flightPanel);
		}

		return panel;
	}
	
	/**
	 * Create Speed Slider
	 */
	public void createSpeedSlider() {
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
		speedSlider.setLabelTable(labelTable);
	}
	
	/**
	 * Adjust Timer Speeds
	 */
	public void adjustTimerSpeeds() {
		for (Map.Entry<Integer, Timer> entry : flightTimers.entrySet()) {
			Timer timer = entry.getValue();
			if (timer != null) {
				timer.setDelay(timerSpeed);
				// Note: This does not restart the timer; it only adjusts the delay for future
				// ticks.
			}
		}
	}
	
	
	/**
	 * GUI Information Display Update threads
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(timerSpeed);

				// for passenger queue 1
				PassengerQueue business = queues.get(0);
				PassengerQueue economy = queues.get(1);
				business.setTimer(timerSpeed);
				economy.setTimer(timerSpeed);

				if (!business.states() && !economy.states()) {
					for (JPanel d : deskPanels) {
						d.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					}

					for (CheckInDesk desk : desks) {
						desk.getButton().setText("Close");
						desk.getButton().setSelected(true);
						desk.getText().setText("All Passengers Have Checked-in");
					}
					Logger.log(Logger.LogLevel.INFO, "All Passengers Have Checked-in");
				}
				if (!so.getFlightDesk().get(0).states() && !so.getFlightDesk().get(1).states()
						&& !so.getFlightDesk().get(2).states()) {
					for (JPanel d : deskPanels) {
						d.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					}

					for (CheckInDesk desk : desks) {
						desk.getButton().setText("Close");
						desk.getButton().setSelected(true);
						desk.getText().setText("All Flights Have Departured");
					}
					Logger.log(Logger.LogLevel.INFO, "All Flights Have Departured");

				}

				if (business.states()) {
					business.getText()
							.setText("There are currently " + so.getQueue1().size() + " people in Business queue");
					for (Passenger p : so.getQueue1()) {
						business.getText().append("\n" + p.getFlight() + "\t" + p.getName() + "          " + "\t"
								+ p.getWeight() + "\t" + p.getVolume());
					}
				}

				if (economy.states()) {
					economy.getText()
							.setText("There are currently " + so.getQueue2().size() + " people in Economy queue");
					for (Passenger p : so.getQueue2()) {
						economy.getText().append("\n" + p.getFlight() + "\t" + p.getName() + "          " + "\t"
								+ p.getWeight() + "\t" + p.getVolume());
					}
				}

				// for check-in desk
				for (CheckInDesk desk : desks) {
					Passenger p = desk.getClient();
					if (!desk.states() || p == null) {
						continue;
					}
					if (so.getFlightDesk().get(0).states() || so.getFlightDesk().get(1).states()
							|| so.getFlightDesk().get(2).states()) {
						if (p.getCabin().equals("Business")) {
							desk.getText().setText("[VIP Membership] " + p.getName() + " is dropping off 1 bag of "
									+ p.getWeight() + "kg. ");
						} else {
							desk.getText().setText(p.getName() + " is dropping off 1 bag of " + p.getWeight() + "kg. ");
						}

						if (p.excess_fee() != 0)
							desk.getText()
									.append("A buggage fee of " + String.format("%.2f", p.excess_fee()) + "$ is due.");
						else
							desk.getText().append("\n" + "No baggage fee is due.");
					}
				}
				so.getFlightDesk().get(0).getText().setText(so.getPassengerNum1() + " checked in of " + "184");
				so.getFlightDesk().get(0).getText()
						.append("\n" + " Hold is " + String.format("%.2f", 100 * (so.getBaggageNum1() / 6440)) + "%");
				so.getFlightDesk().get(1).getText().setText(so.getPassengerNum2() + " checked in of " + "266");
				so.getFlightDesk().get(1).getText()
						.append("\n" + " Hold is " + String.format("%.2f", 100 * (so.getBaggageNum2() / 9310)) + "%");
				so.getFlightDesk().get(2).getText().setText(so.getPassengerNum3() + " checked in of " + "190");
				so.getFlightDesk().get(2).getText()
						.append("\n" + " Hold is " + String.format("%.2f", 100 * (so.getBaggageNum3() / 6650)) + "%");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setTimerSpeed(int timerspeed) {
		this.timerSpeed = timerspeed;
	}

	public JSlider getSpeedSlider() {
		return speedSlider;
	}

	public int getDeskNum() {
		return deskNum;
	}

	public ArrayList<CheckInDesk> getDesks() {
		return desks;
	}

	public ArrayList<JCheckBox> getDeskButton() {
		return deskButtons;
	}
}
