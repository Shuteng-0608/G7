package myExceptions;
/*
 * Illegal type of data entered by the passenger
 */
public class UnexpectedDataTypeException extends Exception{
    public UnexpectedDataTypeException() {
        super("Unexpected DataType! Ask the passenger to enter their information again");
    }
}
