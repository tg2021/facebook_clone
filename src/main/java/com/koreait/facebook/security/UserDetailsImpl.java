package com.koreait.facebook.security;

import com.koreait.facebook.user.model.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    @Getter
    private UserEntity user;

    public UserDetailsImpl(UserEntity user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPw();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    /*
        메소드명                         리턴타입                                           설명
        getUsername()                   String                                          계정의 이름을 리턴한다
        getPassword()                   String                                          계정의 패스워드를 리턴한다
        isAccountNonExpired()           boolean                                         계정이 만료되지 않았는지를 리턴한다(true를 리턴하면 만료되지 않음을 의미)
        isAccountNonLocked()            boolean                                         계정이 잠겨있지 않은지를 리턴한다(true를 리턴하면 계정이 잠겨있지 않음을 의미)
        isCredentialsNonExpired()       boolean                                         계정의 패스워드가 만료되지 않았는지를 리턴한다(true를 리턴하면 패스워드가 만료되지 않음을 의미)
        isEnabled()                     boolean                                         계정이 사용가능한 계정인지를 리턴한다(true를 리턴하면 사용가능한 계정인지를 의미)
        getAuthorities()                Collection<? extends GrantedAuthority>          계정이 갖고 있는 권한 목록을 리턴한다
     */
}
