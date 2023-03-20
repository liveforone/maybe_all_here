package maybe_all_here.userservice.service;

import lombok.RequiredArgsConstructor;
import maybe_all_here.userservice.domain.Member;
import maybe_all_here.userservice.domain.Role;
import maybe_all_here.userservice.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return createUserDetails(memberRepository.findByEmail(email));
    }

    private UserDetails createUserDetails(Member member) {
        if (member.getAuth() == Role.ADMIN) {
            String ADMIN_ROLE = "ADMIN";
            return User.builder()
                    .username(member.getEmail())
                    .password(member.getPassword())
                    .roles(ADMIN_ROLE)
                    .build();
        } else if(member.getAuth() == Role.SELLER) {
            String SELLER_ROLE = "SELLER";
            return User.builder()
                    .username(member.getEmail())
                    .password(member.getPassword())
                    .roles(SELLER_ROLE)
                    .build();
        } else {
            String MEMBER_ROLE = "MEMBER";
            return User.builder()
                    .username(member.getEmail())
                    .password(member.getPassword())
                    .roles(MEMBER_ROLE)
                    .build();
        }
    }
}
