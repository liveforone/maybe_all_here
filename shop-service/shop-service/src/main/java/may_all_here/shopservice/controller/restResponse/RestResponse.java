package may_all_here.shopservice.controller.restResponse;

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

    public static ResponseEntity<?> notSellerError() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseMessage.NOT_SELLER.getValue());
    }

    public static ResponseEntity<?> duplicateUser() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.DUPLICATE_USER.getValue());
    }

    public static ResponseEntity<?> createShopSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseMessage.SHOP_CREATE_SUCCESS.getValue());
    }

    public static ResponseEntity<?> shopIsNull() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.SHOP_IS_NULL.getValue());
    }
}
