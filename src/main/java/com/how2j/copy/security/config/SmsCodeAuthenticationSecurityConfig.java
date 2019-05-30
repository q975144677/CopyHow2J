package com.how2j.copy.security.config;

import com.how2j.copy.security.filter.SmsCodeAuthenticationFilter;
import com.how2j.copy.security.handler.MyAuthenticationFailHandler;
import com.how2j.copy.security.handler.MyAuthenticationSuccessHandler;
import com.how2j.copy.security.provider.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    MyAuthenticationFailHandler failHandler;
    @Autowired
    MyAuthenticationSuccessHandler successHandler;
    @Autowired
    SmsCodeAuthenticationProvider provider ;
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        failHandler.setTar("phone");

        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter(new AntPathRequestMatcher("/account/login/email","POST")) ;
        filter .setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter .setAuthenticationSuccessHandler(successHandler );
        filter .setAuthenticationFailureHandler(failHandler );
        builder.authenticationProvider(provider)
                .addFilterAfter(filter , UsernamePasswordAuthenticationFilter.class);

    }
}
