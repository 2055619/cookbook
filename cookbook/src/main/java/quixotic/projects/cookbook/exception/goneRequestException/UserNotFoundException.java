package quixotic.projects.cookbook.exception.goneRequestException;

public class UserNotFoundException extends GoneRequestException{
    public UserNotFoundException() {
        super("UserNotFoundException");
    }
}
