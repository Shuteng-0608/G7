package myExceptions;

/**
 * This class represents a custom exception type specifically for handling
 * invalid booking reference situations in a booking or check-in system.
 * It extends from the Java's built-in Exception class.
 */
public class InvalidBookRefException extends Exception {
	public InvalidBookRefException(String message) {
        super(message);
    }
}
