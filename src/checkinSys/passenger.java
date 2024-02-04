package checkinSys;

public class passenger {
	private String name;
    private String bookingReference;
    private String flightCode;
    private boolean checkedIn;
    private baggage baggage;
    
    public passenger(String name) {
    this.name = name;	
    	
    }
    
    public boolean checkInStatus() {
		return checkedIn;
        // 返回签到状态
    }

    public void updateCheckInStatus(boolean status) {
        // 更新签到状态
    }

    public void setBaggage(baggage baggage) {
        // 设置或更新行李信息
    }

	public String getBookingReference() {
		// TODO Auto-generated method stub
		return null;
	}
}
