package quixotic.projects.cookbook.validation;

import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.exception.badRequestException.ValidationException;

import static quixotic.projects.cookbook.validation.ValidationPattern.*;

public final class Validation{

//	Validation of exceptions

	private static void exception(String message) {
		throw new ValidationException(message);
	}

//	Validation of objects

	public static void validateSignIn(SignUpDTO signInDTO) {
		validatePassword(signInDTO.getPassword());
		validateEmail(signInDTO.getEmail());
		validateUsername(signInDTO.getUsername());
		validateName(signInDTO.getFirstName());
		validateName(signInDTO.getLastName());
	}

//	Validation of attributes
	private static void validatePassword(String password) {
		if(password.matches(PASSWORD_PATTERN.toString()))
			return;
		exception(ValidationMessage.PASSWORD_MESSAGE.toString());
	}

	private static void validateEmail(String email) {
		if(email.matches(EMAIL_PATTERN.toString()))
			return;
		exception(ValidationMessage.EMAIL_MESSAGE.toString());
	}

	private static void validateUsername(String username) {
		if(username.matches(USERNAME_PATTERN.toString()))
			return;
		exception(ValidationMessage.USERNAME_MESSAGE.toString());
	}

	private static void validateName(String name) {
		if(name.matches(NAME_PATTERN.toString()))
			return;
		exception(ValidationMessage.NAME_MESSAGE.toString());
	}
}
