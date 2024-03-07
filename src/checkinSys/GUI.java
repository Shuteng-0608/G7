package checkinSys;

import javax.swing.*;
import myExceptions.InvalidAttributeException;
import myExceptions.InvalidBookRefException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger; // 导入日志记录器类

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				manager.report();
				System.out.println("JFrame窗口正在关闭");
				//System.exit(0); // 退出程序
			}
		});
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
				manager = new Manager();
				appendToReport("数据加载成功!");
			}
		});

		// 办理登机手续按钮的点击事件处理
		checkInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String last_name = nameField.getText();
				String reservationNumber = reservationField.getText();
				try {
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
				} catch (InvalidBookRefException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
				double weight = Double.parseDouble(weightField.getText());
				double volume = Double.parseDouble(volumeField.getText());
				String last_name = nameField.getText();
				String reservationNumber = reservationField.getText();
				double fee = 0;
				try {
					fee = manager.excess_fee(last_name, reservationNumber, weight, volume);
				} catch (InvalidBookRefException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		System.out.print("i am cloasing");
	}



	public static void writeToTextFile(String report, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(report);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addReport(String report) {
		String reportContent = report;
		String filePath = "src/report.txt";
		GUI.writeToTextFile(reportContent, filePath);
	}

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