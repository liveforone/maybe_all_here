package maybe_all_here.userservice.exception;

import maybe_all_here.userservice.controller.restResponse.ResponseMessage;
import maybe_all_here.userservice.controller.restResponse.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> dtoBadRequestHandle(BindingResult bindingResult) {

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> loginFailHandle() {
        return RestResponse.loginFail();
    }
}
