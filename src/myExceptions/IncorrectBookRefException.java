package myExceptions;

public class IncorrectBookRefException extends Exception{

    public IncorrectBookRefException() {
        super("The generated Booking-Reference is INCORRECT! Check the rule of Booking-Ref Generation!");
    }
}
