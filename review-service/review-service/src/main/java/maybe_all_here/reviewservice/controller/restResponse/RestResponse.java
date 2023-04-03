package maybe_all_here.reviewservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Objects;

public class RestResponse {

    public static ResponseEntity<?> validError(BindingResult bindingResult) {
        String errorMessage = Objects
                .requireNonNull(bindingResult.getFieldError())
                .getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    public static ResponseEntity<?> orderIsNull() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(RestMessage.NULL_ORDER.getValue());
    }

    public static ResponseEntity<?> orderCanceled() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.CANCEL_ORDER.getValue());
    }

    public static ResponseEntity<?> createReviewSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestMessage.CREATE_REVIEW_SUCCESS.getValue());
    }

    public static ResponseEntity<?> reviewIsNull() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(RestMessage.REVIEW_IS_NULL.getValue());
    }

    public static ResponseEntity<?> notOwner() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.NOT_OWNER.getValue());
    }

    public static ResponseEntity<?> editSuccess() {
        return ResponseEntity.ok(RestMessage.EDIT_REVIEW_SUCCESS.getValue());
    }

    public static ResponseEntity<?> deleteSuccess() {
        return ResponseEntity.ok(RestMessage.DELETE_REVIEW_SUCCESS.getValue());
    }
}
