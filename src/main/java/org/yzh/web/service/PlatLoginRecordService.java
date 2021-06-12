package org.yzh.web.service;

import org.yzh.web.model.entity.JsPlatLoginRecord;

public interface PlatLoginRecordService {

    JsPlatLoginRecord findById(int id);

    int insert(JsPlatLoginRecord record);

}