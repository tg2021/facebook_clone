package com.koreait.facebook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetails;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // ignoring 설정
        web.ignoring().antMatchers("/pic/**", "/css/**", "/js/**", "/img/**", "/error", "favicon.ico", "/resources/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            CSRF(Cross Site Request Forgery) 공격 : 웹 어플리케이션 취약점 중 하나로 인터넷 사용자가
            자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록 등)를 특정 웹사이트에 요청하게 만든는 공격이다.

            csrf().disable() : basic auth를 사용하기 위해 csrf 보호 기능 disable
            authorizeRequests() : 모든 request를 인증

            표현식

            - hasRole : 특정 Role에 해당하면 성공
            - hasAnyRole : 특정 Role들 중 하나에 해당하면 성공
            - isAnonymous : 로그인하지 않은 사용자면 성공
            - isAuthenticated : 이미 인증된 사용자면 성공
            - permitAll : 항상 성공
            - denyAll : 항상 실패
            - access : 경로 변수나 여러개의 접근 정책 설정


             Resource 지정

            - antMatchers : ant 형식으로 지정한 경로 패턴의 resource에 적용
            - regexMatchers : 정규표현식으로 지정한 경로 패턴의 resource에 적용
            - requestMatchers :  RequestMatcher 인터페이스 구현과 일치하는 resource에 적용
            - anyRequest : 기타 resource에 적용
         */
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/user/login", "/user/join", "/user/auth").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/user/login")
                .usernameParameter("email")
                .passwordParameter("pw")
                .defaultSuccessUrl("/feed/home");

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 비밀번호 암호화해서 비교
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}
