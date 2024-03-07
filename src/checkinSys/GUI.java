package checkinSys;

import javax.swing.*;
import myExceptions.InvalidAttributeException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger; 

public class GUI extends JFrame {
	private Manager manager;
	
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());
    private StringBuilder reportBuilder;
    
	private JTextArea reportTextArea;

	// Text fields for user input
	private JTextField nameField;
	private JTextField reservationField;
	private JTextField weightField;
	private JTextField volumeField;

	// Buttons for different actions
	private JButton proceedToPaymentButton;
	private JButton proceedToSuccessButton;

	// Constructor setting up the GUI
	public GUI() {
		super("Airport Check-in System");

		// Set properties of the window
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window

		// Create panels and layouts
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		JPanel inputPanel = new JPanel(new GridLayout(2, 2));

		// Create buttons
		JButton loadButton = new JButton("Load Data");
		JButton checkInButton = new JButton("Check-in");
		JButton proceedToLuggageButton = new JButton("Proceed to Luggage");

		// Create the report text area
		reportTextArea = new JTextArea();
		reportTextArea.setEditable(false);

		// Create input fields and labels
		JLabel nameLabel = new JLabel("Name:");
		nameField = new JTextField();
		JLabel reservationLabel = new JLabel("Reservation Number:");
		reservationField = new JTextField();



		// Add components to the input panel
		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(reservationLabel);
		inputPanel.add(reservationField);

		// Add buttons to the button panel
		buttonPanel.add(loadButton);
		buttonPanel.add(checkInButton);
		buttonPanel.add(proceedToLuggageButton);

		// Add components to the main panel
		mainPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(inputPanel, BorderLayout.NORTH);
		
	    // Initialize the report content
	    reportBuilder = new StringBuilder();

		// Add main panel to the frame
		add(mainPanel);
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				manager = new Manager();
				appendToReport("数据加载成功!");

				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						manager.report();
						System.out.println("\n程序已退出!");
					}
				});
			}
		});
		// Action listener for the load button
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				manager = new Manager();
				
				appendToReport("Data loaded successfully!");
			}
		});

		// Action listener for the check-in button
		checkInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String last_name = "";
				String reservationNumber = "";

				try {
					last_name = nameField.getText();
					reservationNumber = reservationField.getText();
					if (last_name.isEmpty()) throw new InvalidAttributeException("last_name cannot be empty");
					if (reservationNumber.isEmpty()) throw new InvalidAttributeException("Flight reservationNumber cannot be empty");
					if (last_name.length() > 20) {
						last_name = "";
						reservationNumber = "";
						throw new InvalidAttributeException("last_name illeagel");
					}
					if (reservationNumber.length() > 20) {
						last_name = "";
						reservationNumber = "";
						throw new InvalidAttributeException("Flight reservationNumber illeagel");
					}
					
				} catch (NullPointerException | InvalidAttributeException e1) {
					nameField.setText("");
					reservationField.setText("");
					last_name = "";
					reservationNumber = "";
					JOptionPane.showMessageDialog(null, "请正确输入姓名和预定号！");
					return;
				}
				
				if (manager.findPassenger(last_name, reservationNumber) != null) {
					if (manager.check_in(last_name, reservationNumber))
						appendToReport("您已办理登机手续！");
					else
						appendToReport("登机手续办理成功！");
				}
				// 如果值机失败，则清空输入框
				else {
					nameField.setText("");
					reservationField.setText("");
					appendToReport("信息有误，请重新输入个人信息！");
				}
			}
		});

		// 跳转到行李托运界面按钮的点击事件处理
		proceedToLuggageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 这里可以添加逻辑来处理跳转到行李托运界面的操作
				// 留待您根据需求来完成
				appendToReport("请您输入行李的重量和尺寸！");
				openLuggageCheckPanel();
			}
		});
	}

	// 打开行李托运界面
	private void openLuggageCheckPanel() {
		JFrame luggageCheckFrame = new JFrame("行李托运");
		luggageCheckFrame.setSize(400, 200);
		luggageCheckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		luggageCheckFrame.setLocationRelativeTo(this);

		JPanel panel = new JPanel(new GridLayout(3, 2));
		JLabel weightLabel = new JLabel("行李重量(kg):");
		weightField = new JTextField();
		JLabel volumeLabel = new JLabel("行李体积(m³):");
		volumeField = new JTextField();
		proceedToPaymentButton = new JButton("付款");
		proceedToSuccessButton = new JButton("成功");

		panel.add(weightLabel);
		panel.add(weightField);
		panel.add(volumeLabel);
		panel.add(volumeField);
		panel.add(proceedToPaymentButton);
		panel.add(proceedToSuccessButton);

		luggageCheckFrame.add(panel);
		luggageCheckFrame.setVisible(true);

		proceedToPaymentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String weight = null; 
				String volume = null; 
				String last_name = nameField.getText();
				String reservationNumber = reservationField.getText();
				double weight_ = 0;
				double volume_ = 0;
				weight = weightField.getText();
				volume = volumeField.getText();	
				try {
					
					weight_ = Double.parseDouble(weight);
					volume_ = Double.parseDouble(volume);
					if (weight_ < 0 || weight_ > 50) {
						weight = null;
						volume = null;
						throw new InvalidAttributeException("Weight  must be a non-negative Double");
					}
					if (volume_ < 0 || volume_ > 50) {
						weight = null;
						volume = null;
						throw new InvalidAttributeException("volume must be a non-negative Double");
					}
					
					
				} catch (NumberFormatException | InvalidAttributeException e1) {
					weightField.setText("");
					volumeField.setText("");
					JOptionPane.showMessageDialog(null, "请正确输入行李重量和尺寸！");
					return;
				}
				
				
				
				double fee = manager.excess_fee(last_name, reservationNumber, weight_, volume_);
				if (fee != 0) {
					// 计算额外费用
					JOptionPane.showMessageDialog(null,
							"行李超重或超体积，需要支付额外费用: $" + fee);
					appendToReport("额外费用: $" + fee); // 将额外费用信息添加到报告
				} else {
					JOptionPane.showMessageDialog(null, "行李托运成功!");
				}
			}
		});

		proceedToSuccessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 这里可以添加逻辑来处理跳转到成功界面的操作
				luggageCheckFrame.dispose();
				appendToReport("行李托运已完成！");
				nameField.setText("");
				reservationField.setText("");
			}
		});
	}

	// 在报告文本区域中追加文本
	private void appendToReport(String text) {
		reportTextArea.append(text + "\n");
	}
	
    @Override
    public void dispose() {
        super.dispose();
    }

	// This should indicate, for each flight,
	// the number of passengers checked-in, the total weight of their baggage, the
	// total volume of
	// their baggage, and the total excess baggage fees collected. It should also
	// indicate whether
	// the capacity of the flight is exceeded in any way.
	// 将文本写入报告文件
