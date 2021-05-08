package businessManagement.application.exception;

public class UserCannotAddToFIleException extends RuntimeException{
    public UserCannotAddToFIleException(String message){
        super(message);
    }
}
