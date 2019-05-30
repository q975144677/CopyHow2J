package com.how2j.copy.config;

import com.how2j.copy.security.config.EmailCodeAuthenticationSecurityConfig;
import com.how2j.copy.security.config.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
//启用权限管理
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static String KEY = "";
    //加密  需要下方注册为Bean
    @Autowired
    private PasswordEncoder passwordEncoder;
    //UserServiceImpl需要实现此接口
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig ;

    @Autowired
    EmailCodeAuthenticationSecurityConfig emailCodeAuthenticationSecurityConfig ;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
    //认证方式
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider  = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return  authenticationProvider;
    }
    //管理认证
    @Autowired
    protected void configureGlobel(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());

    }


    @Override
/**
 * 自定义配置
 */
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub

        http
                .authorizeRequests()
                .antMatchers("/css/**","/js/**","/fonts/**","/index")
                .permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/account/login").failureUrl("/account/login?msg=error").defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and()
                .rememberMe()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.apply(emailCodeAuthenticationSecurityConfig );
        http.apply(smsCodeAuthenticationSecurityConfig );
    }


}
