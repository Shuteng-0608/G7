package checkInSys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public class AirportSimulation {
	public static void main(String[] args) {
		List<Thread> checkInThreads = new ArrayList<>();
		List<Passenger> passengerQueue = new ArrayList<>();
		List<Passenger> passengerNotCheckIn = new ArrayList<>();
		int queueNum = 1;
		int checkInDeskNum = 3;
		Manager manager = new Manager();
		for (Map.Entry<String, Passenger> entry : manager.getPassengers().entrySet()) {
            //
        }
		
		
		
		// Create passenger queue
		for (int i = 1; i <= queueNum; i++) {
			PassengerQueue queue = new PassengerQueue("economy", passengerQueue, passengerNotCheckIn);
			Thread thread = new Thread(queue);
			checkInThreads.add(thread);
	        thread.start();
		}
		

        // Create check-in desks
        
        for (int i = 1; i <= checkInDeskNum; i++) {
            CheckInDesk desk = new CheckInDesk("Desk" + i, passengerQueue);
            Thread thread = new Thread(desk);
            checkInThreads.add(thread);
            thread.start();
        }

        // Wait for all check-in desks to finish processing
        for (Thread thread : checkInThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Logger.log("All passengers have been processed.");
    }
}
