package quixotic.projects.cookbook.exception.badRequestException;


import quixotic.projects.cookbook.exception.APIException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends APIException {
    public BadRequestException(String message) {
        super(BAD_REQUEST, message);
    }
}
