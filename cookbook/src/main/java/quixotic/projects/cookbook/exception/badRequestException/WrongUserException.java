package quixotic.projects.cookbook.exception.badRequestException;

public class WrongUserException extends BadRequestException{
    public WrongUserException() {
        super("message.wrongUser");
    }
}
