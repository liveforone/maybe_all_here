## 상품 서비스 소개
* 상품 서비스는 상품의 재고부터 가격 등 상품 정보를 주관하는 서비스입니다.
* 상점, 리뷰, 주문 등의 서비스와 관계가 있다.
* 상품에서 상품에 담긴 파일까지 관할하므로 상품 서비스는 상품테이블과 파일 테이블, 즉 두개의 테이블이 있다.

## 상세 요구사항
* 상품의 외부 식별자는 shopId이다.
* 상점 접근 후에 상품에 접근할때에는 email이 아닌 shopId로 모든 api가 접근한다.
* 셀러 회원 전용 상점 상품 리스트로부터 접근하면 사실상 모든 권한을 획득한것이고, 이에따라 crud 모든 작업이 가능하다.
* 상품 홈은 최신순 + 추천순으로 정렬된다.
* 이외의 상품 리스트는 추천순 + 최신순으로 정렬된다.
* 카테고리를 넣지 않은 대신 검색 기능을 강화한다.
* 상품의 추천/비추천은 리뷰 수정시에도 수정 불가능하다.(영구)
* 파일데이터는 상품DB에 파일 테이블을 만들어서 저장한다.
* 파일 수정은 기존 파일을 삭제하고 새로 파일을 삽입하는 방식으로 한다.
* 상품 삭제시 파일 + 리뷰 모두 삭제된다.
* 주문 취소시 상품의 재고를 주문 수량만큼 원상복구 한다.

## API 설계
```
[GET] /item-home : 상품 홈, 전체 상품이 최신순 + 추천순으로 나열된다.
[GET] /item/{itemId} : 상품 상세 페이지
[POST] /create-item/{shopId} : 상품 등록 페이지
[GET] /items/{shopId} : 상점 상품 리스트, 일반 회원 전용
[GET] /items/seller/{shopId} : 상점 상품 리스트, 셀러 회원(주인) 전용
[GET] /items/search : 상품 검색, 추천순으로 나열된다.
[PATCH] /item/update-title/{itemId} : 상품 제목 수정
[PATCH] /item/update-file/{itemId} : 상품 파일 수정
[PATCH] /item/update-content/{itemId} : 상품 소개(내용) 수정
[PATCH] /item/update-price/{itemId} : 상품 가격 수정
[PATCH] /item/update-remaining/{itemId} : 상품 재고 추가
[DELETE] /item/delete/{itemId} : 상품 삭제 -> 연관된 리뷰와 파일도 같이 삭제된다.
[GET] /file/{saveFileName} : 파일 조회, 뷰에서 이미지 보여줄때 사용
[POST] /item-provide/{itemId} : 상품 수량 제공 api
```
## Json body 예시
```
[상품 등록]
{
  "title": "test_title",
  "content": "test_content",
  "price": 15000,
  "remaining": 150
}
```

## 서비스간 통신
### 주문(결제)시 수량 감소
* 주문시 수량을 감소한다.
```
request dto : ItemRemainingRequest
topic : decrease-remaining
```
### 리뷰 등록시 추천/비추천 업데이트
```
request : itemId
topic : item-is-good, item-is-bad
```
### 주문 취소시 재고 수량 원복
* 주문한 수량만큼 재고 수량을 원상복구 한다.
```
request dto : ItemRemainingRequest
topic : rollback-remaining
```
### 상품 삭제시 연관 리뷰 삭제
* 상품 삭제시 연관된 리뷰를 벌크로 삭제한다.
```
request : itemId
topic : remove-review-belong-item
```

## 파일을 따로 서비스로 빼지 않은이유
* 처음에는 파일 서비스를 따로 빼서 단일 서비스화 하려했다.
* 그 안에서 파일 이미지 보여주고, 파일 로컬에 저장하고 삭제하는 일을 하려했다.
* 그렇지 않고 상품 서비스에서 상품의 이미지까지 관할하게된 이유는
* gson 등을 활용해 직렬화해서 다른 서비스로 보낼때 multipartfile은 불가능하다.
* 이러한 이유로 파일을 입력받는 서비스에서 처리하기로 하였다.

## 상품의 추천/비추천을 리뷰에서 관할하지 않는 이유
* 리뷰에 추천/비추천을 놓고 상품에서 리뷰에서 해당 정보를 가져와 사용하는 방식을 처음에 생각했다.
* 그러나 추천수/비추천수를 집계하여 다른 서비스로 끌고오기에는
* 상품 리스트 같이 10개, 15개 등 여러 데이터가 있는 경우에 너무 복잡한일이 생기고, 쿼리때문에 성능도 저하될것 같아서
* 상품의 추천/비추천은 상품에서 관할하도록 하였다.