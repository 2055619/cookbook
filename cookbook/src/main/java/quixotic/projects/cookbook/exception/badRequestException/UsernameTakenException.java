package quixotic.projects.cookbook.exception.badRequestException;

public class UsernameTakenException extends BadRequestException {
    public UsernameTakenException() {
        super("messages.usernameTaken");
    }
}
