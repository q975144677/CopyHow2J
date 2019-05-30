package com.how2j.copy.security.provider;

import com.how2j.copy.security.token.EmailCodeAuthenticationToken;
import com.how2j.copy.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class EmailCodeAuthentictionProvider implements AuthenticationProvider {

    UserService userService ;

    public EmailCodeAuthentictionProvider(UserService userService) {
        this.userService = userService;
    }

    /**
     * 认证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailCodeAuthenticationToken token = (EmailCodeAuthenticationToken)authentication;
        UserDetails user = userService.getByEmail((String)token.getPrincipal());
        System.out.println(token.getPrincipal());
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        System.out.println(user.getAuthorities());
        UsernamePasswordAuthenticationToken result =
                new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return EmailCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
