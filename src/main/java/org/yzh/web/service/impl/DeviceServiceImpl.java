package org.yzh.web.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.protocol.t808.T8100;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.mapper.JsDeviceAuthRecordMapper;
import org.yzh.web.mapper.JsDeviceMapper;
import org.yzh.web.model.entity.JsDevice;
import org.yzh.web.model.entity.JsDeviceAuthRecord;
import org.yzh.web.service.DeviceService;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class.getSimpleName());

    @Autowired
    private JsDeviceMapper jsDeviceMapper;

    @Autowired
    private JsDeviceAuthRecordMapper jsDeviceAuthRecordMapper;

    @Override
    public T8100 register(T0100 request,T8100 t8100) {
        String sn = request.getSn();
        String plateNo = request.getPlateNo();
        t8100.setResultCode(T8100.Success);
        //判断终端是否存在
        JsDevice jsDevice = jsDeviceMapper.isExistsDev(sn);
        if(jsDevice == null && t8100.getResultCode() == t8100.Success){
            //终端不存在
            t8100.setResultCode(t8100.NotFoundTerminal);
        }
        //判断终端是否注册
        jsDevice = jsDeviceMapper.isRegisterDev(sn);
        if(jsDevice != null && t8100.getResultCode() == t8100.Success){
            //终端已注册
            t8100.setResultCode(t8100.AlreadyRegisteredTerminal);
        }
        jsDevice = jsDeviceMapper.isExistsCar(request.getPlateColor(),plateNo);
        if(jsDevice == null && t8100.getResultCode() == t8100.Success){
            //车辆不存在
            t8100.setResultCode(t8100.NotFoundVehicle);
        }
        jsDevice = jsDeviceMapper.isRegisterCar(request.getPlateColor(),plateNo);
        if(jsDevice != null && t8100.getResultCode() == t8100.Success){
            //车辆已注册
            t8100.setResultCode(t8100.AlreadyRegisteredVehicle);
        }
        jsDevice = jsDeviceMapper.isCanRegister(sn,plateNo);
        if(jsDevice == null && t8100.getResultCode() == t8100.Success){
            //终端不存在
            t8100.setResultCode(t8100.NotFoundTerminal);
        }
        if(t8100.getResultCode() != t8100.Success) return t8100;

        jsDevice.setStatus(1);
        jsDevice.setRegisterTime(new Date());
        log.info("ok");
        jsDeviceMapper.updateByPrimaryKeySelective(jsDevice);
        t8100.setPlatNum("aaa");
        t8100.setInscode(jsDevice.getInscode());
        t8100.setDevnum(jsDevice.getDevnum());
        t8100.setCertSign(jsDevice.getPasswd());
        t8100.setCert(jsDevice.getCert());

        return t8100;
    }

    @Override
    public Boolean authentication(T0102 request) {
        String token = request.getToken();
        byte[] bytes;
//        try {
            //解密获得devnum
            bytes = Base64.getDecoder().decode(token);
            bytes = EncryptUtils.decrypt(bytes);
            String devnum = "aaa";

//            List<JsDeviceAuthRecord> list = jsDeviceAuthRecordMapper.selectRecordAndDevice(1);
//            JsDeviceAuthRecord listOne = CollectionUtils.isNotEmpty(list)?list.get(0):null;
//            log.info("mobilePhone:{}",listOne.getJsDevice().getMobile());

            JsDevice jsDevice = jsDeviceMapper.getByDevnum(devnum);
            log.info("js:{}",jsDevice);
            if(jsDevice == null || jsDevice.getStatus() != 1){
                return false;
            }
            JsDeviceAuthRecord condition = new JsDeviceAuthRecord();
            condition.setDevnum(devnum);
            condition.setAuthTime(request.getTimeStamp());
            List<JsDeviceAuthRecord> selectList = jsDeviceAuthRecordMapper.selectByCondition(condition);
            if(CollectionUtils.isNotEmpty(selectList)) return false;
            log.info("selectList:{}",selectList);
            JsDeviceAuthRecord selectOne = CollectionUtils.isNotEmpty(selectList)?selectList.get(0):null;
            log.info("selectOne:{}",selectOne);
            log.info("dev:{}",selectOne.getDevnum());
//            log.info("mobilePhone:{}",selectOne.getJsDevice().getMobile());


            JsDeviceAuthRecord record = new JsDeviceAuthRecord();
            record.setAuthTime(request.getTimeStamp());
            record.setDevnum(devnum);
            record.setCreateTime(new Date());
            int num = jsDeviceAuthRecordMapper.insertSelective(record);
            log.info("id:{}",num);
            log.info("id:{}",record.getId());
            return true;


//            LocalDate expiresAt = device.getIssuedAt().plusDays(device.getValidAt());
//            if (expiresAt.isBefore(LocalDate.now())) {
//                log.warn("鉴权失败：过期的token，{}", token);
//                return null;
//            }
//            JsDevice record = deviceMapper.get(device.getDeviceId());
//            if (record != null) {
//                device.setPlateNo(record.getPlateNo());
//
//                record = new JsDevice(device.getDeviceId(), true, LocalDateTime.now());
//                record.setImei(request.getImei());
//                record.setSoftwareVersion(request.getVersion());
//                deviceMapper.update(record);
//            }
//            return device;
//        } catch (Exception e) {
//            log.info("鉴权失败：，{}", e.getMessage());
//            return false;
//        }
    }

    @Override
    public Boolean logout(String mobile) {
        try {
            JsDevice jsDevice = jsDeviceMapper.getByMobile(mobile);
            if(jsDevice == null){
                return false;
            }
            jsDevice.setStatus(2);
            jsDeviceMapper.updateByPrimaryKeySelective(jsDevice);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}