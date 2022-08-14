//package js.daangnclone.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http .csrf().disable();
//
//        http    .authorizeRequests()
//                //.antMatchers("/board/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/signup/**").permitAll()
//                .antMatchers("/login/**").permitAll()
//                //.antMatchers("/confirm-email/**").permitAll()
//                //다른 요청은 누구든지 접근 가능
//                .anyRequest().authenticated()
//                .anyRequest().permitAll()
//        .and()
//                .formLogin()
//                .loginPage("/login") //사용자 정의 loginPage
//                .loginProcessingUrl("/login_proc") ///login_proc 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
//                .defaultSuccessUrl("/")
//                //.successHandler(successHandler)
//                //.failureHandler(failureHandler)
//                .permitAll()
//        .and()
//                .oauth2Login().loginPage("/token/expired"); // Authentication 객체가 springSecurityContextHolder 들어오면 모든 시큐리티 체인이 종료가 되면서 login 서비스 종료
//                //.successHandler(successHandler)
//                //.userInfoEndpoint().userService(oAuth2UserService);
//
//        return http.build();
//    }
//
//}
