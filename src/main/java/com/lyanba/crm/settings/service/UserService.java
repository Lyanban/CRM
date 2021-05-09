package com.lyanba.crm.settings.service;

import com.lyanba.crm.exception.LoginException;
import com.lyanba.crm.settings.domain.User;

import java.util.List;

/**
 * @className: UserService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/8 23:43
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
