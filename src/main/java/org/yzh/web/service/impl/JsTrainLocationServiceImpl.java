package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.transform.Bytes;
import org.yzh.protocol.commons.transform.attribute.TirePressure;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.mapper.JsTrainLocationMapper;
import org.yzh.web.model.entity.JsDeviceLocation;
import org.yzh.web.model.entity.JsTrainLocation;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.JsTrainLocationService;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JsTrainLocationServiceImpl implements JsTrainLocationService {

    private static final Logger log = LoggerFactory.getLogger(JsTrainLocationServiceImpl.class.getSimpleName());

    @Autowired
    private JsTrainLocationMapper jsTrainLocationMapper;


    @Override
    public void insert(T0200 request, String mobile, int type,int tableId) {
        String deviceId = mobile;
        String plateNo = "";
        DeviceInfo device = TcpClientUtils.getClientDevice(mobile);
        if (device != null) {
            deviceId = device.getDeviceId();
            plateNo = device.getPlateNo();
        }

        JsTrainLocation location = new JsTrainLocation();

        location.setMarkId(tableId);
        location.setDeviceTime(request.getDateTime());
        location.setDevnum(deviceId);
        location.setMobileNo(mobile);
        location.setPlateNo(plateNo);
        location.setWarningMark(request.getWarningMark());
        location.setStatus(request.getStatus());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setDriveSpeed(request.getDriveSpeed());
        location.setStarSpeed(request.getStarSpeed());
        location.setDirection(request.getDirection());
        location.setMapFenceId(0);
        location.setCreateTime(LocalDateTime.now());
        //转速
        if(request.getAttributes() != null){
            TirePressure tirePressure = (TirePressure)request.getAttributes().get(TirePressure.attributeId);
            location.setEngineSpeed(Bytes.byteArrayToInt(tirePressure.getValue()));
        }else{
            location.setEngineSpeed(0);
        }
        jsTrainLocationMapper.insertSelective(location);
    }
}