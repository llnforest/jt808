package org.yzh.web.service.impl;

import com.mysql.cj.util.Base64Decoder;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzh.protocol.t808.T0100;
import org.yzh.protocol.t808.T0102;
import org.yzh.protocol.t808.T8100;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.mapper.JsDeviceAuthRecordMapper;
import org.yzh.web.mapper.JsDeviceMapper;
import org.yzh.web.mapper.JsPlatInfoMapper;
import org.yzh.web.mapper.JsTrainingcarMapper;
import org.yzh.web.model.entity.JsDevice;
import org.yzh.web.model.entity.JsDeviceAuthRecord;
import org.yzh.web.model.entity.JsPlatInfo;
import org.yzh.web.model.entity.JsTrainingcar;
import org.yzh.web.service.DeviceService;
import org.yzh.web.sign.IVerify;
import org.yzh.web.sign.Sign;
import org.yzh.web.sign.Verify;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class.getSimpleName());

    @Autowired
    private JsDeviceMapper jsDeviceMapper;

    @Autowired
    private JsTrainingcarMapper jsTrainingcarMapper;

    @Autowired
    private JsDeviceAuthRecordMapper jsDeviceAuthRecordMapper;

    @Autowired
    private JsPlatInfoMapper jsPlatInfoMapper;

    @Override
    public T8100 register(T0100 request,T8100 t8100) {

        String sn = request.getSn();
        String licnum = request.getPlateNo();
        String plateColor = String.valueOf(request.getPlateColor());

        //判断终端是否存在
        JsDevice jsDevice = jsDeviceMapper.isCanRegister(sn,request.getHeader().getMobileNo());
        if(jsDevice == null){
            t8100.setResultCode(t8100.NotFoundTerminal);//终端不存在
            return t8100;
        }

        //判断终端是否注册
        if(jsDevice.getStatus() == 1){
            t8100.setResultCode(t8100.AlreadyRegisteredTerminal);//终端已注册
            return t8100;
        }

        //判断车辆是否存在
        JsTrainingcar carRecord = new JsTrainingcar();
        carRecord.setPlatecolor(plateColor);
        carRecord.setLicnum(licnum);
        JsTrainingcar jsTrainingcar = jsTrainingcarMapper.find(carRecord);
        if(jsTrainingcar == null || !jsTrainingcar.getCarnum().equals(jsDevice.getBindCarnum())){
            t8100.setResultCode(t8100.NotFoundVehicle);//车辆不存在
            return t8100;
        }

        //判断车辆是否注册
        JsDevice isCarRegister = jsDeviceMapper.isRegisterCar(jsTrainingcar.getCarnum());
        if(isCarRegister != null && t8100.getResultCode() == t8100.Success){
            t8100.setResultCode(t8100.AlreadyRegisteredVehicle);//车辆已注册
            return t8100;
        }

        jsDevice.setStatus(1);
        jsDevice.setRegisterTime(new Date());
        jsDeviceMapper.updateByPrimaryKeySelective(jsDevice);
        t8100.setResultCode(T8100.Success);

        JsPlatInfo jsPlatInfo = jsPlatInfoMapper.selectByPrimaryKey(1);
        t8100.setPlatNum(jsPlatInfo.getPlatNum());


        t8100.setInscode(jsDevice.getInscode());
        t8100.setDevnum(jsDevice.getDevnum());
        t8100.setCertSign(jsDevice.getPasswd());
        t8100.setCert(jsDevice.getCert());

        return t8100;
    }

    @Override
    public Boolean authentication(T0102 request) {
//        String token = request.getToken();
        byte[] bytes = request.getToken();
//        String token = new String(bytes);
//            log.info(token);

//        log.info("16进制字符串:",byte2HexStrNoSpace(bytes));
//        try {
            //解密获得devnum

//            bytes = Base64.getDecoder().decode(token);
//            bytes = EncryptUtils.decrypt(bytes);
            String mobile = request.getHeader().getMobileNo();
            JsDevice jsDevice = jsDeviceMapper.getByMobile(mobile);

//            List<JsDeviceAuthRecord> list = jsDeviceAuthRecordMapper.selectRecordAndDevice(1);
//            JsDeviceAuthRecord listOne = CollectionUtils.isNotEmpty(list)?list.get(0):null;
//            log.info("mobilePhone:{}",listOne.getJsDevice().getMobile());

            log.info("js:{}",jsDevice);
            if(jsDevice == null){
                return false;
            }
            String devnum = jsDevice.getDevnum();
            JsDeviceAuthRecord condition = new JsDeviceAuthRecord();
            condition.setDevnum(devnum);
            condition.setAuthTime(String.valueOf(request.getTimeStamp()));
            List<JsDeviceAuthRecord> selectList = jsDeviceAuthRecordMapper.selectByCondition(condition);
            if(CollectionUtils.isNotEmpty(selectList)) return false;
            try{
                String cadata = jsDevice.getCert();
                char[] password = jsDevice.getPasswd().toCharArray();
                byte [] cabuf = Base64Decoder.decode(cadata.getBytes(), 0, cadata.length());
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                keyStore.load(new ByteArrayInputStream(cabuf), password);
                Enumeration<String> aliases = keyStore.aliases();
                if (!aliases.hasMoreElements()) {
                    throw new RuntimeException("no alias found");
                }
                String alias = aliases.nextElement();
                X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
                IVerify verify = new Verify();
                boolean ok = verify.verify(devnum, request.getTimeStamp(), bytes, cert,"e2");
                log.info("ok:{}",ok);
                if(!ok) return ok;
            }catch(Exception e){
                log.info("异常：{},{}",e.getMessage(),e.getStackTrace());
                return false;
            }


            JsDeviceAuthRecord record = new JsDeviceAuthRecord();
            record.setAuthTime(String.valueOf(request.getTimeStamp()));
            record.setDevnum(devnum);
            record.setCreateTime(new Date());
            int num = jsDeviceAuthRecordMapper.insertSelective(record);
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

    @Override
    public JsDevice getDeviceByMobile(String mobile) {
        JsDevice jsDevice = jsDeviceMapper.getByMobile(mobile);
        return jsDevice;
    }

    public static String byte2HexStrNoSpace(byte[] b) {
        String stmp;
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
        }
        return sb.toString().toUpperCase().trim();
    }
}