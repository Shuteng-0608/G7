package checkinSys;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class flight {
	private String initAirport;
	private String airCompany;
	private String destinationAirport;
    private int capacity;//人数
    private double maxBaggageWeight;
    private String maxBaggageVolume;//额定荷载
	private List<passenger> initPassengers;

    private double totalBaggageWeight;
    private String totalBaggageVolume;//实际荷载
    private double totalExcessFees;
    public HashMap<String, passenger> actulPassengers = new HashMap<>();
    
   

	public void loadPassengersFromFile(String filename) {
        // 从文件加载乘客数据
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 解析行数据，创建Passenger对象
            	try {
                    passenger passenger = parsePassenger(line);
                    actulPassengers.put(passenger.getBookingReference(), passenger);
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
    
	/**
	 * @return the initAirport
	 */
	public String getInitAirport() {
		return initAirport;
	}
	/**
	 * @param initAirport the initAirport to set
	 */
	public void setInitAirport(String initAirport) {
		this.initAirport = initAirport;
	}
	/**
	 * @return the airCompany
	 */
	public String getAirCompany() {
		return airCompany;
	}
	/**
	 * @param airCompany the airCompany to set
	 */
	public void setAirCompany(String airCompany) {
		this.airCompany = airCompany;
	}
	/**
	 * @return the destinationAirport
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}
	/**
	 * @param destinationAirport the destinationAirport to set
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the maxBaggageWeight
	 */
	public double getMaxBaggageWeight() {
		return maxBaggageWeight;
	}
	/**
	 * @param maxBaggageWeight the maxBaggageWeight to set
	 */
	public void setMaxBaggageWeight(double maxBaggageWeight) {
		this.maxBaggageWeight = maxBaggageWeight;
	}
	/**
	 * @return the maxBaggageVolume
	 */
	public String getMaxBaggageVolume() {
		return maxBaggageVolume;
	}
	/**
	 * @param maxBaggageVolume the maxBaggageVolume to set
	 */
	public void setMaxBaggageVolume(String maxBaggageVolume) {
		this.maxBaggageVolume = maxBaggageVolume;
	}
	/**
	 * @return the passengers
	 */
	public List<passenger> getPassengers() {
		return initPassengers;
	}
	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(List<passenger> passengers) {
		this.initPassengers = passengers;
	}
	/**
	 * @return the totalBaggageWeight
	 */
	public double getTotalBaggageWeight() {
		return totalBaggageWeight;
	}
	/**
	 * @param totalBaggageWeight the totalBaggageWeight to set
	 */
	public void setTotalBaggageWeight(double totalBaggageWeight) {
		this.totalBaggageWeight = totalBaggageWeight;
	}
	/**
	 * @return the totalBaggageVolume
	 */
	public String getTotalBaggageVolume() {
		return totalBaggageVolume;
	}
	/**
	 * @param totalBaggageVolume the totalBaggageVolume to set
	 */
	public void setTotalBaggageVolume(String totalBaggageVolume) {
		this.totalBaggageVolume = totalBaggageVolume;
	}
	/**
	 * @return the totalExcessFees
	 */
	public double getTotalExcessFees() {
		return totalExcessFees;
	}
	/**
	 * @param totalExcessFees the totalExcessFees to set
	 */
	public void setTotalExcessFees(double totalExcessFees) {
		this.totalExcessFees = totalExcessFees;
	}
    /**
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}
	/**
	 * @param flightCode the flightCode to set
	 */
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}
	private String flightCode;
	/**
	 * @return the actulPassengers
	 */
	public HashMap<String, passenger> getActulPassengers() {
		return actulPassengers;
	}

	/**
	 * @param actulPassengers the actulPassengers to set
	 */
	public void setActulPassengers(HashMap<String, passenger> actulPassengers) {
		this.actulPassengers = actulPassengers;
	}
    /**
	 * @return the initPassengers
	 */
	public List<passenger> getInitPassengers() {
		return initPassengers;
	}

	/**
	 * @param initPassengers the initPassengers to set
	 */
	public void setInitPassengers(List<passenger> initPassengers) {
		this.initPassengers = initPassengers;
	}
}
