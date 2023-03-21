## 마일리지 서비스 소개

마일리지는 회원가입과 동시에 만들어짐 -> kafka consumer
json body : email

주문 가격의 1%만큼 마일리지에 적립한다. -> kafka consumer로 적립가격 받아서 처리.
여기서 고민 : 적립금(주문금액의 1%)을 어디서 계산할것인가?
마일리지 서비스를 따로 뺏기때문에 주문서비스에 부하를 줄일겸
마일리지 서비스에서 계산한다.
즉 마일리지 서비스는 주문 금액을 그대로 받아서 계산을 한 후에 저장한다.
request body : orderPrice, email

유저는 mypage에서 마일리지를 볼 수 있다. -> 유저에 마일리지 전달해줘야함(feign client)
pathvariable : email

주문시 마일리지를 사용할 수 있다.
-> 주문에 마일리지를 전달해줘야함(feign client)
pathvariable : email
-> 주문시 마일리지를 사용함(kafka)
request body : spentMileage, email