package com.lyanba.crm.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: LoginFilter
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/9 16:22
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if ("/login.jsp".equals(request.getServletPath()) || "/settings/user/login.do".equals(request.getServletPath())) {
            chain.doFilter(request, response);
        } else {
            if (null != request.getSession().getAttribute("user")) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
