package com.lyanba.crm.settings.web.controller;

import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.settings.service.impl.UserServiceImpl;
import com.lyanba.crm.utils.MD5Util;
import com.lyanba.crm.utils.PrintJson;
import com.lyanba.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: UserController
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/8 23:46
 */
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(">---------- 进入到用户控制器 ----------<");
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path)) {
            login(request, response);
        } else if ("/settings/user/xxx.do".equals(path)) {
            // xxx(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">---------- 进入到用户验证操作 ----------<");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = MD5Util.getMD5(request.getParameter("loginPwd"));
        String ip = request.getRemoteAddr();
        System.out.println(">---------- 当前用户的 IP 地址 ----------<\n>---------- " + ip + " ----------<");
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = userService.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            PrintJson.printJsonFlag(response, true);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", e.getMessage());
            PrintJson.printJsonObj(response, map);
        }
    }
}
