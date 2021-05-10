package com.lyanba.crm.workbench.dao;

/**
 * @className: ActivityRemarkDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/10 15:24
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);
}
