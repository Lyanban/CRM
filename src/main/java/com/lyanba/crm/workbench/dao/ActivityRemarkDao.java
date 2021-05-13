package com.lyanba.crm.workbench.dao;

import com.lyanba.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @className: ActivityRemarkDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/10 15:24
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByActivityId(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
