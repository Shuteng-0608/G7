package checkinSys;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class flightManager {
//	private List<Flight> flights;
	public HashMap<String, flight> flights = new HashMap<>();
	
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
    
}
