package services;

public class AuthorizationException extends Exception {
    AuthorizationException(String message)
    {
        super(message);
    }

    AuthorizationException()
    {
        super();
    }
}
