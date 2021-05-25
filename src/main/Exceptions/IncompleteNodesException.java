package main.Exceptions;

public class IncompleteNodesException extends Exception {

    public IncompleteNodesException() {
    }

    public IncompleteNodesException(String message) {
        super(message);
    }

    public IncompleteNodesException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompleteNodesException(Throwable cause) {
        super(cause);
    }

    public IncompleteNodesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
