## 리뷰 서비스 소개
* 리뷰 서비스는 회원이 상품에 대해 작성하는 리뷰 전반을 담당합니다.
* 단순히 리뷰를 작성하고 수정/삭제 하는 것 뿐만 아니라
* 상품의 추천/비추천까지 다루고 있습니다.

## 상세 요구사항
* 리뷰를 등록하려면 해당 상품을 반드시 주문(결제)를 하여야한다.
* 추천/비추천 정보를 바탕으로 producer를 통해 상품 서비스에 메세지를 전달한다.
* 상품 삭제시 상품에 속한 모든 리뷰를 삭제(벌크)한다.

## API 설계
```
[GET] /reviews/{itemId} : 상품 전체 리뷰
[GET] /review/{reviewId} : 리뷰 상세 조회
[POST] /create-review/item/{itemId}/order/{orderId} : 리뷰 등록
[PATCH] /edit-review/{reviewId} : 리뷰 수정
[DELETE] /delete-review/{reviewId} : 리뷰 삭제
```

## Json body 예시
```
[생성]
{
  "content": "test_content",
  "recommend": "true"
}

[수정]
{
    "content": "updated_content"
}
```

## 서비스간 통신
* Feign client : 주문 서비스에 주문 했는지 확인 요청
* kafka producer : item-service에 추천/비추천
* kafka consumer : 상품 삭제시 상품에 속한 리뷰 전체 삭제 요청
* kafka consumer : 주문 취소시 리뷰 삭제

## 추천/비추천 처리 방법
* dto에서 recommand에 추천인지 비추천인지를 담어서 오면,
* if문으로 판별해서 추천은 추천 producer를,
* 비추천은 비추천 producer를 호출해 카프카 메세지를 전달한다.

## 추천/비추천 표기법
* 상품 DB에서 상품의 추천/비추천을 표기하는 방법은 수이다.
* good/bad로 표현하며 long타입의 자료구조로 추천과 비추천의 개수를 표기한다.
* 이와 달리 리뷰에서는 true와 false라는 문자열로 표현한다.
* recommend라는 컬럼에 true/false로 표기한다.
* 수를 집계해야하는 상품과 달리, 리뷰에서는 해당 리뷰가 추천인지 비추천인지만 표현하면 되기 때문이다.