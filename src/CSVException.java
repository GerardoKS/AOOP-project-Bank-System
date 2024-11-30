package src;

public class CSVException extends Exception {
    public CSVException(String message, Throwable exception) {
        super(message,exception);
    }
}