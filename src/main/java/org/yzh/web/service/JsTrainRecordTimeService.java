package org.yzh.web.service;

import org.yzh.protocol.t808.T8900_0900_time_up;
import org.yzh.web.model.entity.JsTrainRecordTime;

public interface JsTrainRecordTimeService {

    JsTrainRecordTime addRecord(T8900_0900_time_up data);

    void getMenuList(short parentId,int times);




}