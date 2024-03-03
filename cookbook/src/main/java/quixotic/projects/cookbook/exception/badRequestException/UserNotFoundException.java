package quixotic.projects.cookbook.exception.badRequestException;

import quixotic.projects.cookbook.exception.badRequestException.BadRequestException;

public class UserNotFoundException extends BadRequestException {
    public UserNotFoundException() {
        super("UserNotFoundException");
    }
}
