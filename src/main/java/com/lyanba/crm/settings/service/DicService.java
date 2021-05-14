package com.lyanba.crm.settings.service;

import com.lyanba.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @className: DicService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/5/14 09:02
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
