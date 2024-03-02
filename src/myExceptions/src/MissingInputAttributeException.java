/*
 * should decide what makes valid data
 * (e.g., length, range, number of characters, etc.) If an error is found, just continue without
 * that line of data. Provide suitable data to check that your program is working correctly, e.g.
 * input files with some errors.
 */
public class MissingInputAttributeException extends Exception{
    public MissingInputAttributeException() {
        super("Invalid input data found, continue without this line of data");
    }
}
