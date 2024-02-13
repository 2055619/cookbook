package quixotic.projects.cookbook.validation;

import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.exception.badRequestException.ValidationException;

import java.util.regex.Pattern;
import static quixotic.projects.cookbook.validation.ValidationPattern.*;

public final class Validation{

//	Validation of exceptions

	private static void exception(String message) {
		throw new ValidationException(message);
	}

//	Validation of attributes
	public static void validatePassword(String password) {
		if(password.matches(PASSWORD_PATTERN.toString()))
			return;
		exception(ValidationMessage.PASSWORD_MESSAGE.toString());
	}

	public static void validateEmail(String email) {
		if(email.matches(EMAIL_PATTERN.toString()))
			return;
		exception(ValidationMessage.EMAIL_MESSAGE.toString());
	}

//	Validation of objects

	public static void validateSignIn(SignInDTO signInDTO) {
		validatePassword(signInDTO.getPassword());
		validateEmail(signInDTO.getEmail());
	}
}
