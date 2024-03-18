package checkInSys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public class AirportSimulation {
	public static void main(String[] args) {
		
		List<Passenger> passengerQueue = new ArrayList<>();
		Manager manager = new Manager();
		for (Map.Entry<String, Passenger> entry : manager.getPassengers().entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
		
        

        // Populate queue with some randomly generated passengers
        for (int i = 1; i <= 10; i++) {
            
            passengerQueue.add(passenger);
        }

        // Create check-in desks
        List<Thread> checkInThreads = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
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
