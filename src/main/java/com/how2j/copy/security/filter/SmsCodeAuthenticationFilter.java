package com.how2j.copy.security.filter;

import com.how2j.copy.security.token.SmsCodeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private static final String SMS_CODE = "code";
    private static final String SMS_NAME="phone";

    protected SmsCodeAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public SmsCodeAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(new AntPathRequestMatcher("/account/login/phone","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String ph = getPhone(httpServletRequest);
        if(ph == null){
            ph = "" ;
        }
        ph = ph.trim();
        boolean flag = check(httpServletRequest);
        SmsCodeAuthenticationToken token = null;
        if(flag){
            token = new SmsCodeAuthenticationToken(ph);
        }else{
            token = new SmsCodeAuthenticationToken("error");
        }
        this.setDetails(httpServletRequest,token);
        return this.getAuthenticationManager().authenticate(token);

    }


    public boolean check(HttpServletRequest request ){
        String code1 = request.getParameter(SMS_CODE);
        HttpSession session = request.getSession();
        String code2 = (String) session.getAttribute("p_"+SMS_CODE+getPhone(request));
        System.out.println(code1 + "---" +code2);
        try{
            return code1.equals(code2) ;
        }catch (Exception e ){
            return false;
        }


    }
    public String getPhone(HttpServletRequest request ){
        return request.getParameter(SMS_NAME);
    }

    public void setDetails(HttpServletRequest request , SmsCodeAuthenticationToken token ){
        token.setDetails(this.authenticationDetailsSource.buildDetails(request ));
    }


}
