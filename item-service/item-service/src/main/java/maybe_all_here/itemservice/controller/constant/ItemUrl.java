package maybe_all_here.itemservice.controller.constant;

public class ItemUrl {

    public static final String ITEM_HOME = "/item-home";
    public static final String ITEM_DETAIL = "/item/{itemId}";
    public static final String CREATE_ITEM = "/create-item/{shopId}";
    public static final String SHOP_ITEMS_LIST = "/items/{shopId}";
    public static final String SELLER_ITEMS_LIST = "/items/seller/{shopId}";
    public static final String SEARCH_ITEMS_LIST = "/items/search";
    public static final String EDIT_TITLE = "/item/update-title/{itemId}";
    public static final String EDIT_FILE = "/item/update-file/{itemId}";
    public static final String EDIT_CONTENT = "/item/update-content/{itemId}";
    public static final String EDIT_PRICE = "/item/update-price/{itemId}";
    public static final String EDIT_REMAINING = "/item/update-remaining/{itemId}";
    public static final String DELETE_ITEM = "/item/delete/{itemId}";
}
