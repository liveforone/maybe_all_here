package maybe_all_here.userservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maybe_all_here.userservice.domain.constant.MemberConstant;
import maybe_all_here.userservice.dto.signupAndLogin.MemberSignupRequest;
import maybe_all_here.userservice.service.util.PasswordUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(value = EnumType.STRING)
    private Role auth;

    private Member(String email, String password, String realName, String username, Role auth) {
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.username = username;
        this.auth = auth;
    }


    //==Domain Logic Space==//

    public static Member signup(MemberSignupRequest request) {
        final String ADMIN = "admin@maybeAllHere.com";
        if (Objects.equals(request.getEmail(), ADMIN)) {
            request.setAuth(Role.ADMIN);
        } else {
            request.setAuth(Role.MEMBER);
        }
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));

        return new Member(
                request.getEmail(), request.getPassword(),
                request.getRealName(), createUsername(), request.getAuth()
        );
    }

    private static String createUsername() {
        return UUID.randomUUID() + RandomStringUtils.randomAlphabetic(MemberConstant.RANDOM_STRING_LENGTH);
    }

    public static Member signupSeller(MemberSignupRequest request) {
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));
        request.setAuth(Role.SELLER);

        return new Member(
                request.getEmail(), request.getPassword(),
                request.getRealName(), createUsername(), request.getAuth()
        );
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    //==End Domain Logic Space==//


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(auth.getValue()));
        return authList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
