package quixotic.projects.cookbook.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import quixotic.projects.cookbook.exception.APIException;
import quixotic.projects.cookbook.model.ErrorResponce;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestControllerAdvice
public class CookbookExceptionHandler {
    private final HttpServletRequest request;

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponce> handleAPIException(APIException ex) {
        ErrorResponce response = ErrorResponce.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponce> handleOtherException(Exception ex) {
        ErrorResponce response = ErrorResponce.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(673).body(response);
    }

}
