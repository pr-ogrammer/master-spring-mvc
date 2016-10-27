package pl.prutkowski.master.spring.mvc.error;

/**
 * Created by programmer on 10/27/16.
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
