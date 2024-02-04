package checkinSys;

public class invalidBookingException extends Exception {
    public invalidBookingException(String message) {
        super(message);
    }
}