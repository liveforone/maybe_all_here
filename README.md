# Maybe All Here
> 오픈 마켓 서비스 플랫폼

# 1. 프로젝트 소개
### 소개
* 오픈 마켓 서비스 플랫폼 입니다.
* 셀러 회원과 일반 회원이 존재하며, 셀러는 자유롭게 상품을 등록하고
* 일반 회원은 상품을 구매하고 리뷰를 작성합니다.
* Maybe All Here 이란 프로젝트 이름은 "이 세상 모든 물건을 다 판다" 라는 의미를 가집니다.
### 기술 스택
* Framework : Spring Boot 3.0.X & Spring Cloud
* Lang : Java17
* Data : Spring Data Jpa & Query Dsl & MySql
* Security : Spring Security & Jwt
* Service Communication : Apache Kafka(Async), Open Feign Client(Sync)
* Container : Docker & Docker-compose
* Test : Junit5
* Util : Apache Commons Lang3, LomBok

# 2. 요구사항
* [코드 품질 요구사항](https://github.com/liveforone/maybe_all_here/wiki/%EC%BD%94%EB%93%9C-%ED%92%88%EC%A7%88-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD)
* [자료구조 요구사항](https://github.com/liveforone/maybe_all_here/wiki/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD)
* 서비스별 정책 요구사항은 서비스별 설계에서 확인바람.

# 3. 설계
### 수익 모델
* [수익 모델]()
### 아키텍처
* [아키텍처 설계](https://github.com/liveforone/maybe_all_here/wiki/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%84%A4%EA%B3%84)
### DB
* [DB 설계]()
### 서비스(엔티티)
* [회원 서비스](https://github.com/liveforone/maybe_all_here/wiki/%ED%9A%8C%EC%9B%90-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [마일리지 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EB%A7%88%EC%9D%BC%EB%A6%AC%EC%A7%80-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [상점 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EC%83%81%EC%A0%90-%EC%84%9C%EB%B9%84%EC%8A%A4)
* [상품 서비스](https://github.com/liveforone/maybe_all_here/wiki/%EC%83%81%ED%92%88-%EC%84%9C%EB%B9%84%EC%8A%A4)
### 서비스(데이터)간 통신
* [데이터 통신 전략](https://github.com/liveforone/maybe_all_here/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%86%B5%EC%8B%A0-%EC%A0%84%EB%9E%B5)
* [Kafka 활용 전략](https://github.com/liveforone/maybe_all_here/wiki/%EC%B9%B4%ED%94%84%EC%B9%B4-%ED%99%9C%EC%9A%A9-%EC%A0%84%EB%9E%B5)
* [Feign Client 활용 전략](https://github.com/liveforone/maybe_all_here/wiki/Feign-Client-%ED%99%9C%EC%9A%A9-%EC%A0%84%EB%9E%B5)
* [Kafka Command](https://github.com/liveforone/maybe_all_here/wiki/Kafka-Command)
* [Kafka 주요 에러 및 주의 사항]()

# 4. 프로젝트 진행
### 진행과정
* [소개와 들어가며](https://github.com/liveforone/maybe_all_here/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C%EC%99%80-%EB%93%A4%EC%96%B4%EA%B0%80%EB%A9%B0)
* [진행 시 주의점](https://github.com/liveforone/maybe_all_here/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%A7%84%ED%96%89%EC%8B%9C-%EC%A3%BC%EC%9D%98%EC%A0%90)
* [프로젝트 회고]()
### 비즈니스 문제해결
### 장애 시나리오
### 프로젝트 시 고민점
* [페이징 성능을 높일 순 없을까?](https://github.com/liveforone/maybe_all_here/wiki/%ED%8E%98%EC%9D%B4%EC%A7%95-%EC%84%B1%EB%8A%A5%EC%9D%84-%EB%86%92%EC%9D%BC-%EC%88%9C-%EC%97%86%EC%9D%84%EA%B9%8C%3F)
* [데이터 변경 요청시 항상 모든 예외상황을 처리해야할까?](https://github.com/liveforone/maybe_all_here/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B3%80%EA%B2%BD-%EC%9A%94%EC%B2%AD%EC%8B%9C-%ED%95%AD%EC%83%81-%EB%AA%A8%EB%93%A0-%EC%98%88%EC%99%B8%EC%83%81%ED%99%A9%EC%9D%84-%EC%B2%98%EB%A6%AC%ED%95%B4%EC%95%BC%ED%95%A0%EA%B9%8C%3F)

# 5. 스타일 가이드(코드 컨벤션)
* 아래는 필자가 직접 제작한 스타일 가이드이며, 본 프로젝트는 아래 스타일 가이드를 모두 지켜 제작되었다.
* [스타일 가이드 전문](https://github.com/liveforone/study/tree/main/%5B%EB%82%98%EB%A7%8C%EC%9D%98%20%EC%8A%A4%ED%83%80%EC%9D%BC%20%EA%B0%80%EC%9D%B4%EB%93%9C%5D)

## 반드시 할 것
* 카프카 상수랑 YML에서는 로컬과 컨테이너 포트번호가 다름에 주의한다.
* yml에서 db 비번 제대로 설정한다.
* record 서비스를 보면 페이징 최적화 예제가 있으니 참조한다.
* 파라미터, requestbody, pathvariable, 은 전부다 명시적으로 선언해준다.
* 명시적으로 선언해주면 하드코딩이 되므로 상수로 선언한다.
* 모든 문자열은 상수 처리한다.
* 큐클래스가 만들어지고 generated 폴더는 .gitignore에 추가한다.
* 서비스 로직과 컨트롤러를 철저히 분리한다.(dto 변환은 서비스에서 처린한다.)
* 서비스로직에 매퍼를 작성한다.
* 각 서비스 별로 리드미 만들고 저장 후 위키 작성시 copy paste 한다.
* feign client를 사용할 경우 반드시 서킷브레이커 넣는다.
* kafka producer의 경우에만 gson 넣는다.
* 1:1 인경우에는 fk(외부식별자) 역할을 하는 컬럼에 unique 걸기
* 서킷브레이커에서는 서킷브레이커가 활성화가 되면(null이면) 새 빈 객체를 new 생성자로 생성해 리턴해야한다. 그래야 mapper에서 정상적인 처리가 가능해진다.
* 시큐리티가 있는 유저 서비스에서 데이터를 가져올때에는 해당 api를 permitall로 설정해야한다.

## 카프카 로그가 미친 듯이 내려갈때
* 카프카와 주키퍼 로그가 저장된 var폴더에서 로그 기록 두 폴더를 모두 삭제하고 카프카를 재시작하면된다.

## 카프카 설정시
* serial and deserial 은 import org.apache.kafka.common.serialization.StringDeserializer; 를 사용해야 에러가 안뜬다.

## 프로젝트 후 
* 컬럼에 unique 걸려있는거 확인하고
* docker db 만들때 반드시 unique 걸기(위키에도 명시)

## 전체 설계
### 수익창출 수단
플랫폼의 수익 창출 수단은 설레의 수수료 + 광고비이다.
다만 이 부분은 구현하지 않겠다.
은행과 연동하면 수수료를 구현하는 것이 쉬우나,
은행연동, 즉 실질적인 결제부분은 포함하지 않은 프로젝트 이므로 정책상 수수료를 부과한다고만 놓고 구현은 하지 않는다.
### 정책
1. 플랫폼의 수익 창출 수단은 수수료와 광고비이다.
2. 사용자의 빠른 결제를 유도하기 위해 장바구니는 없앤다.
3. 쿠폰의 경우 이용횟수가 적고, 시스템적인 복잡성으로 인한 코드 증가 문제로 마일리지(적립금)으로 대체한다.
4. 별점 없애고 추천/비추천으로 두기
5. 카테고리없애고 검색 기능 강화(성능 면에서)
### 서비스 목록
* 유저 서비스
* 마일리지 서비스
* 상점 서비스
* 상품 서비스
* 리뷰 서비스
* 결제 서비스
* 배송 서비스(추가 고민 : 배송정보, 전화번호)

## 장애 시나리오(MSA를 하는 목적 : 장애대응!!)
### 결제시 마일리지 계산 장애
* 결제 밀린다. 유저 DB에는 많은 정보가 저장되기에 부하가 가중된다.
* 따라서 마일리지까지 넣고 계산 하고 있으면 답이 안나온다..
* 마일리지 서비스를 따로 빼서 금액에 맞추어 계산까지 하고 다시 결제창으로 돌아온다.
* 그럼 장애에서 조금 괜찮지 않을까?
### 상품 조회시 파일 부하로 인한 장애
* 파일은 로컬이던 어디던 분명 저장되어 있을 것이다.
* 하나의 상품에 파일은 여러개일 것이기에 상품과 같이 저장하기에는 무리가 있다.
* 따라서 파일만 다루는 파일 서비스를 따로 구축하고,
* 파일은 상품을 fk(외부식별자)로 가지고 있는 데이터를 파일 서비스에서 가져오는 것이 부하를 줄이는 방법일 것이다.

## 비즈니스 문제해결
### 별점 없애기
* 별점이 주는 많은 문제점때문에 대부분의 기업이 별점을 없애고
* 더 나아가 추천마저 없애는 중이다.
* 즉 비즈니스 문제라고 많은 기업에서 정의를 내렸다.
* 다만 상품에 리뷰만으로는 직관적이지 않기때문에 직관적인 평가는 반드시 필요하다.
* 따라서 별점이 아닌 추천/비추천으로 추천을 분류하면 부작용이 완전히 사라지진 않더라도 나름 두마리의 토끼를 잡을 수 있다.
### 카테고리는 필요할까?
* 카테고리는 고민을 해봐야함. 검색을 집중해서 한다면 카테고리는 사실 필요가 없음. 일반적으로도 검색을 많이 사용하고,
검색이 직관적이고, 편하고, 카테고리처럼 답이 있는 제약이 걸린게 아닌 자유로워서 검색으로 두고 카테고리 태그를 따로 안다는 것이 더 효과적일 수도 있다.

## 주문
* 주문에는 반드시 생성 날자 createdDate 들어가야함.

## 리뷰 시 추천
* 추천은 dto에서 추천/비추천을 식별할 것을 넣어서
* if문으로 추천시 추천 producer, 비추천시 비추천 producer로 보내기
* 상품 서비스에 토픽 있음. 참고

## 위키 할일 : 모든 문서는 위에 있으니 참고해 작성
* 수익창출
* 장애시나리오(위에 있는 것 참고해서 작성)
* 비즈니스 밸류 창출(장바구니 포함)
* 데이터베이스 설계
* kafka 에러및 주의사항
* 프로젝트를 마치며
* 위키 리드미에 맞게 정리