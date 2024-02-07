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


	public void loadFlightsFromFile(String filename) {
        // 从文件加载航班数据
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 解析行数据，创建Flight对象
                flight flight = parseFlight(line);
                flights.put(flight.getFlightCode(), flight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public void loadPassengersFromFile(String filename) {
        // 从文件加载乘客数据
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 解析行数据，创建Passenger对象
            	try {
                    passenger passenger = parsePassenger(line);
                    passengers.put(passenger.getBookingReference(), passenger);
                } catch (invalidBookingException e) {
                    // 处理异常，例如记录错误和继续
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private passenger parsePassenger(String line) throws invalidBookingException {
		// 解析文件内容TODO Auto-generated method stub
    	if (line == null) {
            throw new invalidBookingException("Invalid booking reference found in data.");
        }
		return null;
	}



    //检查id是否一直进行check-in
    public String checkPassenger(String name,String ID) {
    	
		return null;
    }
    
    public double exceedFees(double actualBaggage) {
    	double fees = 0;
    	return fees;
    }
    
    private flight parseFlight(String line) {
		// 解析文件内容TODO Auto-generated method stub
		return null;
	}

	public flight findFlight(String flightCode) {
        // 查找航班
    	return null;
    }
    
	public void updateFlightStats(String flightCode) {
        flight flight = flights.get(flightCode);
        if (flight != null) {
            // 更新航班统计数据
            
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
