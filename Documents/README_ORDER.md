## 주문 서비스 소개
* 주문 서비스는 주문내역과 주문, 주문 취소를 관할하는 서비스 입니다.
* 마일리지, 상품, 리뷰 서비스에 영향을 끼치고, 받는 역할을 합니다.
* 존재하는 마이크로 서비스 중에 가장 많은 타 서비스와 메세지를 주고 받는 서비스 입니다.

## 상세 요구사항
* 주문시에는 총 주문 가격과 할인 후 주문 가격 두가지 종류의 주문 가격이 저장되어야한다. 이는 아래 가격 정책에 상세히 나와 있다.
* 주문 시에는 validator를 이용해 상품이 품절되었는지,
* 주문 수량이 상품의 재고 수량을 초과하는지 확인해야한다.
* 또한 사용하려는 마일리지가 보유 마일리지를 초과하는지 확인해야한다.
* 서비스 로직에서는 입력받은 request dto를 바탕으로 db에 insert할 최종 dto를 mapper를 통해서 만들어야한다.
* 이때 최종 결제금액과, 할인 후 금액, 사용한 마일리지등을 setting 해주어야한다.
* 주문 취소는 7일이내 가능하며, 7일 이후에는 취소가 불가능하다.
* 주문 취소와 동시에 주문에 속한 리뷰도 삭제해야한다.
* 주문이 취소되면 상품의 재고는 다시 원상 복구 된다. 주문한 수량만큼 다시 복구한다.
* 주문이 취소되면 사용한 마일리지는 추가해주고, 적립된 마일리지는 빼준다.

## API 설계
```
[GET] /orders : 나의 주문 리스트, jwt토큰의 유저 정보를 바탕으로 리턴한다.
[GET] /order/detail/{orderId} : 주문 상세 페이지
[GET/POST] /order/{itemId} : 주문, OrderRequest dto 필요
[PATCH] /order/cancel/{orderId} : 주문 취소, 주문 취소 정책에 의거해 작동
[GET] /order-info/{orderId} : 리뷰 작성시 주문 상세 provide api
```

## Json body 예시
```
[주문]
{
    "orderQuantity" : 1,
    "spentMileage" : 3000
}
```

## 서비스간 통신
### 주문시 상품 정보 가져오기
* Feing client -> item-service
```
url : /item-provide/{itemId}
response dto : ItemProvideResponse
```
### 주문시 상품 재고 수량 감소 - producer
* 주문한 수량만큼 상품 재고 수량을 감소한다.
```
request dto : ItemRemainingRequest
topic : decrease-remaining
```
### 주문시 마일리지 적립 - producer
* 주문한 총 금액을 보내어 마일리지를 적립한다.
```
request dto : AccumulateRequest
topic : increase-mileage
```
### 마일리지 사용시 감소 - producer
* 마일리지를 사용하면 감소시키고, 사용하지 않을경우 호출하지 않는다.
```
request dto : UsingMileageRequest
topic : decrease-mileage
```
### 주문시 마일리지 정보 가져오기
* Feing client -> mileage-service
```
url : /mileage-info/{email}
response dto : MileageResponse
```
### 리뷰서비스에 주문정보 전달
* Provide controller -> review-service
```
url : /order-info/{orderId}
response dto : OrderProvideResponse
```
## 주문 취소시 리뷰 삭제 - producer
* 주문 취소시 연관된 리뷰를 삭제한다.
```
request : orderId
topic : remove-review-belong-order
```
### 주문 취소시 마일리지 롤백
* 주문 취소시 사용한 마일리지는 다시 복구 시키고(플러스),
* 적립된 마일리지는 마이너스 한다.
```
request dto : RollbackMileageRequest
topic : rollback-mileage
```
### 주문 취소시 재고 롤백
* 주문을 취소하면 상품 주문 수량만큼 재고를 롤백(플러스)한다.
```
request dto : ItemRemainingRequest
topic : rollback-remaining
```

## 가격 정책
* totalPrice : 상품가격 * 주문 수량
* discountedPrice : totalPrice - 사용한 마일리지

## 주문 취소 가능 날짜 체크 로직
* 주문날짜는 localDate를 사용했다.
* 주문 취소가능 날짜를 뽑을때에는 조심해야하는 것이 있는데,
* localDate의 getDayOfYear() 메서드에 7을 더하게되면 원하는 주문 취소 마지막 날짜를 구할 수 있다.
* 그런데 이 getDayOfYear() 메서드는 1년이 지나면 초기화 된다.
* 따라서 1월1일을 기준으로 7일 전 날까지는 위의 방법을 사용하면 오류가 발생하게된다.
* 즉, 12월 25일부터 31일 까지의 날짜들은 모두 7을 더하면 365라는 숫자를 넘어가버려서 비교가 불가능해진다.
* 이들은 모두 swtich문을 사용해서 12월 25일은 마감 날짜가 1월 1일, 31일은 1월 7일로 하여 하나씩 값을 넣어주어야 한다.
* 코드는 아래와 같다.
```
public boolean isOverCancelLimitDate(Long orderId) {
    Orders orders = orderRepository.findOneById(orderId);

    LocalDate createdDate = orders.getCreatedDate();
    int orderDate = createdDate.getDayOfYear();

    int nowYear = LocalDate.now().getYear();
    int nowDate = LocalDate.now().getDayOfYear();

    int cancelLimitDate = switch (orderDate) {
        case 359 -> LocalDate.of(nowYear, 1, 1).getDayOfYear();
        case 360 -> LocalDate.of(nowYear, 1, 2).getDayOfYear();
        case 361 -> LocalDate.of(nowYear, 1, 3).getDayOfYear();
        case 362 -> LocalDate.of(nowYear, 1, 4).getDayOfYear();
        case 363 -> LocalDate.of(nowYear, 1, 5).getDayOfYear();
        case 364 -> LocalDate.of(nowYear, 1, 6).getDayOfYear();
        case 365 -> LocalDate.of(nowYear, 1, 7).getDayOfYear();
        default -> orderDate + 7;
    };

    return nowDate > cancelLimitDate;
}
```