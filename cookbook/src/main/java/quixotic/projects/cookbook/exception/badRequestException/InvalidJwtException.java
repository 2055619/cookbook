package quixotic.projects.cookbook.exception.badRequestException;

public class InvalidJwtException extends BadRequestException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
