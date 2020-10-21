package org.yzh.web.service;

import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.web.model.vo.DeviceInfo;

public interface DeviceService {

    DeviceInfo register(T0100 request);

    DeviceInfo authentication(T0102 request);

}