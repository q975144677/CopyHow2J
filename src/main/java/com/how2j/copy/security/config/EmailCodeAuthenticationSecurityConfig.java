package com.how2j.copy.security.config;

import com.how2j.copy.security.filter.EmailCodeAuthenticationFilter;
import com.how2j.copy.security.handler.MyAuthenticationFailHandler;
import com.how2j.copy.security.handler.MyAuthenticationSuccessHandler;
import com.how2j.copy.security.provider.EmailCodeAuthentictionProvider;
import com.how2j.copy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class EmailCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private UserService userService ;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        myAuthenticationFailHandler.setTar("email");
        EmailCodeAuthenticationFilter filter = new EmailCodeAuthenticationFilter(new AntPathRequestMatcher("/account/login/email","POST")) ;
        filter .setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter .setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter .setAuthenticationFailureHandler(myAuthenticationFailHandler);

        EmailCodeAuthentictionProvider provider = new EmailCodeAuthentictionProvider(userService) ;
        http.authenticationProvider(provider)
                .addFilterAfter(filter , UsernamePasswordAuthenticationFilter.class);
    }
}
