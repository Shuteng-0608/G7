/*
 * Retrieve the passenger information does not match
 * Ask the passenger to check personal information and then re-Checkin
 */
public class MisMatchedInfoException extends Exception{
    public MisMatchedInfoException() {
        super("Mismatched passenger information ask the passenger to enter again to Check-in");
    }
}
