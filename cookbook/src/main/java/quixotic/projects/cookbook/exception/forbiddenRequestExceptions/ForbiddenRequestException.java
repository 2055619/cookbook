package quixotic.projects.cookbook.exception.forbiddenRequestExceptions;

import quixotic.projects.cookbook.exception.APIException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenRequestException extends APIException {
    public ForbiddenRequestException(String message) {
        super(FORBIDDEN, message);
    }
}
