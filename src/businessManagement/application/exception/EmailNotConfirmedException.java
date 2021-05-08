package businessManagement.application.exception;

public class EmailNotConfirmedException extends  RuntimeException{
    public EmailNotConfirmedException(String message){
        super(message);
    }
}
