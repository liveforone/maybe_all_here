package maybe_all_here.itemservice.controller.restResponse;

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

    public static ResponseEntity<?> itemIsNull() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.NULL_ITEM.getValue());
    }

    public static ResponseEntity<?> createItemSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestMessage.CREATE_ITEM_SUCCESS.getValue());
    }

    public static ResponseEntity<?> editTitleSuccess() {
        return ResponseEntity.ok(RestMessage.EDIT_TITLE_SUCCESS.getValue());
    }
}
