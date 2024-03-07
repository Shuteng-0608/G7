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
		JLabel nameLabel = new JLabel("Last Name:");
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
				appendToReport("Data load successfully!");

				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						manager.report();
						System.out.println("\nProgram quit!");
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
					// Clear the input fields and prompt the user if input is invalid
					nameField.setText("");
					reservationField.setText("");
					last_name = "";
					reservationNumber = "";
					JOptionPane.showMessageDialog(null, "Please enter the correct last name and reference code！");
					return;
				}
				// Check-in logic
				if (manager.findPassenger(last_name, reservationNumber) != null) {
					if (manager.check_in(last_name, reservationNumber))
						appendToReport("You have already checked in！");
					else
						appendToReport("Check in success！");
				}
				// if check in failed, empty the report frame
				else {
					nameField.setText("");
					reservationField.setText("");
					appendToReport("Invalid information, please re-enter your details!");
				}
			}
		});

		// Event listener for luggage button to proceed to luggage check-in interface
		proceedToLuggageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				appendToReport("Please type your Laggage weight and volume！");
				openLuggageCheckPanel();
			}
		});
	}

	// Opens a new window for luggage check-in process
	private void openLuggageCheckPanel() {
		// Frame for luggage check-in
		JFrame luggageCheckFrame = new JFrame("Luggage Check-in");
		luggageCheckFrame.setSize(400, 200);
		luggageCheckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		luggageCheckFrame.setLocationRelativeTo(this);

		// Panel for luggage check-in interface
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JLabel weightLabel = new JLabel("Baggage Weight(kg):");
		weightField = new JTextField();
		JLabel volumeLabel = new JLabel("Baggage Volume (m³):");
		volumeField = new JTextField();
		proceedToPaymentButton = new JButton("Pay");
		proceedToSuccessButton = new JButton("Success");


		// Adding components to the panel
		panel.add(weightLabel);
		panel.add(weightField);
		panel.add(volumeLabel);
		panel.add(volumeField);
		panel.add(proceedToPaymentButton);
		panel.add(proceedToSuccessButton);

		// Add the panel to the frame and make it visible
		luggageCheckFrame.add(panel);
		luggageCheckFrame.setVisible(true);

		// Button action handling within the luggage check-in window
		proceedToPaymentButton.addActionListener(new ActionListener() {
			// Implement the logic for processing luggage weight and volume inputs
			// along with payment process
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
					JOptionPane.showMessageDialog(null, "Please enter the correct Baggege weight and volume！");
					return;
				}


				// Check if there are any extra fees due to baggage weight or volume
				double fee = manager.excess_fee(last_name, reservationNumber, weight_, volume_);
				if (fee != 0) {
					// If there are extra fees, inform the user and add to the report
					JOptionPane.showMessageDialog(null,
							"Baggage is overweight or over volume, additional fees required: ￡" + fee);
					appendToReport("Extra fees : ￡" + fee); // Adding the information about extra fees to the report
				} else {
					// If there are no extra fees, inform the user that luggage check-in was successful
					JOptionPane.showMessageDialog(null, "Luggage check-in successful!");
				}
			}
		});

		proceedToSuccessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Logic to be added for transitioning to the success interface
				luggageCheckFrame.dispose();
				appendToReport("Luggage check-in completed！");
				nameField.setText("");
				reservationField.setText("");
			}
		});
	}

	// Method to append text to the report area
	private void appendToReport(String text) {
		reportTextArea.append(text + "\n");
	}
	
    @Override
    public void dispose() {
        super.dispose();// Clean up resources
		// Additional clean-up code can go here if needed
    }

	// This should indicate, for each flight,
	// the number of passengers checked-in, the total weight of their baggage, the
	// total volume of
	// their baggage, and the total excess baggage fees collected. It should also
	// indicate whether

//	private void report() {
//		for (int i = 0; i < manager.getFlightList().getNumberOfEntries(); i++) {
//			Flight f = manager.getFlightList().getFlight(i);
//			reportBuilder.append("*******************************************").append("\n");
//			writeReportToFile("*******************************************");
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
		// Launch the Swing application using the Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI gui = new GUI();
				gui.setVisible(true);
			}
		});
	}
}
