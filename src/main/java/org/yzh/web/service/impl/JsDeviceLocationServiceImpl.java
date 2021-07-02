package org.yzh.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.transform.Bytes;
import org.yzh.framework.session.Session;
import org.yzh.protocol.commons.transform.attribute.TirePressure;
import org.yzh.protocol.t808.T0200;
import org.yzh.protocol.t808.T0201;
import org.yzh.web.mapper.JsDeviceMapper;
import org.yzh.web.mapper.JsDeviceLocationMapper;
import org.yzh.web.model.entity.JsDevice;
import org.yzh.web.model.entity.JsDeviceLocation;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.JsDeviceLocationService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsDeviceLocationServiceImpl implements JsDeviceLocationService
{

    private static final Logger log = LoggerFactory.getLogger(JsDeviceLocationServiceImpl.class.getSimpleName());

    @Autowired
    private JsDeviceLocationMapper jsDeviceLocationMapper;

    @Autowired
    private JsDeviceMapper jsDeviceMapper;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Location> find(LocationQuery query) {
        List<Location> result = jsDeviceLocationMapper.find(query);
        return result;
    }

    @Override
    public void batchInsert(List<T0200> list) {
        log.info("ok");
//        jdbcBatchInsert(list);
        mybatisBatchInsert(list);
    }

    @Override
    public void insert(T0201 request) {

    }

    private static final String sql = "insert ignore into location(device_time,device_id,mobile_no,plate_no,warning_mark,status,latitude,longitude,altitude,speed,direction,map_fence_id,create_time)values" +
            "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private void jdbcBatchInsert(List<T0200> list) {
        LocalDateTime now = LocalDateTime.now();
        Session session;
        String mobileNo, deviceId, plateNo;
        int size = list.size();
        T0200 request;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            //降低事务隔离级别，提高写入速度
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            for (int i = 0; i < size; i++) {
                request = list.get(i);
                int j = 1;
                session = request.getSession();
                mobileNo = request.getHeader().getMobileNo();
                deviceId = mobileNo;
                plateNo = "";

                DeviceInfo device = TcpClientUtils.getClientDevice(mobileNo);
                if (device != null) {
                    deviceId = device.getDeviceId();
                    plateNo = device.getPlateNo();
                }

                statement.setObject(j++, request.getDateTime());
                statement.setString(j++, deviceId);
                statement.setString(j++, mobileNo);
                statement.setString(j++, plateNo);
                statement.setInt(j++, request.getWarningMark());
                statement.setInt(j++, request.getStatus());
                statement.setInt(j++, request.getLatitude());
                statement.setInt(j++, request.getLongitude());
                statement.setInt(j++, request.getDriveSpeed());
                statement.setInt(j++, request.getStarSpeed());
                statement.setInt(j++, request.getDirection());
                statement.setInt(j++, 0);
                statement.setObject(j, now);
                statement.addBatch();
            }
            statement.executeLargeBatch();
        } catch (Exception e) {
            log.warn("批量写入失败", e);
        }
    }

    private void mybatisBatchInsert(List<T0200> list) {
        LocalDateTime now = LocalDateTime.now();
        String mobileNo, deviceId, plateNo;
        int size = list.size();
        List<JsDeviceLocation> locations = new ArrayList<>(size);
        for (T0200 request : list) {
            mobileNo = request.getHeader().getMobileNo();
            deviceId = mobileNo;
            plateNo = "";
//
            DeviceInfo device = TcpClientUtils.getClientDevice(mobileNo);
            if (device != null) {
                deviceId = device.getDeviceId();
                plateNo = device.getPlateNo();
            }

            JsDeviceLocation location = new JsDeviceLocation();
            locations.add(location);

            location.setDeviceTime(request.getDateTime());
            location.setDeviceId(deviceId);
            location.setMobileNo(mobileNo);
            location.setPlateNo(plateNo);
            location.setWarningMark(request.getWarningMark());
            location.setStatus(request.getStatus());
            location.setLatitude(request.getLatitude());
            location.setLongitude(request.getLongitude());
            location.setDriveSpeed(request.getDriveSpeed());
            location.setStarSpeed(request.getStarSpeed());
            location.setDirection(request.getDirection());
            location.setMapFenceId(0);
            location.setCreateTime(now);

            TirePressure tirePressure = (TirePressure)request.getAttributes().get(TirePressure.attributeId);
            location.setEngineSpeed(Bytes.byteArrayToInt(tirePressure.getValue()));
        }
        int row = jsDeviceLocationMapper.batchInsert(locations);
        if (row <= 0) log.warn("主键重复,写入数据库失败{}", locations);
    }
}