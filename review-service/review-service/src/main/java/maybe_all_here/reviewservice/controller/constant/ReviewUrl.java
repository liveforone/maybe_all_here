package maybe_all_here.reviewservice.controller.constant;

public class ReviewUrl {

    public static final String REVIEWS_BELONG_TO_ITEM = "/reviews/{itemId}";
    public static final String REVIEW_DETAIL = "/review/{reviewId}";
    public static final String CREATE_REVIEW = "/create-review/item/{itemId}/order/{orderId}";
    public static final String EDIT_REVIEW = "/edit-review/{reviewId}";
    public static final String DELETE_REVIEW = "/delete-review/{reviewId}";
}
