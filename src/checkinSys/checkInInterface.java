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
    	
    }

    public void displayInterface() {
        // Already handled in the constructor
    }

    public boolean receiveAndValidateInfo(String lastName, String bookingReference) {

    }

    public void displayBaggageFee(double fee) {

    }

    public static void main(String[] args) {

    }
}
