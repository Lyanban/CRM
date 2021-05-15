package com.lyanba.crm.workbench.service.impl;

import com.lyanba.crm.utils.SqlSessionUtil;
import com.lyanba.crm.workbench.dao.ClueDao;
import com.lyanba.crm.workbench.domain.Clue;
import com.lyanba.crm.workbench.service.ClueService;

/**
 * @className: ClueServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 08:48
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    @Override
    public boolean save(Clue clue) {
        return clueDao.save(clue) == 1;
    }
}
