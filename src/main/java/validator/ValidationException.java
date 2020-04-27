package validator;

public class ValidationException extends RuntimeException{
    /**
     * class for handling exceptions
     */
//    public ValidationException() {
//    }
    /**
     *
     * @param message the message shown
     */
    public ValidationException(String message) {
        super(message);
    }

}
