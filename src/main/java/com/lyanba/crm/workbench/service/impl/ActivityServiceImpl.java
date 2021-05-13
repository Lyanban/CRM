package com.lyanba.crm.workbench.service.impl;

import com.lyanba.crm.settings.dao.UserDao;
import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.utils.SqlSessionUtil;
import com.lyanba.crm.vo.PaginationVO;
import com.lyanba.crm.workbench.dao.ActivityDao;
import com.lyanba.crm.workbench.dao.ActivityRemarkDao;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.domain.ActivityRemark;
import com.lyanba.crm.workbench.service.ActivityService;

import java.util.HashMap;
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
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

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

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAids(ids);
        if(count1!=count2){
            flag = false;
        }
        //删除市场活动
        int count3 = activityDao.delete(ids);
        if(count3!=ids.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> uList = userDao.getUserList();
        Activity a = activityDao.getById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("uList", uList);
        map.put("a", a);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        return activityDao.update(activity) == 1;
    }

    @Override
    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    @Override
    public List<ActivityRemark> getRemarkListByActivityId(String activityId) {
        return activityRemarkDao.getRemarkListByActivityId(activityId);
    }

    @Override
    public boolean deleteRemark(String id) {
        return activityRemarkDao.deleteRemark(id)  == 1;
    }
}
