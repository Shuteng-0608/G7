import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class GUI extends JFrame {
	Manager manager = new Manager();

	private JTextField nameField;
	private JTextField reservationField;

	private JTextField weightField;
	private JTextField volumeField;

	private JButton proceedToPaymentButton;
	private JButton proceedToSuccessButton;


	public GUI() {
		super("Check-in System");

		// set frame
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // frame to the middle

		// create the  pannel
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		JPanel inputPanel = new JPanel(new GridLayout(2, 2));

		// create the button
		JButton loadButton = new JButton("Load Data");
		JButton checkInButton = new JButton("Check in");
		JButton proceedToLuggageButton = new JButton("Laggage Check");



		// create the input label
		JLabel nameLabel = new JLabel("Last Name:");
		nameField = new JTextField();
		JLabel reservationLabel = new JLabel("Reference Code:");
		reservationField = new JTextField();

		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(reservationLabel);
		inputPanel.add(reservationField);

		// add the button to the panel
		buttonPanel.add(loadButton);
		buttonPanel.add(checkInButton);
		buttonPanel.add(proceedToLuggageButton);

		// add to the main panel
		mainPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(inputPanel, BorderLayout.NORTH);


		add(mainPanel);

		// add action haddle
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.readFromFile(
						"D:\\WeChat Files\\WeChat Files\\wxid_iiosqnpbqnjd12\\FileStorage\\File\\2024-03\\flight_details_data.csv",
						"D:\\WeChat Files\\WeChat Files\\wxid_iiosqnpbqnjd12\\FileStorage\\File\\2024-03\\passenger_data.csv");
				appendToReport("Data load success");
			}
		});

		checkInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String last_name = nameField.getText();
				String reservationNumber = reservationField.getText();
				manager.check_in(last_name, reservationNumber);
				appendToReport("Check-in Success");
				// if failed to check in clear the input fraame
				if (manager.check_in(last_name, reservationNumber)) {
					nameField.setText("");
					reservationField.setText("");
				}
			}
		});

		// jump to the laggage handle
		proceedToLuggageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				appendToReport("Click the laggage Check");
				openLuggageCheckPanel();
			}
		});

		// 初始化报告内容
		reportBuilder = new StringBuilder();
	}

	// 打开行李托运界面
	private void openLuggageCheckPanel() {
		JFrame luggageCheckFrame = new JFrame("Laggage check");
		luggageCheckFrame.setSize(400, 200);
		luggageCheckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		luggageCheckFrame.setLocationRelativeTo(this);

		JPanel panel = new JPanel(new GridLayout(3, 2));
		JLabel weightLabel = new JLabel("Weight(kg):");
		weightField = new JTextField();
		JLabel volumeLabel = new JLabel("Volume(m³):");
		volumeField = new JTextField();
		proceedToPaymentButton = new JButton("Pay");
		proceedToSuccessButton = new JButton("Success");

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
				if (manager.excess_fee(last_name, reservationNumber, weight, volume) > 40) {
					JOptionPane.showMessageDialog(null, "Laggage overweight: ￡");
				} else {
					JOptionPane.showMessageDialog(null, "success");
				}
			}
		});

		proceedToSuccessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				luggageCheckFrame.dispose();
			}
		});
	}



	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI gui = new GUI();
				gui.setVisible(true);
			}
		});
	}
}
