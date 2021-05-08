package businessManagement.application.exception;

public class EmailOrPasswordIncorrectException extends RuntimeException{
    public EmailOrPasswordIncorrectException(String message){
        super(message);
    }
}
