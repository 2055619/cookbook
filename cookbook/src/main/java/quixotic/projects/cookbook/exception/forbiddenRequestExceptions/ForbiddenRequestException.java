package quixotic.projects.cookbook.exception.forbiddenRequestExceptions;

import org.springframework.http.HttpStatus;
import quixotic.projects.cookbook.exception.APIException;

public class ForbiddenRequestException extends APIException {
    public ForbiddenRequestException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
