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
* 주문시 상품 수량감소 : producer -> item-service
* 주문시 마일리지 increase, 마일리지 사용시 decrease : producer -> mileage-service
* 주문시 마일리지 정보 가져오기 : Feing client -> mileage-service
* 리뷰서비스에 주문정보 전달 : Provide controller -> review-service
* 주문 취소시 리뷰 삭제(email, itemId) : producer -> review-service

## 가격 정책
* totalPrice : 상품가격 * 주문 수량
* discountedPrice : totalPrice - 사용한 마일리지