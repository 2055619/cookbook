package quixotic.projects.cookbook.exception.goneRequestException;


import quixotic.projects.cookbook.exception.APIException;

import static org.springframework.http.HttpStatus.GONE;

public class GoneRequestException extends APIException {
    public GoneRequestException(String message) {
        super(GONE, message);
    }
}
