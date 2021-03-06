package com.lyanba.crm.workbench.service;

import com.lyanba.crm.vo.PaginationVO;
import com.lyanba.crm.workbench.domain.Activity;
import com.lyanba.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * @className: ActivityService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/9 17:30
 */
public interface ActivityService {
    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByActivityId(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String activityName);
}
