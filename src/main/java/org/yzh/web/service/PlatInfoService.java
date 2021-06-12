package org.yzh.web.service;

import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.protocol.t808.T8100;
import org.yzh.web.model.entity.JsDevice;
import org.yzh.web.model.entity.JsPlatInfo;

public interface PlatInfoService {

    JsPlatInfo findById(int id);


}