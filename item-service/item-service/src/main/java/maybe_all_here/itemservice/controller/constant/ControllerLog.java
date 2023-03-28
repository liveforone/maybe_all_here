package maybe_all_here.itemservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_ITEM_SUCCESS("Create Item Success || Item id : "),
    EDIT_TITLE_SUCCESS("Edit Item title success || Item id : "),
    EDIT_FILE_SUCCESS("Edit Item file success || Item id : "),
    EDIT_CONTENT_SUCCESS("Edit Item content success || Item id : "),
    EDIT_PRICE_SUCCESS("Edit Item price success || Item id : "),
    EDIT_REMAINING_SUCCESS("Edit Item remaining success || Item id : "),
    DELETE_ITEM_SUCCESS("Delete Item success || Item id : ");

    private final String value;
}
