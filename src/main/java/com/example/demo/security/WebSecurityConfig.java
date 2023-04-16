package com.example.demo.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.jwt.JwtAuthenticationFilter;
import com.example.demo.security.jwt.JwtAuthorizationFilter;
import com.example.demo.security.jwt.JwtProperties;
import com.example.demo.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration // 빈 등록
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor // final이 붙거나 @NotNull 이 붙은 필드의 생성자 생성
// @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 생성
@EnableMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
@Slf4j
public class WebSecurityConfig {
        // WebSecurityConfigurerAdapter을 상속받아 configure 재정의 -> SecurityFilterChain과
        // WebSecurityCustomizer을 Bean으로 등록

        private final CorsFilter corsFilter;
        private final JwtTokenProvider jwtTokenProvider;

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        // antMatchers("/url")는 정확한 URL만, mvcMatchers("/url")는 url/ and url.~까지 허용
        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                // WebSecurity은 Spring Security Filter Chain을 거치지 않기 때문에 '인증' , '인가' 서비스가 모두 적용X
                return (web) -> web.ignoring()
                                // .antMatchers("/h2-console/**")
                                // .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
                                // .requestMatchers(PathRequest.toH2Console())
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 자원에 대해서
                                                                                                       // Security를 적용X

        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
                        throws Exception {
                http
                                .formLogin().disable() // 기본적인 formLogin방식을 쓰지않음 -> JWT를 쓰려면 필수 위
                                                       // 세션허용,cors등록,formLogin방식을 꺼야함
                                .httpBasic().disable() // httpbasic방식(기본인증방식) : authorization에 id,pw를 담아서 보내는 방식(여기서
                                                       // id,pw가 노출될수 있음)

                                .csrf().disable()
                                // .exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // 인증예외처리
                                // AuthenticationEntryPoint 호출 -> 로그인페이지 이동, 401오류 코드 전달
                                // .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 인증이므로
                                                                                                            // 세션 사용x
                                .and()
                                .addFilter(corsFilter)
                                .headers().frameOptions().sameOrigin();

                http
                                .authorizeRequests()
                                .antMatchers("/**").permitAll()
                                // .anyRequest().authenticated(); // 그 외 어떤 요청이든 '인증'하는 화이트리스트 형식
                                // .anyRequest().permitAll()
                                .and()
                                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider),
                                                UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider),
                                                UsernamePasswordAuthenticationFilter.class); // 필터가 인증 프로세스 처리할 때만 적용
                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOriginPattern("http://localhost:3000");
                // configuration.addAllowedOriginPattern("http://3.35.9.167:8080");
                // 이곳에 관련 url 추가 해야합니다 도메인,리액트(?) 등
                configuration.addAllowedMethod("*");
                configuration.addAllowedHeader("*");
                configuration.addExposedHeader("Authorization"); // 없으면 프론트측 Header에 나타나지않고 network에만 나타나게됨
                configuration.addExposedHeader(JwtProperties.HEADER_ACCESS); // 없으면 프론트측 Header에 나타나지않고 network에만 나타나게됨
                configuration.addExposedHeader("username");
                configuration.addExposedHeader("nickname");
                configuration.addExposedHeader("profile");
                configuration.addExposedHeader("loginto");
                configuration.setAllowCredentials(true);
                // configuration.validateAllowCredentials();
                configuration.setMaxAge(3600L);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
// https://velog.io/@csh0034/Spring-Security-Config-Refactoring,
// https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
// 참고