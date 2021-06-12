package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.JsDevice;

@Repository
public interface JsDeviceMapper {

    JsDevice isCanRegister(@Param("sn") String sn, @Param("bindSim") String  sim);

//    JsDevice isExistsDev(@Param("sn") String sn, @Param("bindSim") String  sim);
//    JsDevice isRegisterDev(String sn);
//    JsDevice isExistsCar(@Param("plateColor") int plateColor, @Param("plateNo") String  plateNo);
//    JsDevice isRegisterCar(@Param("plateColor") int plateColor, @Param("plateNo") String  plateNo);
    JsDevice isRegisterCar(String carnum);

    JsDevice getByDevnum(String devnum);


    JsDevice getByMobile(String mobile);

    int insert(JsDevice record);

    int updateByPrimaryKeySelective(JsDevice record);

    int delete(String deviceId);
}