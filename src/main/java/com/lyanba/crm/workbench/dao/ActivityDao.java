package com.lyanba.crm.workbench.dao;

import com.lyanba.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @className: ActivityDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/9 17:25
 */
public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);
}
