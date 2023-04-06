package maybe_all_here.orderservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> itemIsSoldOut() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.ITEM_IS_SOLD_OUT.getValue());
    }
}
