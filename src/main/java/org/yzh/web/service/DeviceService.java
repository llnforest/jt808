package org.yzh.web.service;

import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.protocol.t808.T8100;
import org.yzh.web.model.vo.DeviceInfo;

public interface DeviceService {

    T8100 register(T0100 request,T8100 t8100);

    DeviceInfo authentication(T0102 request);

}