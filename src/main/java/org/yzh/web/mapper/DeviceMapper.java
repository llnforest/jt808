package org.yzh.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.vo.DeviceInfo;

@Repository
public interface DeviceMapper {

    DeviceDO isRegister(@Param("sn") String sn, @Param("plateNo") String  plateNo);

    DeviceDO getBySn(String sn);

    DeviceDO getByMobileNo(String mobileNo);

    int insert(DeviceDO record);

    int update(DeviceDO record);

    int delete(String deviceId);
}