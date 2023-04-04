## 주문 서비스 소개
## 상세 요구사항
## API 설계
## Json body 예시
## 서비스간 통신


* 주문 서비스 제작전 모든 서비스를 훑어서 서비스간 통신 정리 먼저 하기
* 주문시 상품 수량감소 : producer
* 리뷰서비스에 itemId와 email로 주문 찾아서 리턴하기(쿼리스트링으로 받아야함) : provide controller
* 주문에는 반드시 생성 날자 createdDate 들어가야함.
* 주문 취소시 리뷰 삭제(email, itemId) : producer
* 주문시 마일리지 increase하고, 사용시 decrease 해야함 : producer
* 주문시 마일리지 정보 가져와야함 : Feing client
* 리뷰서비스에 주문정보 넘겨야함(쿼리스트링 정상작동 확인하기) : Provide controller

feign 다 만들면 참조 복사해서 로그 레벨 설정하기