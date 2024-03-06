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

	private static final Logger LOGGER = Logger.getLogger(GUI.class.getName()); // 创建日志记录器

	private JTextArea reportTextArea;
	private JLabel queueStatusLabel;

	private JTextField nameField;
	private JTextField reservationField;

	private JTextField weightField;
	private JTextField volumeField;

	private JButton proceedToPaymentButton;
	private JButton proceedToSuccessButton;

	private StringBuilder reportBuilder;

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

		// create a state report area
		reportTextArea = new JTextArea();
		reportTextArea.setEditable(false);

		// create the input label
		JLabel nameLabel = new JLabel("Last Name:");
		nameField = new JTextField();
		JLabel reservationLabel = new JLabel("Reference Code:");
		reservationField = new JTextField();

		// add components to the pannel
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

		// add main panel to the windows
		add(mainPanel);

		// add action handle button to process the event
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.readFromFile(
						"data/flight_details_data.csv",
						"data/passenger_data.csv");
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
					appendToReport("addtional fee: ￡"); // add the additional fee to the report
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
	// add text to the report area
	private void appendToReport(String text) {
		reportTextArea.append(text + "\n");
		reportBuilder.append(text).append("\n"); // add extra info to the report contents
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() ){
			@Override
			public void run() {
				GUI gui = new GUI();
				gui.setVisible(true);
			}
		});
	}
}
