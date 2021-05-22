package main.Exceptions;


/**
 *
 * @author ruidu
 */
public class XYNotFoundException extends Exception {

    public XYNotFoundException() {
    }

    public XYNotFoundException(String message) {
        super(message);
    }

    public XYNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public XYNotFoundException(Throwable cause) {
        super(cause);
    }

    public XYNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}


