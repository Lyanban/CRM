package com.lyanba.crm.workbench.service.impl;

import com.lyanba.crm.utils.SqlSessionUtil;
import com.lyanba.crm.workbench.dao.ActivityDao;
import com.lyanba.crm.workbench.service.ActivityService;

/**
 * @className: ActivityServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/9 17:30
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}
