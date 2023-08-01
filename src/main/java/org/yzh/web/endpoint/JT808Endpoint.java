package org.yzh.web.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.annotation.AsyncBatch;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.service.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.yzh.protocol.commons.JT808.*;

@Endpoint
@Component
public class JT808Endpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());
    private AtomicInteger serialNo = new AtomicInteger();

    //-----------------------公共部分---------------------------

    @Mapping(types = 终端心跳, desc = "终端心跳")
    public void heartBeat(Header header, Session session) {
        log.info("心跳{}", header);
//        TcpClientUtils.setClientChannel(header.getMobileNo(),session.getChannel());
    }

    //-----------------------已完成---------------------------
    @Mapping(types = 平台登录请求, desc = "平台登录请求")
    public T81F0 平台登录请求(T01F0 request, Session session) {
        T81F0 t81F0 = new T81F0(request.getHeader().getMobileNo(), serialNo.addAndGet(1));
        CzTcpPlatsService service = BeanHelper.getBean(CzTcpPlatsService.class);
        int errorCode = service.handleLogin(request, session);
        t81F0.setResult(errorCode);
        return t81F0;
    }

    @Mapping(types = 平台登出请求, desc = "平台登出请求")
    public void 平台登出请求(T01F1 request, Session session) {
        CzTcpPlatsService service = BeanHelper.getBean(CzTcpPlatsService.class);
        service.handleLogout(request, session);
    }

    @Mapping(types = 上报教练员登录, desc = "上报教练员登录")
    public void 上报教练员登录(T8900_0900_coach_login request, Session session) {
        CzTcpCoachLoginsService service = BeanHelper.getBean(CzTcpCoachLoginsService.class);
        service.insert(request);
    }

    @Mapping(types = 上报教练员登出, desc = "上报教练员登出")
    public void 上报教练员登出(T8900_0900_coach_logout request, Session session) {
        CzTcpCoachLogoutsService service = BeanHelper.getBean(CzTcpCoachLogoutsService.class);
        service.insert(request);
    }

    @Mapping(types = 上报学员登录, desc = "上报学员登录")
    public void 上报学员登录(T8900_0900_student_login request, Session session) {
        CzTcpStudentLoginsService service = BeanHelper.getBean(CzTcpStudentLoginsService.class);
        service.insert(request);
    }

    //
    @Mapping(types = 上报学员登出, desc = "上报学员登出")
    public void 上报学员登出(T8900_0900_student_logout request, Session session) {
        CzTcpStudentLogoutsService service = BeanHelper.getBean(CzTcpStudentLogoutsService.class);
        service.insert(request);
    }

    @Mapping(types = 上报学时记录, desc = "上报学时记录")
    public void 上报学时记录(T8900_0900_time_up request, Session session) {
        CzJsTeachTimesService service = BeanHelper.getBean(CzJsTeachTimesService.class);
        service.insert(request);
    }


    @Mapping(types = 终端注册, desc = "终端注册")
    public void 终端注册(T0100 request, Session session) {
        CzTcpTerminalRegistersService service = BeanHelper.getBean(CzTcpTerminalRegistersService.class);
        service.insert(request);
    }

    @Mapping(types = 终端注销, desc = "终端注销")
    public void 终端注销(Header header, Session session) {
        CzTcpTerminalLogoutsService service = BeanHelper.getBean(CzTcpTerminalLogoutsService.class);
        service.insert(header);
    }

    @Mapping(types = 终端鉴权, desc = "终端鉴权")
    public void 终端鉴权(T0102 request, Session session) {
        CzTcpTerminalAuthsService service = BeanHelper.getBean(CzTcpTerminalAuthsService.class);
        service.insert(request);
    }


    //异步批量处理默认 4线程 最大累积100条记录处理一次 最大等待时间1秒
    @AsyncBatch
    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public void 位置信息汇报(List<T0200> list) {
        CzTcpDeviceLocationsService service = BeanHelper.getBean(CzTcpDeviceLocationsService.class);
        service.batchInsert(list);
    }


    @Mapping(types = 照片上传初始化, desc = "照片上传初始化")
    public void 照片上传初始化应答(T8900_0900_photo_up_init request, Session session) {
        CzTcpPhotoUpInitsService service = BeanHelper.getBean(CzTcpPhotoUpInitsService.class);
        service.insert(request);
    }

    @Mapping(types = 照片上传数据包, desc = "照片上传数据包")
    public void 上传照片数据包(T8900_0900_photo_up_data request, Session session) {
        CzTcpPhotoUpDatasService service = BeanHelper.getBean(CzTcpPhotoUpDatasService.class);
        service.insert(request);
    }


}