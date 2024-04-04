package checkInSimulation;

import GUI.AirportGUI;

public class AirportSimulation {
	
	
	/**
	 * Main Entrance of the Whole Application
	 */
	public static void main(String[] args) {
		
		Logger logger = Logger.getInstance();
		AirportGUI airport = new AirportGUI();
		Controller c = new Controller(airport);
		
	}

}
