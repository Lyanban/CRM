package com.lyanba.crm.settings.dao;

import com.lyanba.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @className: DicValueDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 09:00
 */
public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
