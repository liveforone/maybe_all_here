package maybe_all_here.orderservice.service.util;

public class PriceCalculator {

    public static long calculateTotalPrice(long itemPrice, long orderQuantity) {
        return itemPrice * orderQuantity;
    }

    public static long calculateDiscountedPrice(long totalPrice, long spentMileage) {
        return totalPrice - spentMileage;
    }
}
