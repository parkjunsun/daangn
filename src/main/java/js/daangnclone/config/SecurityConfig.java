package js.daangnclone.config;

import js.daangnclone.handler.CustomAuthenticationFailureHandler;
import js.daangnclone.handler.CustomAuthenticationSuccessHandler;
import js.daangnclone.security.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/favicon.ico", "/resources/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

//        http
//                .rememberMe()
//                .rememberMeParameter("remember")
//                .tokenValiditySeconds(3600)
//                .alwaysRemember(true)
//                .userDetailsService(userDetailsService)
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/boardList/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/certify/**").permitAll()
                .antMatchers("/search/**").permitAll()
                .anyRequest()
                .authenticated()
        // 인가되지 않은 url 접속시 /login 으로 redirect
        .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        .and()
                .formLogin()
                .loginPage("/login") //사용자 정의 loginPage
                .loginProcessingUrl("/login_proc") ///login_proc 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌(필터 다 타면서 db검증까지..)
                .defaultSuccessUrl("/")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
        .and()
                .oauth2Login() //구글 로그인이 완료된 뒤의 후처리가 필요 1.코드받기(인증됬다는것) 2.엑세스토큰(권한) 3.사용자프로필 정보를 가져옴 4-1.그 정보를 토대로 회원가입 자동진행 4-2. 집주소, vip(일반)등급같은 추가할 정보가 있을 경우 또 입력이 가능해야함
                //중요! 구글 로그인이 완료가 되면 엑세스 토큰 + 사용자프로필 정보를 받음
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }
}
