# Maybe All Here
> 오픈 마켓 서비스 플랫폼

전체 요구사항을 생각해보고,
서비스를 나눈후에(도메인 분리),
서비스들간의 통신을 생각해보고,
각 서비스별로 기능을 생각해보고, : 각 서비스 위키에서
어떤 비즈니스 문제가 존재하는지 생각해본다. : 각 서비스 위키에서

리빌드, 마이그레이션 등은 기본적으로 
'일' 이 되어버린다.
주로 copy and paste를 많이 하는 일을 하면 할수록 지루해지고
흥미를 못느낀다.
따라서 일 이라고 생각하지 않도록 복붙을 줄이고 하나의 서비스에 집중해야한다.
편한 마음으로 하자. 위키니 뭐니 문서화니 뭐니 등 많은 것을 머릿속에 넣고 제작하면
힘들다.

1. 설계하기, 이 설계에는 서비스끼리의 통신도 들어가야한다.
2. 각 서비스 별로 문서 만들기
3. 서비스를 만들땐 서비스전용 문서를 보고, 오로지 그 한 서비스만 생각하기
4. 향 후 문서화시 서비스 문서를 보고 작성하며, git에 올리지 않는다. 
프로젝트 완료 후에는 서비스 문서는 모두 폐기(삭제)

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
### 서비스 목록
* 유저 서비스
* 마일리지 서비스(mileage, fk는 email)
* 상점 서비스
* 상품 서비스
* 파일 서비스
* 결제 서비스
* 리뷰 서비스
* 추천 서비스
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
### 리뷰에 추천정보를 저장할때 발생하는 장애
* 별점이 주는 많은 문제점때문에 대부분의 기업이 별점을 없애고
* 더 나아가 추천마저 없애는 중이다.
* 즉 비즈니스 문제라고 많은 기업에서 정의를 내렸다.
* 다만 상품에 리뷰만으로는 직관적이지 않기때문에 직관적인 평가는 반드시 필요하다.
* 따라서 별점이 아닌 추천/비추천으로 추천을 분류하면 부작용이 완전히 사라지진 않더라도 나름 두마리의 토끼를 잡을 수 있다.
* 리뷰 서비스에서 추천정보를 가지고 있게되면,
* 상품 상세조회가 아닌 페이징에서 추천정보를 리뷰서비스에서 가져오고,
* 상품 상세조회에서 또 가져오게 된다.
* 이러한 방법은 리뷰서비스에 리뷰 이외의 부하를 가중시키는 일이 되어버린다.
* 따라서 추천을 추천서비스로 따로 분류하여 추천이 필요할때 바로바로 가져올 수 있도록 한다.