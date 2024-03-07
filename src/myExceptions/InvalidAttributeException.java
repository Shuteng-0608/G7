package myExceptions;

/*
 * Should decide what makes valid data (e.g., length, range, number of characters, etc.)
 * If an error is found, just continue without that line of data.
 * Provide suitable data to check that your program is working correctlye.g.
 * e.g. input files with some errors.
 */
public class InvalidAttributeException extends Exception{
    public InvalidAttributeException(String message) {
        super(message);
    }
}

