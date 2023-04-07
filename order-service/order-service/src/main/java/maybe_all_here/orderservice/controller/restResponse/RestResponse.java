package maybe_all_here.orderservice.controller.restResponse;

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
                .status(HttpStatus.NOT_FOUND)
                .body(RestMessage.ITEM_IS_NULL.getValue());
    }

    public static ResponseEntity<?> itemIsSoldOut() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.ITEM_IS_SOLD_OUT.getValue());
    }

    public static ResponseEntity<?> overRemaining() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.OVER_REMAINING.getValue());
    }

    public static ResponseEntity<?> overMileage() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.OVER_MILEAGE.getValue());
    }

    public static ResponseEntity<?> orderSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestMessage.ORDER_SUCCESS.getValue());
    }

    public static ResponseEntity<?> overCancelDate() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.ORDER_IS_OVER_DATE.getValue());
    }

    public static ResponseEntity<?> isNotOwner() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.NOT_OWNER_OF_ORDER.getValue());
    }

    public static ResponseEntity<?> orderCancelSuccess() {
        return ResponseEntity.ok(RestMessage.ORDER_CANCEL_SUCCESS.getValue());
    }
}
