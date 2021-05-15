package com.lyanba.crm.workbench.service.impl;

import com.lyanba.crm.utils.SqlSessionUtil;
import com.lyanba.crm.utils.UUIDUtil;
import com.lyanba.crm.workbench.dao.ClueActivityRelationDao;
import com.lyanba.crm.workbench.dao.ClueDao;
import com.lyanba.crm.workbench.domain.Clue;
import com.lyanba.crm.workbench.domain.ClueActivityRelation;
import com.lyanba.crm.workbench.service.ClueService;

/**
 * @className: ClueServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 08:48
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    @Override
    public boolean save(Clue clue) {
        return clueDao.save(clue) == 1;
    }

    @Override
    public Clue detail(String id) {
        return clueDao.detail(id);
    }

    @Override
    public boolean unbind(String id) {
        return clueActivityRelationDao.unbind(id) == 1;
    }

    @Override
    public boolean bind(String cid, String[] aids) {
        boolean flag = true;
        for (String aid : aids) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(cid);
            clueActivityRelation.setActivityId(aid);
            if (clueActivityRelationDao.bind(clueActivityRelation) != 1) flag = false;
        }
        return flag;
    }
}
