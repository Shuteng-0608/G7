public class MissingInputAttributeException extends Exception{
    public MissingInputAttributeException() {
        super("Invalid input data found, continue without this line of data");
    }
}
