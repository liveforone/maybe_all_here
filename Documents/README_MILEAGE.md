## 마일리지 서비스 소개
* 마일리지 서비스는 회원의 마일리지를 관리하는 서비스 입니다.
* 적립급 계산하여 적립을 하거나, 사용하는 등의 일을 하는 서비스 입니다.
* 유저 서비스와 분리함으로써 유저 서비스의 부하를 줄일 수 있습니다.

## 상세 요구사항
* 회원가입과 동시에 마일리지도 생성되어야한다.
* 외부식별자는 unique로 설정한다. 이유는 1대1로 관계가 맺어지기 때문이다.
* 마이페이지와 주문시 마일리지 조회 api를 제공한다.
* 적립금 계산은 마일리지 서비스에서 한다.
* 적립금은 주문금액의 1%이다.
* 마일리지 적립과 사용은 모두 kafka를 사용한다.
* 주문 취소시 사용한 마일리지 만큼 다시 더하여주고, 총 주문 금액으로 적립된 마일리지를 계산하여 현재 마일리지에서 마이너스 해준다.

## 서비스 간 통신
### 마일리지 생성
* 마일리지는 모든 회원이 기본으로 가지고 있다.
* 즉, 마일리지는 회원가입과 동시에 만들어짐
* kafka consumer로 명령을 받아 처리한다.
```
request json : email
topic : create-mileage
```
### my mileage
* 유저는 mypage에서 마일리지를 볼 수 있다.
* 유저 서비스에에 마일리지 전달해줘야함
* feign client로 유저 서비스는 전달 받는다.
```
[GET] /my-mileage/{email}
pathvariable : email
```
### 적립
* 주문시에 주문 가격의 1%만큼 마일리지에 적립한다.
* kafka consumer로 주문가격 받아서 처리한다.
* 주문가격에서 적립급을 계산하여 update는 모두 마일리지 서비스에서 진행한다.
```
request dto : AccumulateRequest
topic : increase-mileage
```
### 주문 시 마일리지 조회
* 주문시 마일리지를 사용할 수 있다.
* 주문시에 마일리지 정보를 전달해줘야함
* feign client로 주문 서비스는 전달 받는다.
```
[POST] /mileage-info/{email}
pathvariable : email
```
### 주문 시 마일리지 사용
* 주문시 마일리지를 사용할 수 있다.
* kafka consumer로 사용한 마일리지를 받아서 처리한다.
```
request dto : UsingMileageRequest
topic : decrease-mileage
```
### 주문 취소시 마일리지 롤백
* 사용한 마일리지 만큼 마일리지를 플러스 한다.
* 적립된 마일리지를 총 주문 금액을 이용하여 구한 후 마이너스 한다.
```
request dto : RollbackMileageRequest
topic : rollback-mileage
```