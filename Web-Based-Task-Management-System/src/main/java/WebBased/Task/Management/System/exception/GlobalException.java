package WebBased.Task.Management.System.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(GenericException.class)
    ResponseEntity<?> handleGenericException(GenericException genericException){
        log.error(genericException.getErrorMessage());
        return ResponseEntity.status(genericException.getStatusCode()).body(genericException.getErrorMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        log.error(usernameNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usernameNotFoundException.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,String>>handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String , String> response=new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error)->{
            String message= error.getDefaultMessage();
            String fieldError=((FieldError)error).getField();
            response.put(fieldError,message);
        });
        return new ResponseEntity<Map<String , String>>(response, HttpStatus.BAD_REQUEST);
    }
}
