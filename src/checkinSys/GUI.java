package checkinSys;

import javax.swing.*;
import myExceptions.InvalidAttributeException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger; // 导入日志记录器类

public class GUI extends JFrame {
	private Manager manager;
	
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName()); // 创建日志记录器
    private StringBuilder reportBuilder; // 用于保存报告内容
    
	private JTextArea reportTextArea;

	private JTextField nameField;
	private JTextField reservationField;
	private JTextField weightField;
	private JTextField volumeField;

	private JButton proceedToPaymentButton;
	private JButton proceedToSuccessButton;

	public GUI() {
		super("机场值机系统");

		// 设置窗口属性
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 窗口居中显示

		// 创建面板和布局
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		JPanel inputPanel = new JPanel(new GridLayout(2, 2));

		// 创建按钮
		JButton loadButton = new JButton("加载数据");
		JButton checkInButton = new JButton("值机");
		JButton proceedToLuggageButton = new JButton("继续行李托运");

		// 创建报告文本区域
		reportTextArea = new JTextArea();
		reportTextArea.setEditable(false);

		// 创建输入字段和标签
		JLabel nameLabel = new JLabel("姓名:");
		nameField = new JTextField();
		JLabel reservationLabel = new JLabel("预约号码:");
		reservationField = new JTextField();
		
		

		// 添加组件到输入面板
		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(reservationLabel);
		inputPanel.add(reservationField);

		// 添加按钮到按钮面板
		buttonPanel.add(loadButton);
		buttonPanel.add(checkInButton);
		buttonPanel.add(proceedToLuggageButton);

		// 添加组件到主面板
		mainPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(inputPanel, BorderLayout.NORTH);
		
	    // 初始化报告内容
	    reportBuilder = new StringBuilder();

		// 添加主面板到窗口
		add(mainPanel);

		// 加载数据按钮的点击事件处理
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 加载数据
				
				manager = new Manager();
				
				appendToReport("数据加载成功!");
			}
		});

		// 办理登机手续按钮的点击事件处理
		checkInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String last_name = "";
				String reservationNumber = "";
//				String last_name_ = "";
//				String reservationNumber_ = "";
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
