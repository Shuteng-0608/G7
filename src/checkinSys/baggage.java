package checkinSys;

public class baggage {
	private String dimensions;
    private double weight;

    public baggage(String dimensions, double weight) {
        this.dimensions = dimensions;
        this.weight = weight;
    }
    
    public double calculateExcessFee() {
        // 计算超额行李费
    	return weight;
    }

    public boolean checkLimit(double maxWeight, String maxDimensions) {
        // 检查行李限制
    	return false;
    }
}
