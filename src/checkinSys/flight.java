package checkinSys;
import java.util.List;

public class flight {
	private String destinationAirport;
    private String carrier;
    private int capacity;
    private double maxBaggageWeight;
    private String maxBaggageVolume;
    private List<passenger> checkedInPassengers;
    private double totalBaggageWeight;
    private String totalBaggageVolume;
    private double totalExcessFees;
    
    public flight(String destinationAirport) {
    	this.destinationAirport  = destinationAirport;
    	
    }
    public void addPassenger(passenger passenger) {
        // 添加乘客
    }

    public boolean checkCapacity() {
		return false;
        // 检查航班容量
    }

    public void updateBaggageStats() {
        // 更新行李统计
    }

    public String generateReport() {
        // 生成报告
		return carrier;
    }

	public String getFlightCode() {
		// 获取编码
		return null;
	}
}
