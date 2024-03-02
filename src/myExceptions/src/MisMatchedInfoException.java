// should decide what makes valid data
// (e.g., length, range, number of characters, etc.) If an error is found, just continue without
// that line of data. Provide suitable data to check that your program is working correctly, e.g.
// input files with some errors.
public class MisMatchedInfoException extends Exception{
    public MisMatchedInfoException() {
        super("Mismatched passenger information ask the passenger to enter again to Check-in");
    }
}
