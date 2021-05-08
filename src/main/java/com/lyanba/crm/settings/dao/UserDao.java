package com.lyanba.crm.settings.dao;

import com.lyanba.crm.settings.domain.User;

import java.util.Map;

/**
 * @className: UserDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/8 23:40
 */
public interface UserDao {
    User login(Map<String, String> map);
}
