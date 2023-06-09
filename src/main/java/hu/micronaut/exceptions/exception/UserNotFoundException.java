package hu.micronaut.exceptions.exception;

public class UserNotFoundException extends ApiException {
    private static final String CODE = "001-" + UserNotFoundException.class.getSimpleName();
    public UserNotFoundException(String msg) {
        super(CODE, msg);
    }

    public UserNotFoundException(Long id) {
        super(CODE, "User not found with id: " + id);
    }

    public UserNotFoundException() {
        super(CODE, "User not Found!");
    }

}
