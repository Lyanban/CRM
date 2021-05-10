package com.lyanba.crm.workbench.service.impl;

import com.lyanba.crm.utils.SqlSessionUtil;
import com.lyanba.crm.vo.PaginationVO;
import com.lyanba.crm.workbench.dao.ActivityDao;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

/**
 * @className: ActivityServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/9 17:30
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean save(Activity activity) {
        return activityDao.save(activity) == 1;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        int total = activityDao.getTotalByCondition(map);
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }
}
