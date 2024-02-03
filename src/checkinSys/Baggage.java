package checkinSys;

public class Baggage {
	private String dimensions;
    private double weight;

    public double calculateExcessFee() {
        // 计算超额行李费
    	return weight;
    }

    public boolean checkLimit(double maxWeight, String maxDimensions) {
        // 检查行李限制
    	return false;
    }
}
