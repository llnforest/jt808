package org.yzh.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionListener;
import org.yzh.web.mapper.JsDeviceMapper;
import org.yzh.web.model.vo.DeviceInfo;

public class JTSessionListener implements SessionListener {

    @Autowired
    private JsDeviceMapper jsDeviceMapper;

    @Override
    public void sessionCreated(Session se) {
    }

    @Override
    public void sessionDestroyed(Session session) {
//        DeviceInfo device = (DeviceInfo) session.getSubject();
//        if (device != null)
//            deviceMapper.update();
    }
}