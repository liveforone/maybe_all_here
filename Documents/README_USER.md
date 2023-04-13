## 회원 서비스 소개
* 회원 시스템은 전반적인 회원에 관해 다루는 시스템입니다.
* Member 엔티티를 사용합니다.

## 회원관리 기술
* 서버의 성능을 중요시 하는 전체적인 정책때문에 회원 관리 또한 세션처럼 서버에서 부담을 주는 방식이 아닌,
* Jwt 토큰을 활용해 서버에 부담을 주지 않는 방식을 채택하였습니다.
* 토큰관리는 가장 보편적인 방식인 프론트엔드의 로컬스토리지에서 jwt토큰을 관리하는것을 전제로 합니다.
* 토큰의 만료시간은 30분입니다.

## 상세 요구사항
* 회원은 일반회원과 관리자(ADMIN) 마지막으로 셀러(SELLER), 세 종류가 있다.
* 회원은 이메일기반으로 동작하며, 회원가입시 이메일/비밀번호와 함께 실명도 입력받는다.
* 회원은 이메일과 비밀번호의 변경이 가능해야한다.
* 어드민 페이지는 어드민만 접근 가능하며 모든 회원의 정보를 볼 수 있다.
* 회원은 마이페이지에서 내 정보를 볼 수 있다.
* 회원은 탈퇴가 가능하다.
* 빠르게 회원을 조회할 수 있게 이메일에 인덱스를 건다.
* 회원가입시 마일리지를 동시에 만든다.

## API 설계
```
[GET] / : 홈(토큰 불필요)
[GET/POST] /signup : 회원가입(토큰 불필요), MemberRequest 형식 필요
[GET/POST] /login : 로그인(토큰 불필요)
[GET] /logout : 로그아웃, get으로 받아도 정상 작동(토큰 불필요)
[GET] /my-page : 마이페이지(토큰 필요)
[GET] /user-info/{email} : 외부 서비스에서 호출하는 유저 정보 api
[PATCH] /change-email : 이메일 변경, ChangeEmailRequest 형식 필요
[PATCH] /change-password : 비밀번호 변경, ChangePasswordRequest 형식 필요
[DELETE] /withdraw : 회원탈퇴(토큰 필요), text 형식 문자열 비밀번호 필요
[GET] /admin : 어드민 페이지(토큰 필요)
[GET] /prohibition : 403 페이지(토큰 불필요)
```

## Json body 예시
```
[일반 유저]
{
    "email" : "yc1234@gmail.com",
    "password" : "1234",
    "realName" : "chan"
}

[어드민]
{
    "email" : "admin@intelligentBank.com",
    "password" : "1234",
    "realName" : "admin"
}

[이메일 변경]
{
    "email" : "yc1111@gmail.com",
    "password" : "1234"
}

[비밀번호 변경]
{
    "oldPassword" : "1234",
    "newPassword" : "1111"
}
```

## 셀러의 경우
* 설레는 따로 회원가입한다.
* 로그인은 같이 한다.
* 유저 서비스에서 셀러와 유저는 별반 다를 바 없지만
* 다른 서비스에서 회원의 role정보를 바탕으로 서비스가 오픈되는 범위가 달라진다.

## 어드민 회원가입
* 어드민 회원가입은 일반 회원 가입과 사뭇다릅니다.
* 어드민은 지정된 id(email)로 가입해야합니다.
* 비밀번호는 어드민이 정하고, 또 바꿀 수 있지만 첫 가입시는 반드시 지정된 이메일을 사용해야합니다.
* 이것을 통해서 어드민인지 파악하고 권한을 매핑하기 때문입니다.
* 이번 프로젝트에서 지정된 어드민 이메일은 admin@intelligentBank.com입니다.
* 비밀번호와 마찬가지로 회원가입 후 변경이 가능합니다.

## 서비스간 통신
### 회원 정보 전달
* 타 서비스에 MemberResponse의 형태로 member info를 전달한다.
```
[GET] /user-info/{email}
```
### 회원 가입시 마일리지 생성
* 회원가입시 kafka producer로 마일리지서비스에 create request를 보낸다.
```
request : email
topic : create-mileage
```
### 마일리지 조회
* 마이페이지에서 나의 마일리지를 조회한다.
```
url : /my-mileage/{email}
response dto : MileageResponse
```