package checkinSys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckInInterface {

    // Main frame
    private JFrame frame;

    // Components
    private JTextField lastNameField;
    private JTextField bookingReferenceField;
    private JButton validateButton;
    private JLabel baggageFeeLabel;

    public CheckInInterface() {
        // Initialize GUI components
        frame = new JFrame("Check-In System");
        lastNameField = new JTextField(20);
        bookingReferenceField = new JTextField(20);
        validateButton = new JButton("Validate");
        baggageFeeLabel = new JLabel("Baggage fee will appear here.");

        // Layout
        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("Last Name:"));
        frame.add(lastNameField);
        frame.add(new JLabel("Booking Reference:"));
        frame.add(bookingReferenceField);
        frame.add(validateButton);
        frame.add(baggageFeeLabel);

        // Add action listener to the button
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lastName = lastNameField.getText();
                String bookingRef = bookingReferenceField.getText();
                boolean isValid = receiveAndValidateInfo(lastName, bookingRef);
                if (isValid) {
                    displayBaggageFee(20.0); // Sample fee
                } else {
                    displayBaggageFee(0);
                }
            }
        });

        // Frame settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public void displayInterface() {
        // Already handled in the constructor
    }

    public boolean receiveAndValidateInfo(String lastName, String bookingReference) {
        // Here, implement the actual logic to validate information
        // This is a dummy implementation
        return lastName != null && !lastName.isEmpty() && bookingReference != null && !bookingReference.isEmpty();
    }

    public void displayBaggageFee(double fee) {
        baggageFeeLabel.setText("Baggage Fee: $" + fee);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CheckInInterface();
            }
        });
    }
}
