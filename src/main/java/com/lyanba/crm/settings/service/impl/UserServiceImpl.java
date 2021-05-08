package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.UserDao;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.utils.SqlSessionUtil;

/**
 * @className: UserServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/8 23:43
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
}
