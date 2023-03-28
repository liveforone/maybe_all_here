package maybe_all_here.itemservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_ITEM_SUCCESS("Create Item Success || Item id : ");

    private final String value;
}
