package hu.webler.webleruserregistrationandlogin.controller.exception;

import hu.webler.webleruserregistrationandlogin.value.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;

import static hu.webler.webleruserregistrationandlogin.value.ErrorCode.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {InputMismatchException.class})
    public ResponseEntity<Object> handleInvalidInputException(InputMismatchException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE_001, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE_002, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE_003, ex.getMessage()), HttpStatus.FOUND);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE_004, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private String responseBodyWithMessage(ErrorCode code, String message) {
        return Map.of(code, message).toString();
    }
}
