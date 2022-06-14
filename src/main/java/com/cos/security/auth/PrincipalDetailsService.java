package com.cos.security.auth;

import com.cos.security.model.User;
import com.cos.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// security config 에서 loginProcessUrl("/login");
// /login 요청이 오면 자동으로 UserDetailService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 폼에서 name="username" 과 매개변수 String username 매칭이 되어 있어야 함
    // 파라미터 바꾸고 싶으면 SecurityConfig 에서 parameter name 세팅 필요
    // 시큐리티 session(Authentication(UserDetails))) 의 구조
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username).orElseThrow(() -> null);
        return new PrincipalDetails(userEntity);
    }
}