//	private void report() {
//		for (int i = 0; i < manager.getFlightList().getNumberOfEntries(); i++) {
//			Flight f = manager.getFlightList().getFlight(i);
//			reportBuilder.append("*******************************************").append("\n"); // 将文本追加到报告内容中
//			writeReportToFile("*******************************************"); // 将文本写入报告文件
//			writeReportToFile("For flight " + f.getCarrier() + ":");
//			writeReportToFile("The number of passengers checked-in is " + f.numberOfCheckIn());
//			writeReportToFile("The total weight of their baggage is " + f.totalWeight());
//			writeReportToFile("The total volume of their baggage is " + f.totalVolume());
//			writeReportToFile("The total excess baggage fees collected is " + f.totalFees());
//			writeReportToFile("The maximum baggage weight of this flight is " + f.getWeight());
//			writeReportToFile("The maximum baggage volume of this flight is " + f.getVolume());
//			if (f.totalWeight() <= f.getWeight() && f.totalVolume() <= f.getVolume() && f.numberOfCheckIn() <= f.getCapacity()) {
//				writeReportToFile("The capacity of the flight is not exceeded");
//			} else
//				writeReportToFile("The capacity of the flight is exceeded");
//			writeReportToFile("*******************************************");
//		}
//	}
	
	public static void main(String[] args) {
		// 使用 Event Dispatch Thread (EDT) 启动 Swing 应用程序
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI gui = new GUI();
				gui.setVisible(true);
			}
		});
	}
}
