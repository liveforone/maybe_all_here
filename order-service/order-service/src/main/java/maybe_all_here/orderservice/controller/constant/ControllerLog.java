package maybe_all_here.orderservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    ORDER_SUCCESS("Order Success | Order Id : ");

    private final String value;
}
