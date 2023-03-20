package maybe_all_here.userservice.config;

import lombok.RequiredArgsConstructor;
import maybe_all_here.userservice.controller.constant.MemberUrl;
import maybe_all_here.userservice.jwt.JwtAuthenticationFilter;
import maybe_all_here.userservice.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ROLE = "ADMIN";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                MemberUrl.HOME,
                                MemberUrl.SIGNUP,
                                MemberUrl.LOGIN,
                                MemberUrl.ACTUATOR
                        ).permitAll()
                        .requestMatchers(
                                MemberUrl.ADMIN_ALL
                        ).hasRole(ADMIN_ROLE)
                        .anyRequest().authenticated()
                        .and()
                        .addFilterBefore(
                                new JwtAuthenticationFilter(jwtTokenProvider),
                                UsernamePasswordAuthenticationFilter.class
                        )
                )
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(MemberUrl.LOGOUT))
                .logoutSuccessUrl(MemberUrl.HOME)
                .and()
                .exceptionHandling()
                .accessDeniedPage(MemberUrl.PROHIBITION);
        return http.build();
    }
}
