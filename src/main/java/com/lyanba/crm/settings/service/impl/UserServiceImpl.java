package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.exception.LoginException;
import com.lyanba.crm.settings.dao.UserDao;
import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.utils.DateTimeUtil;
import com.lyanba.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: UserServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/8 23:43
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userDao.login(map);
        if (null == user) {
            throw new LoginException("账号密码不匹配");
        }
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0) {
            throw new LoginException("账号已失效");
        }
        if ("0".equals(user.getLockState())) {
            throw new LoginException("账号被锁定");
        }
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)) {
            throw new LoginException("IP地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
