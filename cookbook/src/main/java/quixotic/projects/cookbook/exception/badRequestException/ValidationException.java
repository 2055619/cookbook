package quixotic.projects.cookbook.exception.badRequestException;

public class ValidationException extends BadRequestException {
	public ValidationException(String message){
		super(message);
	}
}