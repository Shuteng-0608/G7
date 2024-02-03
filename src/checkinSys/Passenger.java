package checkinSys;

public class Passenger {
	private String name;
    private String bookingReference;
    private String flightCode;
    private boolean checkedIn;
    private Baggage baggage;

    public boolean checkInStatus() {
		return checkedIn;
        // 返回签到状态
    }

    public void updateCheckInStatus(boolean status) {
        // 更新签到状态
    }

    public void setBaggage(Baggage baggage) {
        // 设置或更新行李信息
    }
}
