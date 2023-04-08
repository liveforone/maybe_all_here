## Feign Client 활용 전략
* feign client는 간단한 데이터를 조회하는데에 쓰인다.
* 만약 데이터의 규모가 거대해지고, 일례로 1년간 거래내역을 뽑아서 통계를 내는 등의 조회를 할때에는
* read-only DB를 사용한다. 
* 이런 일이 아니라 계좌 정보를 조회하거나, 회원정보를 조회하거나 하는 간단한 조회작업은 feign client를 통해서 타 서비스에 조회 요청을 보낸다.

## Feign Client의 주의점
* 현재 feign의 주의점은 요청하는 http method와 같은 메소드를 가지고 있어야한다는 것이다.
* rest-api라는 상태를 전달하는 transfer 역할이라고 보면 간단하다.
* 따라서 모든 상태가 전송되고 전이된다.
* post 요청 -> feign get사용 이 불가하는 것이다.
* post 요청 -> feign post 사용 해야하만 가능하다.
* 하나의 http request안에서 feign을 호출하게되면 같은 http method로 전이가 된다. 
* 따라서 method가 다르다면 feign이 호출되지 않는다.
* 이 부분을 놓치면 많은 에러를 경험하게 되니 주의하자.
* 따라서 Feign client는 gateway의 fallback처럼 필요한 http method로 모두 구현해놓아야한다.
* 해당 feign client의 메서드를 호출하는 함수가 어떤 http method인지 fallback처럼 확인하고 모두 구현하자.

## Feign client의 주의점이 적용되는 곳
* 바로 circuit breaker의 fallback controller가 그러하다.
* post로 입력받은 api에 대해 fallback 해주고 싶다면 post로 fallback response 해야한다.
* rest-api를 사용하는 모든 곳에서는 http method에 주의하고, 상태가 전이된다는 점을 염두하자.

## 장애처리
* feign client를 사용할때 상대 서비스가 장애가 발생하거나, 적절하지 않은 식별자를 이용해 조회할때에는 에러가 발생한다. 
* 그중에서도 5XX로 시작하는 서버에러가 발생한다.
* 따라서 반드시 feign client는 서킷 브레이커를 걸어주어야한다.
* 서킷브레이커를 걸때에는 가져온 객체가 null일 경우에 new 생성자로 빈 새로운 객체를 생성하여 리턴하면
* 매퍼를 사용하거나, 컨트롤러단에서 null에 해당하는 response entity를 리턴할때 많은 도움이 된다.

## 시큐리티가 있는 서비스에서 데이터를 가져올때
* 유저 서비스는 시큐리티가 작동하는 서비스 이다.
* 시큐리티가 작동하는 서비스는 Feign client로 가져올때 주의해야한다.
* 외부에 제공하는 api를 permitAll로 설정하지 않으면 401에러를 맞기 때문이다.
* 로그인, 회원가입 처럼 permitAll로 외부에 개방하지 않으면 토큰을 넣고 호출하여도 문제가 발생한다.
* 따라서 시큐리티가 있는 서비스에서 데이터를 가져올때에는 해당 서비스의 시큐리티에 permitAll로 외부에 개방한다.