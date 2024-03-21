package quixotic.projects.cookbook.exception.forbiddenRequestExceptions;

public class InvalidJwtException extends ForbiddenRequestException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
