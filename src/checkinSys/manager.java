package checkinSys;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class manager {
//	private List<Flight> flights;
	public HashMap<String, flight> flights = new HashMap<>();
	public HashMap<String, passenger> passengers = new HashMap<>();


	public void loadFlightsFromFile(String file_flight, String file_Passenger) {
		try {
			// 从文件加载航班数据
			String file = "D:\\Users\\Desktop\\software\\data\\flight_details_data.csv";
//			String file = "D:\\Users\\Desktop\\software\\data\\" + file_flight + ".csv";
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line =  br.readLine();
			FlightList flightList = new FlightList();
			int count = 0;
			ArrayList<String> destinationList = new ArrayList<String>();
			ArrayList<String> carrierList = new ArrayList<String>();
			ArrayList<Integer> capacityList = new ArrayList<Integer>();
			ArrayList<Double> weightList = new ArrayList<Double>();
			ArrayList<Double> volumeList = new ArrayList<Double>();
			
			while (line != null) {
				
				count++;
				
				if (count != 1) {
					
					String destination = line.split(",")[0];
					String carrier = line.split(",")[1];
					int capacity = Integer.parseInt(line.split(",")[2]);
					double weight = Double.parseDouble(line.split(",")[3]);
					double volume = Double.parseDouble(line.split(",")[4]);
					
					destinationList.add(destination);
					carrierList.add(carrier);
					capacityList.add(capacity);
					weightList.add(weight);
					volumeList.add(volume);
					
				}
				
				line = br.readLine();
			}
			
			for(int i = 0; i < destinationList.size(); i++) {
				
				Flight flight = new Flight(destinationList.get(i), carrierList.get(i), capacityList.get(i), weightList.get(i), volumeList.get(i));
				flightList.addFlight(flight);
						
			}	
			
			// 从文件加载乘客数据
			file = "D:\\Users\\Desktop\\software\\data\\Passenger_data.csv";
//			String file = "D:\\Users\\Desktop\\software\\data\\" + file_Passenger + ".csv";
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			line =  br.readLine();
			PassengerList passengerList = new PassengerList();
			
			count = 0;
			
			ArrayList<String> rcList = new ArrayList<String>();
			ArrayList<String> nameList = new ArrayList<String>();
			ArrayList<String> fcList = new ArrayList<String>();
			ArrayList<String> ciList = new ArrayList<String>();
			
			while (line != null) {
				String rc = line.split(",")[0];
				String name = line.split(",")[1];
				String fc = line.split(",")[2];
				String ci = line.split(",")[3];
				
				count++;
				
				if (count != 1) {
					rcList.add(rc);
					nameList.add(name);
					fcList.add(fc);
					ciList.add(ci);
				}
				
				line = br.readLine();
			}
			
			count = 0;
			int count1 = 0;
			
			while (count1 < rcList.size()) {
				
				for(int i = 0; i < rcList.size(); i++) {
					
					boolean checkin = false;
					if (ciList.get(i).equals("Yes")) {
						checkin = true;
					}else {
						checkin = false;
					}
					
					String previousFC;
					
					if (i > 0) {
						
						previousFC = fcList.get(i-1);
						
					}else {
						
						previousFC = fcList.get(i);
						
					}
					
					if (fcList.get(i).equals(previousFC)) {
						
						Passenger passenger = new Passenger(rcList.get(i), nameList.get(i), fcList.get(i), checkin);
						passengerList.addPassenger(passenger);
					
					}else {
						
						flightList.getFilght(count).setPassengerList(passengerList);
						passengerList = new PassengerList();
						Passenger passenger = new Passenger(rcList.get(i), nameList.get(i), fcList.get(i), checkin);
						passengerList.addPassenger(passenger);
						count++;	
					
					}
					
					count1 ++;
					
					if (count1 == rcList.size()) {
						flightList.getFilght(count).setPassengerList(passengerList);
					}
				}
			}	

			br.close();
			
			//Test 以下均为测试
			System.out.println(flightList.getNumberOfEntries());
			for(int i = 0; i < flightList.getNumberOfEntries(); i++) {
				System.out.println(flightList.getFilght(i).getList().getNumberOfEntries());
			}
			
			for(int i = 0; i < flightList.getNumberOfEntries(); i++) {
				System.out.println(flightList.getFilght(i).getDestination() + " " + flightList.getFilght(i).getCarrier()
						+ " " + flightList.getFilght(i).getCapacity() + " " + flightList.getFilght(i).getWeight()
						+ " " + flightList.getFilght(i).getVolume());
			}
			
			PassengerList pa = new PassengerList();
			for(int i = 0; i < flightList.getFilght(0).getList().getNumberOfEntries(); i++) {
				pa = flightList.getFilght(0).getList();
				System.out.println(pa.listDetails());
			}
//			for(int i = 0; i < flightList.getNumberOfEntries(); i++) {
////				for(int j = 0; j < flightList.getFilght(i).getList().getNumberOfEntries(); j++) {
//					System.out.println(flightList.getFilght(i).getList().listDetails());
////				}
//			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
	
    /**
	 * @return the flights
	 */
	public HashMap<String, flight> getFlights() {
		return flights;
	}
	/**
	 * @param flights the flights to set
	 */
	public void setFlights(HashMap<String, flight> flights) {
		this.flights = flights;
	}
    
    public List<passenger> generateReport() {
        // 生成报告
		return null;
    }
	/**
	 * @return the passengers
	 */
	public HashMap<String, passenger> getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(HashMap<String, passenger> passengers) {
		this.passengers = passengers;
	}
}
