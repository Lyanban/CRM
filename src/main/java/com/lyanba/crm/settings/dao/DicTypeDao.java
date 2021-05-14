package com.lyanba.crm.settings.dao;

import com.lyanba.crm.settings.domain.DicType;

import java.util.List;

/**
 * @className: DicTypeDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 09:00
 */
public interface DicTypeDao {
    List<DicType> getTypeList();
}
