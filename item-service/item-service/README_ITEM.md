## 상품 서비스 소개
## 상세 요구사항

price, title, content, remaining
(fk : email, shopId, 상점의 상품 목록은 shopId를 pathvariable로 한다.)

카테고리는 고민을 해봐야함. 검색을 집중해서 한다면 카테고리는 사실 필요가 없음. 일반적으로도 검색을 많이 사용하고,
검색이 직관적이고, 편하고, 카테고리처럼 답이 있는 제약이 걸린게 아닌 자유로워서 검색으로 두고 카테고리 태그를 따로 안다는 것이 더 효과적일 수도 있다.

## API 설계
## Json body 예시

## 서비스간 통신
kafak producer -> file service에게 list형식의 multipartfile 넘기기

상점 seller 페이지에서 내 상품 보기 클릭시 내 상품과 상품 등록 버튼이 나옴.
상품 등록 버튼 클릭시 상품이 등록됨.
이때 shopId의 pathvariable이 걸릴텐데,
이 shopId로 shop 서비스에게 상점 정보 요청해서(dto만들어야함) 상점 주인 이메일 받아오기
이메일을 저장하는 이유는 상품을 수정할때 이메일 재차 확인 할것이기 때문.
sellerinfoResponse 형식으로 받아옴