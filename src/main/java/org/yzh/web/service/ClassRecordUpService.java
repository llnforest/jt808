package org.yzh.web.service;

import org.yzh.protocol.t808.T8900_0900_time_up;
import org.yzh.web.model.entity.JsClassrecordUp;

public interface ClassRecordUpService {

    JsClassrecordUp addRecord(T8900_0900_time_up data);

    void getMenuList(short parentId,int times);




}