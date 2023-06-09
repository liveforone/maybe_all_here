## 아키텍처
* 프로젝트의 아키텍처는 마이크로 서비스 아키텍처(MSA)입니다.
* 서비스 별로 독립 DB를 두고, 중앙 DB가 존재하지 않는다.
* 타 서비스에서 너무 많은 데이터 참조 요청을 할 경우에는 Read Only DB를 두어서 부하를 줄인다.
* 대부분의 DB는 하나의 테이블이고, 같은 서비스에 속한다면 하나 이상의 테이블을 두어도 상관없다.
* 관심사(하는 일)에 따라 서비스를 분리하고, DB를 분리하는 약속을 잘 지켜야한다.

## 아키텍처 채택 이유
* 항상 기술을 도입할 때에는 이유가 있어야합니다.
* "남들이 해서", "트렌드라서" 가 아니라 분명한 이유가 있어야합니다.
* 필자는 아래와 같은 이유로 마이크로 서비스를 프로젝트에 도입하였다.
#### 모놀리틱 아키텍처의 단점
* 아무리 각 디렉토리별로 서비스를 나눴다고 한들, db는 하나를 사용한다.
* 물론 하나의 db를 사용함으로써 오는 장점도 어마어마하다.
* 조인과 같은것이 대표적이겠다.
* 그러나 불필요하게 서비스들 간의 결합이 너무나도 강했다.
* db도 함께쓰고 하나의 서버에 모든 파일이 다 담겨있는 등 
* 장애처리나, 강한 결합 때문에 찜찜한 점들이 많았다.
* 또한 단일 서버가 너무 큰 책임을 가지고 있고, 너무 많은 기능을 가지고 있었다.
* 결국 각 서비스별로 물리적/소프트웨어적으로 분류하는 작업을 하게되며 프로젝트의 아키텍처는 msa를 채택하게 되었다.

## MSA의 장점
* 필자가 생각하는 msa의 장점은 낮은 결합도와 장애처리의 뛰어남, 그리고 높은 자유도, 유지보수 용이점, 관심사 집중이 있다..
### 장점1 : 낮은 결합도
* 모놀리틱에서는 하나의 서버가 모든일을 하다보니 부하가 심했다.
* 각 서비스별로 분산을 하다보니 부하도 분산이 된다.
* 특히 게이트웨이라는 혁신적인 방법은 부하를 분산시키는 일등 공신이다.
### 장점2 : 장애처리
* 위의 낮은 결합도로 인해 장애처리에서는 뛰어난 모습을 보여준다.
* 회원 서비스 서버가 죽었다고 상품서비스도 같이 죽지 않습니다.
* 회원 서비스를 쓰는 이용자는 불편하겠지만, 상품서비스를 쓰는 이용자는 정상적으로 이용 가능합니다.
### 장점3 : 높은 자유도
* 모든 아키텍처가 그러하듯 정답이 없다는 것이 큰 장점이다.
* "이건 이렇게 해야해" 라는게 없이 상황에 맞게, 환경에 맞게 유연하게 변경 가능하다는 것은 msa의 큰 장점이다.
* 예시로 db를 서비스와 직접 연결하다가 메세지 중심으로 변경해 
* 메세지 큐에 모든 crud를 부담시키기도 하고,
* api 호출방식으로 필요한 데이터를 가져오다가
* read-only db를 놓고 조회쿼리만 날리기도 하는 등
* 서비스의 규모, 자원의 한정량 등 <u>현재의 상황에 맞추어서</u> 언제든지 **'변경이 가능하고'**
* 내 마음대로 또 현재 상태와 규모에 맞게끔 fit하게 튜닝이 가능하다는 점이 msa의 큰 매력이라고 생각한다.
* 단순 스케일링을 넘어서 서비스 구조와 db의 구조까지 유연하게 대응하고 변경하고 대처하는 
* scailability한 시스템을 설계하고 구현할 수 있다는 것이 가능해지는 아키텍처라는 것이 필자가 꼽은 가장 큰 장점이다.
### 장점4 : 유지보수 용이점
* 코드안에서 역할이 짬뽕되어있는 모놀리틱과 달리
* 각자 서비스안에서 다른 서비스의 눈치를 보지 않고 자신의 일만 하기때문에
* 하나의 서비스를 수정한다고 다른 서비스에 영향을 크게 미치지 않는다.
### 장점5 : 관심사 집중
* 유지보수의 용이점과 마찬가지로 타 서비스의 눈치를 보지 않고 본인의 일에 집중하게 설계하는 것이 가능해진다.
* 이로 인해 관심사의 집중이 가능해진다.

## MSA의 단점
* 바로 테스트다.
* 기존 모놀리틱 방식에서 테스트짜기 너무나도 불편해졌고, 어렵다.
* 모든 기술이, 패턴이, 아키텍처가 그렇듯 완벽한 것은 없다.
* 장점이 더 많은 기술이 주목을 받고 사용될 것이나, 단점은 분명히 있다는 것을 염두해야한다.
* 이를 해결하기위해 고민하고 창조해가는 것이 개발자의 숙명이 아닐까라고 필자는 생각한다.