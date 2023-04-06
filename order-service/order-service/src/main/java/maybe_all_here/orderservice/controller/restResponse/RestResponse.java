package maybe_all_here.orderservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponse {

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
}
