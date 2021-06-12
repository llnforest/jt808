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
import org.yzh.web.mapper.JsDeviceAuthRecordMapper;
import org.yzh.web.mapper.JsDeviceMapper;
import org.yzh.web.mapper.JsPlatInfoMapper;
import org.yzh.web.mapper.JsTrainingcarMapper;
import org.yzh.web.model.entity.JsDevice;
import org.yzh.web.model.entity.JsDeviceAuthRecord;
import org.yzh.web.model.entity.JsPlatInfo;
import org.yzh.web.model.entity.JsTrainingcar;
import org.yzh.web.service.DeviceService;
import org.yzh.web.service.PlatInfoService;
import org.yzh.web.sign.IVerify;
import org.yzh.web.sign.Verify;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Service
public class PlatInfoServiceImpl implements PlatInfoService {

    private static final Logger log = LoggerFactory.getLogger(PlatInfoServiceImpl.class.getSimpleName());

    @Autowired
    private JsPlatInfoMapper jsPlatInfoMapper;


    @Override
    public JsPlatInfo findById(int id) {
        JsPlatInfo jsPlatInfo = jsPlatInfoMapper.selectByPrimaryKey(id);
        return jsPlatInfo;
    }
}