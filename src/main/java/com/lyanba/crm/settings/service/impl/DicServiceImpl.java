package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.DicTypeDao;
import com.lyanba.crm.settings.dao.DicValueDao;
import com.lyanba.crm.settings.service.DicService;
import com.lyanba.crm.utils.SqlSessionUtil;

/**
 * @className: DicServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 09:02
 */
public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
}
