package com.lyanba.crm.workbench.service;

import com.lyanba.crm.workbench.domain.Clue;

/**
 * @className: ClueService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 08:48
 */
public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbind(String id);

    boolean bind(String cid, String[] aids);
}
