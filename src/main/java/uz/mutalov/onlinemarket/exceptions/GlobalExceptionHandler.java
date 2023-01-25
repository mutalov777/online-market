package uz.mutalov.onlinemarket.exceptions;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.mutalov.onlinemarket.response.AppErrorDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<DataDTO<AppErrorDTO>> handleNotFound(NotFoundException e, WebRequest webRequest) {
        return new ResponseEntity<>(new DataDTO<>(
                new AppErrorDTO(HttpStatus.NOT_FOUND, e.getMessage(), webRequest)));
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<DataDTO<AppErrorDTO>> handleBadRequest(BadRequestException e, WebRequest webRequest) {
        return new ResponseEntity<>(new DataDTO<>(
                new AppErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage(), webRequest)));
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<DataDTO<AppErrorDTO>> handleBadCredentials(BadCredentialsException e, WebRequest webRequest) {
        return new ResponseEntity<>(new DataDTO<>(
                new AppErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage(), webRequest)));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<DataDTO<AppErrorDTO>> handleRuntime(RuntimeException e, WebRequest webRequest) {
        return new ResponseEntity<>
                (new DataDTO<>(
                        new AppErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage(), webRequest)));
    }

    @ExceptionHandler(value = {CustomSQLException.class})
    public ResponseEntity<DataDTO<AppErrorDTO>> handleCustomSQL(CustomSQLException e, WebRequest webRequest) {
        return new ResponseEntity<>(new DataDTO<>(
                new AppErrorDTO(HttpStatus.CONFLICT, e.getMessage(), webRequest)));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<DataDTO<AppErrorDTO>> handleTypeMismatchException(TypeMismatchException e, WebRequest webRequest) {
        return new ResponseEntity<>(new DataDTO<>(
                new AppErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage(), webRequest)));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<DataDTO<AppErrorDTO>> handleWebExchangeBindException(WebExchangeBindException e, WebRequest webRequest) {
        return new ResponseEntity<>(new DataDTO<>(
                new AppErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage(), webRequest)));

    }
}
