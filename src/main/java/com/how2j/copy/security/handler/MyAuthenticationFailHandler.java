package com.how2j.copy.security.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
我这里加上scope 的原因是
spring中 autowired 装配的对象是单例的；
那么问题来了，
因为我 既有 邮箱登录 又有 密码登录，这两个我使用同一个handler ,
但是它们失败后跳转的url 又是不一样的，所以通过一个 tar 属性动态的绑定不同的handle
如果是单例的 ， 那么后一个配置的tar 会 覆盖 前一个配置的tar ，
从而导致 邮件也好 ， 手机也好 都跳转到 同一个位置，这是我们不想要的，
所以使用 多例 的@Scope 注解来保证绑定的是不同的handle
 */
@Component
@Scope("prototype")
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
/*
要跳转的位置
 */
    String tar  ;

    public String getTar() {
        return tar;
    }

    public void setTar(String tar) {
        this.tar = tar;
    }

    /**
     * 跳转
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        super.setDefaultFailureUrl("/account/login/"+tar+"?msg=error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
