package checkinSys;
import java.util.HashMap;
import java.util.List;

public class FlightManager {
//	private List<Flight> flights;
	public HashMap<String, Flight> flights = new HashMap<>();
    public void loadFlightsFromFile(String filename) {
        // 从文件加载航班数据
    }

    public Flight findFlight(String flightCode) {
        // 查找航班
    	return null;
    }
    
    public static void main(String[] args) {
    	System.out.print("something");
    }
}
