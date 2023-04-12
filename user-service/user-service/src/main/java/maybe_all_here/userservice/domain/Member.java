package maybe_all_here.userservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maybe_all_here.userservice.dto.signupAndLogin.MemberSignupRequest;
import maybe_all_here.userservice.service.util.PasswordUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Builder
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

    @Enumerated(value = EnumType.STRING)
    private Role auth;

    @Builder
    public Member(Long id, String email, String password, String realName, Role auth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.auth = auth;
    }


    //==Domain Logic Space==//

    public void signup(MemberSignupRequest request) {
        final String ADMIN = "admin@maybeAllHere.com";
        if (Objects.equals(request.getEmail(), ADMIN)) {
            request.setAuth(Role.ADMIN);
        } else {
            request.setAuth(Role.MEMBER);
        }
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));

        buildingMember(request);
    }

    public void signupSeller(MemberSignupRequest request) {
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));
        request.setAuth(Role.SELLER);

        buildingMember(request);
    }

    private void buildingMember(MemberSignupRequest request) {
        this.id = request.getId();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.realName = request.getRealName();
        this.auth = request.getAuth();
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
