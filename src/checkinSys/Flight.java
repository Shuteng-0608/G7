package checkinSys;
import java.util.List;

public class Flight {
	private String destinationAirport;
    private String carrier;
    private int capacity;
    private double maxBaggageWeight;
    private String maxBaggageVolume;
    private List<Passenger> checkedInPassengers;
    private double totalBaggageWeight;
    private String totalBaggageVolume;
    private double totalExcessFees;

    public void addPassenger(Passenger passenger) {
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
}
