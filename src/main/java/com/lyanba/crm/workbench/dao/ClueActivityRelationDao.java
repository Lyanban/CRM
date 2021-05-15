package com.lyanba.crm.workbench.dao;

import com.lyanba.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {
    int unbind(String id);

    int bind(ClueActivityRelation clueActivityRelation);
}
