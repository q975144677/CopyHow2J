package com.how2j.copy.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal ;

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public SmsCodeAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        super.setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
