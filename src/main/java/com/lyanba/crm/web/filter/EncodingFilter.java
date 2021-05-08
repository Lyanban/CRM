package com.lyanba.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @className: EncodingFilter
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/9 02:08
 */
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println(">---------- 进入到字符编码过滤器 ----------<");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        chain.doFilter(req, resp);
    }
}
