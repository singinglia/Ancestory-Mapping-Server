package services;

public class UserNotFoundException extends Exception {
    UserNotFoundException(String message)
    {
        super(message);
    }

    UserNotFoundException()
    {
        super();
    }
}

