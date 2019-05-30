package com.how2j.copy.security.filter;

import com.how2j.copy.security.token.EmailCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
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

public class EmailCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter  {
    /**
     * 前端传来的 参数名 - 用于request.getParameter 获取
     */
    private final String DEFAULT_EMAIL_NAME="email";
    private final String DEFAULT_EMAIL_CODE="e_code";
    //是否 仅仅post
    private boolean postOnly = true;

    /**
     * 父类中是 调用setFilterProcessesUrl(defaultFilterProcessesUrl); 方法创建可以通过的url
     * 精确绑定url
     * @param defaultFilterProcessesUrl
     */
    public EmailCodeAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * 通过 传入的 参数 创建 匹配器
     * 即 Filter过滤的url
     * @param requiresAuthenticationRequestMatcher
     */
    public EmailCodeAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(new AntPathRequestMatcher("/account/login/email","POST"));
    }

    /**
     * 给权限
     * filter 获得 用户名（邮箱） 和 密码（验证码） 装配到 token 上 ，
     * 然后把token 交给 provider 进行授权
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        HttpSession session = request.getSession();
        if(postOnly && !request.getMethod().equals("POST") ){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }else{

            String email = getEmail(request);
            if(email == null){
                email = "";
            }
            email = email.trim();
            //如果 验证码不相等 故意让token出错 然后走springsecurity 错误的流程
            boolean flag = isCodeEquals(request);
            /*
            封装 token
             */
            EmailCodeAuthenticationToken token = null;
            if(flag){
                token  = new EmailCodeAuthenticationToken(email);
            }else{
                token = new EmailCodeAuthenticationToken("error");
            }
            this.setDetails(request,token);
            /*
            交给 manager 发证
             */
            return this.getAuthenticationManager().authenticate(token);
        }

    //    return null;
    }

    /**
     * 获取 头部信息 让合适的provider 来验证他
     * @param request
     * @param token
     */
    public void setDetails(HttpServletRequest request , EmailCodeAuthenticationToken token ){
    token.setDetails(this.authenticationDetailsSource.buildDetails(request ));
    }

    /**
     * 获取 传来 的Email信息
     */
    public String getEmail(HttpServletRequest request ){
        String result=  request.getParameter(DEFAULT_EMAIL_NAME);
        return result;
    }
    /**
     * 判断 传来的 验证码信息 以及 session 中的验证码信息
     */
    public boolean isCodeEquals(HttpServletRequest request ){
        HttpSession session = request.getSession();
        String code1 = request.getParameter(DEFAULT_EMAIL_CODE);
        System.out.println("code1**********"+code1);
        String code2 =  (String)session.getAttribute(DEFAULT_EMAIL_CODE+getEmail(request));
        System.out.println("code2***********"+code2 );
        try{
            return code1.equals(code2);
        }catch (Exception e){
            return false;
        }



    }

}
