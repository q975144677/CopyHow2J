package com.how2j.copy.security.provider;

import com.how2j.copy.pojo.User;
import com.how2j.copy.security.token.SmsCodeAuthenticationToken;
import com.how2j.copy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

@Autowired
    UserService userService ;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken token = (SmsCodeAuthenticationToken)authentication;
        String ph = (String) token.getPrincipal();
        User user = userService.getByPhone(ph);
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        UsernamePasswordAuthenticationToken result =
                new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
