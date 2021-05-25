package main.Exceptions;

public class EdgesNotFoundException  extends Exception {

    public EdgesNotFoundException() {
    }

    public EdgesNotFoundException(String message) {
        super(message);
    }

    public EdgesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EdgesNotFoundException(Throwable cause) {
        super(cause);
    }

    public EdgesNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
