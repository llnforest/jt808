package org.yzh.web.service;

import org.yzh.framework.session.Session;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.protocol.t808.T8100;
import org.yzh.web.model.entity.JsDevice;

public interface JsDeviceService {

    T8100 register(T0100 request,T8100 t8100);

    Boolean authentication(T0102 request, Session session);

    Boolean logout(String mobile);

    JsDevice getDeviceByMobile(String mobile);

}