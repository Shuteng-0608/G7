package myExceptions;
/*
 * Illegal range of data entered by the passenger
 */
public class UnexpectedDataRangeException extends Exception{
    public UnexpectedDataRangeException() {
        super("Unexpected data range! Ask the passenger enter their information again");
    }
}
