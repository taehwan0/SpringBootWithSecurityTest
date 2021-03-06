package com.cos.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// secureEnabled -> Secured 어노테이션 활성화 -> Controller 개별적으로 필요 ROLE 구현 가능
// prePostEnabled -> PreAuthorize 어노테이션 활성화 -> 위와 동일하나 Role 문법 사용하며, 다중 Role 구현 가능
// configure Override 를 통한 구현으로 글로벌 활성화 하는 방식을 권장함
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and() // 인증이 없을 경우 user, admin, manager page 접속 시
                .formLogin()
                .loginPage("/loginForm") // 로그인 페이지 리다이렉트
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행한다. -> controller /login 을 default로 진행
                .defaultSuccessUrl("/"); // login 후 redirect url

    }
}
