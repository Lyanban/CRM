package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.DicTypeDao;
import com.lyanba.crm.settings.dao.DicValueDao;
import com.lyanba.crm.settings.domain.DicType;
import com.lyanba.crm.settings.domain.DicValue;
import com.lyanba.crm.settings.service.DicService;
import com.lyanba.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: DicServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 09:02
 */
public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> dicTypeList = dicTypeDao.getTypeList();
        for (DicType dicType : dicTypeList) {
            String code = dicType.getCode();
            List<DicValue> dicValueList = dicValueDao.getListByCode(code);
            map.put(code + "List", dicValueList);
        }
        return map;
    }
}
