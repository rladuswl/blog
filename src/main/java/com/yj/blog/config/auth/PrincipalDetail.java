package com.yj.blog.config.auth;

import com.yj.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면,
// UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소에 저장한다.
@Getter
public class PrincipalDetail implements UserDetails {
    private User user; // 콤포지션 (객체를 품고 있는 것)

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 리턴한다. (true : 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지 안 잠겨있는지 리턴한다. (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지를 리턴한다. (true : 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 리턴한다. (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 현재는 하나만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>(); // ArrayList는 Collection 타입이다. (상속)

//        collectors.add(new GrantedAuthority() {
//
//            @Override
//            public String getAuthority() {
//                return "ROLE_"+user.getRole(); // role 을 받을 때 앞에 "ROLE_" 붙이는 것(prefix)이 스프링의 규칙, 즉 ROLE_USER 같은 식으로 리턴됨
//            }
//        });

        collectors.add(()->{return "ROLE_"+ user.getRole();}); // 어차피 add 안에 들어갈 파라미터는 GrantedAuthority(메소드도 getAuthority() 하나 가지고 있음) 뿐이기 때문에 람다식으로 가능

        return collectors;
    }

}
