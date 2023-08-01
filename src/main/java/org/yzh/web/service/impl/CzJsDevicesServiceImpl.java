package org.yzh.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.yzh.web.commons.DbUtils;
import org.yzh.web.mapper.CzJsCarsMapper;
import org.yzh.web.mapper.CzJsDevicesMapper;
import org.yzh.web.mapper.CzJsStudentReservesMapper;
import org.yzh.web.model.entity.CzJsCars;
import org.yzh.web.model.entity.CzJsDevices;
import org.yzh.web.model.entity.CzJsStudentReserves;
import org.yzh.web.service.CzJsDevicesService;
import org.yzh.web.service.CzJsStudentReservesService;

import java.util.List;

/**
 * @author lynn
 * @description: TODO
 * @date 2022/2/10 16:40
 */
@Service
@Slf4j
@EnableAsync
public class CzJsDevicesServiceImpl implements CzJsDevicesService {
    @Autowired
    private CzJsDevicesMapper czJsDevicesMapper;

    @Autowired
    private CzJsCarsMapper czJsCarsMapper;

    @Override
    public CzJsDevices find(CzJsDevices czJsDevices) {
        return DbUtils.find(czJsDevicesMapper.select(czJsDevices));
    }

    @Override
    public List<CzJsDevices> select(CzJsDevices czJsDevices) {
        return czJsDevicesMapper.select(czJsDevices);
    }

    @Override
    public void updateCarStatus(String terminalNo, int isBehind, String location) {
        //获取车辆编号
        CzJsDevices czJsDevices = new CzJsDevices();
        czJsDevices.setDevnum(terminalNo);
        czJsDevices.setBindStatus(1);
        czJsDevices.setSyncStatus(2);
        CzJsDevices devices = find(czJsDevices);
        //修改车辆状态
        if(devices != null && devices.getBindCarnum() != null){
            CzJsCars czJsCars = new CzJsCars();
            czJsCars.setCarnum(devices.getBindCarnum());
            czJsCars.setLocation(location);
            czJsCars.setIsBehind(isBehind);
            czJsCarsMapper.updateByCarNum(czJsCars);
        }
    }

    @Override
    public void updateCarStatus(String plateNo, String location) {
        if(plateNo != null){
            CzJsCars czJsCars = new CzJsCars();
            czJsCars.setLicnum(plateNo);
            czJsCars.setLocation(location);
            czJsCarsMapper.updateByLicnum(czJsCars);
        }
    }

    @Override
    @Async("pool1")
    public void testThread() {
        log.info(Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
