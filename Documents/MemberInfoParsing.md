## 회원 정보는 언제 필요할까?
* 어떤 데이터의 주인을 구별하거나, 특정 api에 접근할때
* 회원의 username이나 권한이 필요하다.
* 스프링 시큐리티를 사용한다면 principal이나 authentication을 사용해서 손쉽게 회원의 정보를 가져올 수 있다.

## JWT를 사용한다면 스프링 시큐리티 없이도 회원 정보를 가져올수 있다.
* JWT에는 subject에 username이나 회원 식별 정보가 담기고,
* auth(auth 헤더)에는 회원의 권한이 담긴다.
* 이번 프로젝트에서는 jwt를 이용해 회원에게 authorization을 부여하기로 하였다.
* 위의 jwt 특징을 활용해 충분히 회원 정보를 파싱할 수 있다.

## 코드로 알아보기
* AuthenticationInfo 라는 클래스를 만들어 사용하기로 하였다.
* 어디서든지 생성자 주입을 하여서 사용할 수 있도록 했다.
* 당연하게도 jwt 토큰을 발행할 때 사용했던 secret key와 동일한 secret key를 사용해야한다.
* 모든 정보를 반환하기 보다는 getEmail() 이나 getAuth() 처럼 필요한 부분만 가져오도록 설정했다.
* 상수화 처리된 값을 아래와 같다.
* JwtConstant.SECRET_KEY_PATH : ${jwt.secret}
* BEARER_TOKEN : Bearer
* CLAIM_NAME : auth
* HEADER : Authorization
* TOKEN_SUB_INDEX : 7
```
@Component
public class AuthenticationInfo {

    private final Key key;

    public AuthenticationInfo(@Value(JwtConstant.SECRET_KEY_PATH) String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmail(HttpServletRequest request) {
        String token = resolveToken(request);
        Claims claims = getAuthentication(token);
        return claims.getSubject();
    }

    public String getAuth(HttpServletRequest request) {
        String token = resolveToken(request);
        Claims claims = getAuthentication(token);
        return claims.get(JwtConstant.CLAIM_NAME).toString();
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtConstant.HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConstant.BEARER_TOKEN)) {
            return bearerToken.substring(JwtConstant.TOKEN_SUB_INDEX);
        }
        return null;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Claims getAuthentication(String accessToken) {
        return parseClaims(accessToken);
    }
}
```