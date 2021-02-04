package org.yzh.web.endpoint;

import com.sun.xml.internal.bind.v2.TODO;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.WsHandlerUtils;
import org.yzh.framework.mvc.annotation.AsyncBatch;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.model.ResponseModel;
import org.yzh.web.model.entity.JsClassrecordUp;
import org.yzh.web.reply.*;
import org.yzh.web.service.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.yzh.protocol.commons.JT808.*;

@Endpoint
@Component
public class JT808Endpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceService deviceService;

    //-----------------------公共部分---------------------------

    @Mapping(types = 终端心跳, desc = "终端心跳")
    public Object heartBeat(Header header, Session session) {
        log.info("心跳{}",header);
        log.info("收到心跳");
        return null;
    }
    //-----------------------已完成---------------------------

    @Mapping(types = 终端通用应答, desc = "终端通用应答")
    public Object 终端通用应答(T0001 message,Session session) {
        log.info("终端通用应答");
        Channel channel = null;
        String phone = message.getHeader().getMobileNo();
        ResponseModel responseModel = null;
        switch (message.getReplyId()){
            case 33027:
                //设置终端参数
                log.info("设置终端参数应答");
                channel = Msg0x8103.getMsg(phone);
                TerminalConfigService service = BeanHelper.getBean("terminalConfigServiceImpl");
                if(message.getResultCode() == 0){
                    //成功
                    service.updateTerminalConfigStatus(phone,2);
                    responseModel = new ResponseModel("终端设置参数成功");
                }else{
                    //1失败 2消息有误 3不支持
                    service.updateTerminalConfigStatus(phone,3);
                    responseModel = new ResponseModel("1","终端设置参数失败");

                }
                break;
            case 33029:
                //终端控制
                log.info("终端控制");
                channel = Msg0x8105.getMsg(phone);
                if(message.getResultCode() == 0){
                    //成功
                    responseModel = new ResponseModel("终端控制成功");
                }else{
                    //1失败 2消息有误 3不支持
                    responseModel = new ResponseModel("1","终端控制失败");

                }
                break;
            case 33282:
                //临时位置跟踪控制
                log.info("临时位置跟踪控制");
                channel = Msg0x8202.getMsg(phone);
                if(message.getResultCode() == 0){
                    //成功
                    responseModel = new ResponseModel("临时位置跟踪控制成功");
                }else{
                    //1失败 2消息有误 3不支持
                    responseModel = new ResponseModel("1","临时位置跟踪控制失败");

                }
                break;
            default:
                break;
        }

        WsHandlerUtils.write(channel,responseModel);
        Msg0x8103.delMsg(phone);


        return null;
    }

    @Mapping(types = 查询终端参数应答, desc = "查询终端参数")
    public void 查询终端参数应答(T0104 request,Session session) {
        log.info("查询终端参数应答",request);
        String phone = request.getHeader().getMobileNo();
        Map<String,String> dataMap = new HashMap<>();
        for(Map.Entry<Integer,String> entry:request.getParameters().entrySet()){
            dataMap.put("0x"+ StringUtils.leftPad(Integer.toHexString(entry.getKey()),4,"0"),entry.getValue());
        }
        ResponseModel responseModel = new ResponseModel("0","查询终端参数成功!",dataMap);

        Channel channel = Msg0x8104.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8104.delMsg(phone);

    }

    @Mapping(types = 设置禁训状态应答, desc = "设置禁训状态应答")
    public void 设置禁训状态应答(T8900_0900_terminal_status_answer request, Session session) {
        log.info("设置禁训状态应答");
        String phone = request.getHeader().getMobileNo();
        ResponseModel responseModel = null;
        TerminalStatusService service = BeanHelper.getBean("terminalStatusServiceImpl");
        if(request.getResult() == 1 || request.getResult() == 0){
            //0默认 1成功
            service.updateTerminalStatusStatus(phone,2,request);
            responseModel = new ResponseModel("终端设置禁训状态成功!"+request.getMsgContent());
        }else{
            //2失败 9其他错误
            service.updateTerminalStatusStatus(phone,3,request);
            responseModel = new ResponseModel("1","终端设置禁训状态失败!"+request.getMsgContent());

        }
        Channel channel = Msg0x8502.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8502.delMsg(phone);

    }

    @Mapping(types = 设置计时终端应用参数应答, desc = "设置计时终端应用参数应答")
    public void 设置计时终端应用参数应答(T8900_0900_terminal_set_answer request, Session session) {
        log.info("设置计时终端应用参数应答");
        String phone = request.getHeader().getMobileNo();
        ResponseModel responseModel = null;
        TerminalAppConfigService service = BeanHelper.getBean("terminalAppConfigServiceImpl");
        if(request.getResult() == 1 || request.getResult() == 0){
            //0默认 1成功
            service.updateTerminalAppConfigStatus(phone,2);
            responseModel = new ResponseModel("终端设置应用参数成功!");
        }else{
            //2失败 9其他错误
            service.updateTerminalAppConfigStatus(phone,3);
            responseModel = new ResponseModel("1","终端设置应用参数失败!");

        }
        Channel channel = Msg0x8501.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8501.delMsg(phone);

    }

    @Mapping(types = 查询计时终端应用参数应答, desc = "查询计时终端应用参数应答")
    public void 查询计时终端应用参数应答(T8900_0900_terminal_param_search_answer request, Session session) {
        log.info("查询计时终端应用参数应答");
        String phone = request.getHeader().getMobileNo();
        ResponseModel responseModel = null;
        if(request.getResult() == 1 || request.getResult() == 0){
            //0默认 1成功
            Map<String,Integer> dataMap = new HashMap<>();
            dataMap.put("photoTime",request.getPhotoTime());
            dataMap.put("upSet",request.getUpSet());
            dataMap.put("isReadAdd",request.getIsReadAdd());
            dataMap.put("classDelayTime",request.getClassDelayTime());
            dataMap.put("upTime",request.getUpTime());
            dataMap.put("coachDelayTime",request.getCoachDelayTime());
            dataMap.put("verifyTime",request.getVerifyTime());
            dataMap.put("isCoachAcross",request.getIsCoachAcross());
            dataMap.put("isStudentAcross",request.getIsStudentAcross());
            dataMap.put("responseTime",request.getResponseTime());
            responseModel = new ResponseModel("0","查询计时终端应用参数成功!",dataMap);
        }else{
            //2失败 9其他错误
            responseModel = new ResponseModel("1","查询计时终端应用参数失败!");

        }
        Channel channel = Msg0x8503.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8503.delMsg(phone);

    }

    @Mapping(types = 上报教练员登录, desc = "上报教练员登录")
    public T8900_0900_coach_login_answer 上报教练员登录(T8900_0900_coach_login request, Session session) {
        log.info("ok");
        Header header = request.getHeader();
        CoachService service = (CoachService) BeanHelper.getBean("coachServiceImpl");

        int loginResult = service.coachLogin(request);

        T8900_0900_coach_login_answer result = new T8900_0900_coach_login_answer();
        result.setLoginResult(loginResult);
        result.setCoachNo(request.getCoachNo());
        result.setIsRead(0);

        return result;
    }

    @Mapping(types = 上报教练员登出, desc = "上报教练员登出")
    public T8900_0900_coach_logout_answer 上报教练员登出(T8900_0900_coach_logout request, Session session) {
        Header header = request.getHeader();
        CoachService service = (CoachService) BeanHelper.getBean("coachServiceImpl");

        int loginResult = service.coachLogout(request);

        T8900_0900_coach_logout_answer result = new T8900_0900_coach_logout_answer();
        result.setLoginResult(loginResult);
        result.setCoachNo(request.getCoachNo());
        return result;
    }

    @Mapping(types = 上报学员登录, desc = "上报学员登录")
    public T8900_0900_student_login_answer 上报学员登录(T8900_0900_student_login request, Session session) {
        StudentService service = (StudentService) BeanHelper.getBean("studentServiceImpl");

        T8900_0900_student_login_answer result = service.studentLogin(request);
        return result;
    }
    //
    @Mapping(types = 上报学员登出, desc = "上报学员登出")
    public T8900_0900_student_logout_answer 上报学员登出(T8900_0900_student_logout request, Session session) {
        StudentService service = (StudentService) BeanHelper.getBean("studentServiceImpl");

        T8900_0900_student_logout_answer result = service.studentLogout(request);
        return result;
    }

    @Mapping(types = 上报学时记录, desc = "上报学时记录")
    public void 上报学时记录(T8900_0900_time_up request, Session session) {
        Header header = request.getHeader();
        ClassRecordUpService service = (ClassRecordUpService) BeanHelper.getBean("classRecordUpServiceImpl");
        JsClassrecordUp result = service.addRecord(request);
    }

    @Mapping(types = 命令上报学时记录应答, desc = "命令上报学时记录应答")
    public void 命令上报学时记录应答(T8900_0900_time_up_command_answer request, Session session) {
        String phone = request.getHeader().getMobileNo();
        ResponseModel responseModel = null;
        if(request.getResult() == 1){
            responseModel = new ResponseModel("执行成功，查询的记录正在上传");
        }else if(request.getResult() == 2){
            responseModel = new ResponseModel("1","SD卡没有找到!");
        }else if(request.getResult() == 3){
            responseModel = new ResponseModel("执行成功，但无指定记录!");
        }else if(request.getResult() == 4){
            responseModel = new ResponseModel("执行成功，稍候上报查询结果");
        }else if(request.getResult() == 9){
            responseModel = new ResponseModel("1","其他错误!");
        }
        Channel channel = Msg0x8205.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8205.delMsg(phone);
    }

    @Mapping(types = 终端注册, desc = "终端注册")
    public T8100 register(T0100 message, Session session) {
        Header header = message.getHeader();
//        返回终端注册应答
        T8100 result = new T8100(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(header.getSerialNo());
        //TODO:处理终端注册的相关业务逻辑
        deviceService.register(message,result);

//        ClientChannelUtils.getClient().writeObject(message);//转发
        return result;
    }

    @Mapping(types = 终端注销, desc = "终端注销")
    public T0001 终端注销(Header header, Session session) {
        T0001 result = new T0001(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        Boolean isLogout = deviceService.logout(header.getMobileNo());
        if (isLogout) {
            result.setResultCode(T0001.Success);
            return result;
        }
        result.setResultCode(T0001.Failure);
        return result;
    }

    @Mapping(types = 终端鉴权, desc = "终端鉴权")
    public T0001 终端鉴权(T0102 request, Session session) {
        Header header = request.getHeader();

        T0001 result = new T0001(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        //TODO:处理终端注册的相关业务逻辑
        log.info("终端鉴权，{}{}", session.getChannel(), request);

        Boolean isAuth = deviceService.authentication(request);
        if (isAuth) {
            log.info("终端鉴权成功");
            TcpClientUtils.setClientChannel(header.getMobileNo(),session.getChannel());
//            session.register(header, device);
            result.setResultCode(T0001.Success);
            return result;
        }
        log.warn("终端鉴权失败，{}{}", session, request);
        result.setResultCode(T0001.Failure);
        return result;
    }

    //异步批量处理默认 4线程 最大累积100条记录处理一次 最大等待时间1秒
    @AsyncBatch
    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public void 位置信息汇报(List<T0200> list) {
        locationService.batchInsert(list);
    }

    @AsyncBatch
    @Mapping(types = 位置信息查询应答, desc = "位置信息查询应答")
    public void 位置信息查询(List<T0200> list) {
        T0200 t0200 = list.get(0);
        locationService.batchInsert(list);
        Channel channel = Msg0x8201.getMsg(t0200.getHeader().getMobileNo());
        ResponseModel responseModel = new ResponseModel("执行成功，查询的记录正在上传");
        Msg0x8205.delMsg(t0200.getHeader().getMobileNo());
        WsHandlerUtils.write(channel,responseModel);
    }

    @Mapping(types = 立即拍照应答, desc = "立即拍照应答")
    public void 立即拍照应答(T8900_0900_photo_command_answer request, Session session) {
        String phone = request.getHeader().getMobileNo();
        int code = request.getResult();
        request.getChannelNo();
        request.getPhotoSize();
        request.getUpMode();
        ResponseModel responseModel = null;
        if (code == 1){
            responseModel = new ResponseModel("可以拍摄");
        }else if(code == 2){
            responseModel = new ResponseModel("1","拍摄失败");
        }else if(code == 3){
            responseModel = new ResponseModel("1","SD卡故障");
        }else if(code == 4){
            responseModel = new ResponseModel("1","正在拍照，不能执行");
        }else if(code == 5){
            responseModel = new ResponseModel("1","重新连接摄像头，不能保证拍照");
        }else if(code == 6){
            responseModel = new ResponseModel("1","正在上传查询照片，不能执行");
        }else{
            responseModel = new ResponseModel("1","其他错误");
        }
        Channel channel = Msg0x8301.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8301.delMsg(phone);
    }


    @Mapping(types = 查询照片应答, desc = "查询照片应答")
    public void 查询照片应答(T8900_0900_photo_search_command_answer request, Session session) {
        String phone = request.getHeader().getMobileNo();
        int code = request.getResult();
        ResponseModel responseModel = null;
        if (code == 1){
            responseModel = new ResponseModel("开始查询");
        }else if(code == 2){
            responseModel = new ResponseModel("1","执行失败");
        }else{
            responseModel = new ResponseModel("1","其他错误");
        }
        Channel channel = Msg0x8302.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8302.delMsg(phone);
    }


    @Mapping(types = 上报照片查询结果, desc = "上报照片查询结果")
    public T8900_0900_photo_search_result_up_answer 上报照片查询结果应答(T8900_0900_photo_search_result_up request, Session session) {
        Header header = request.getHeader();
        String phone = header.getMobileNo();
        T8900_0900_photo_search_result_up_answer answer = new T8900_0900_photo_search_result_up_answer(header.getMobileNo(),header.getSerialNo());
        //TODO:获得上报图片查询结果逻辑

        answer.setTerminalNo(request.getTerminalNo());
        answer.setResult(0);
        return answer;// 0：默认应答；1：停止上报，终端收到“停止上报”应答后则停止查询结果的上报；9：其他错误

    }

    @Mapping(types = 上传指定照片应答, desc = "上传指定照片应答")
    public void 上传指定照片应答(T8900_0900_photo_up_only_answer request, Session session) {
        String phone = request.getHeader().getMobileNo();
        int code = request.getResult();
        ResponseModel responseModel = null;
        if (code == 0){
            responseModel = new ResponseModel("找到照片，稍候上传");
        }else if(code == 1){
            responseModel = new ResponseModel("1","没有该照片");
        }else{
            responseModel = new ResponseModel("1","其他错误");
        }
        Channel channel = Msg0x8304.getMsg(phone);
        WsHandlerUtils.write(channel,responseModel);
        Msg0x8304.delMsg(phone);
    }

    @Mapping(types = 照片上传初始化, desc = "照片上传初始化")
    public T8900_0900_photo_up_init_answer 照片上传初始化应答(T8900_0900_photo_up_init request, Session session) {
        Header header = request.getHeader();
        request.getPhotoNum();
        //TODO:获得照片上传初始化逻辑

        T8900_0900_photo_up_init_answer  answer = new T8900_0900_photo_up_init_answer(header.getMobileNo(),header.getSerialNo());
        answer.setTerminalNo(request.getTerminalNo());

        answer.setResult(0);
        return answer;//0:接受上传 1:照片编号重复或错误  9: 其他错误  255:拒绝上传，终端收到“拒绝上传”应答后则停止此照片的上传
    }

    @Mapping(types = 照片上传数据包, desc = "照片上传数据包")
    public void 上传照片数据包(T8900_0900_photo_up_data request, Session session) {
        Header header = request.getHeader();
        request.getPhotoNum();
        request.getPhotoData();//照片数据
        //TODO:处理图片上传
        try (FileOutputStream fileOuputStream = new FileOutputStream("F://jt808_img/demo1.txt")) {
            fileOuputStream.write(request.getPhotoData());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //-----------------------待完成---------------------------






    //--------------------------------



    @Mapping(types = 数据上行透传, desc = "数据上行透传")
    public T8900_0900 数据上行透传(T8900_0900 request, Session session) {
        Header header = request.getHeader();
        log.info("type：{}",request.getType());
        T8900_0900_content content = request.getContent();
        int msgId = content.getMsgId();
        if(msgId == 257){
            T8900_0900_coach_up t8900_0900_coach_up = content.getT8900_0900_coach_up();
            log.info("教练编号：{}",t8900_0900_coach_up.getCoachNo());
            log.info("身份证号：{}",t8900_0900_coach_up.getCoachIdentity());
            log.info("车型：{}",t8900_0900_coach_up.getCoachType());
            T0200 t0200 = t8900_0900_coach_up.getT0200();
            log.info("行驶速度：{}",t0200.getDriveSpeed());
        }
        log.info("msgId：{}",content.getMsgId());
        log.info("terminalNo:{}",content.getTerminalNo());
        return null;
    }

















    @Mapping(types = 查询服务器时间, desc = "查询服务器时间")
    public T8004 查询服务器时间(Header header, Session session) {
        T8004 result = new T8004(DateUtils.yyMMddHHmmss.format(new Date(System.currentTimeMillis() + 50)));
        result.setHeader(new Header(Integer.parseInt(查询服务器时间应答.substring(2),16), session.nextSerialNo(), header.getClientId()));
        return result;
    }

    @Mapping(types = 终端补传分包请求, desc = "终端补传分包请求")
    public void 终端补传分包请求(T8003 message, Session session) {
        Header header = message.getHeader();
    }






    @Mapping(types = 查询终端属性应答, desc = "查询终端属性应答")
    public void 查询终端属性应答(T0107 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        messageManager.response(message);
    }

    @Mapping(types = 终端升级结果通知, desc = "终端升级结果通知")
    public void 终端升级结果通知(T0108 message, Session session) {
        Header header = message.getHeader();
    }


    @Mapping(types = 定位数据批量上传, desc = "定位数据批量上传")
    public void 定位数据批量上传(T0704 message) {
        Header header = message.getHeader();
        List<T0704.Item> items = message.getItems();
        List<T0200> list = new ArrayList<>(items.size());
        for (T0704.Item item : items) {
            T0200 position = item.getPosition();
            position.setHeader(header);
            list.add(position);
        }
        locationService.batchInsert(list);
    }



    @Mapping(types = 事件报告, desc = "事件报告")
    public void 事件报告(T0301 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 提问应答, desc = "提问应答")
    public void 提问应答(T0302 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 信息点播_取消, desc = "信息点播/取消")
    public void 信息点播取消(T0303 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 查询区域或线路数据应答, desc = "查询区域或线路数据应答")
    public void 查询区域或线路数据应答(AbstractMessage message, Session session) {
        Header header = (Header) message.getHeader();
    }

    @Mapping(types = 行驶记录数据上传, desc = "行驶记录仪数据上传")
    public void 行驶记录仪数据上传(T0700 message, Session session) {
        Header header = (Header) message.getHeader();
    }

    @Mapping(types = 电子运单上报, desc = "电子运单上报")
    public void 电子运单上报(AbstractMessage message, Session session) {
        Header header = (Header) message.getHeader();
    }

    @Mapping(types = 驾驶员身份信息采集上报, desc = "驾驶员身份信息采集上报")
    public void 驾驶员身份信息采集上报(T0702 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = CAN总线数据上传, desc = "定位数据批量上传")
    public void CAN总线数据上传(T0705 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 多媒体事件信息上传, desc = "多媒体事件信息上传")
    public void 多媒体事件信息上传(T0800 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 多媒体数据上传, desc = "多媒体数据上传")
    public T8800 多媒体数据上传(T0801 message, Session session) throws IOException {
        Header header = message.getHeader();
        byte[] packet = message.getPacket();
        FileOutputStream fos = new FileOutputStream("D://test.jpg");
        fos.write(packet);
        fos.close();
        T8800 result = new T8800();
        result.setHeader(new Header(Integer.parseInt(平台通用应答.substring(2),16), session.nextSerialNo(), header.getMobileNo()));
        result.setMediaId(1);
        return result;
    }

    @Mapping(types = 存储多媒体数据检索应答, desc = "存储多媒体数据检索应答")
    public void 存储多媒体数据检索应答(T0802 message, Session session) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        Integer replyId = header.getSerialNo();
        messageManager.response(message);
    }

    @Mapping(types = 摄像头立即拍摄命令应答, desc = "摄像头立即拍摄命令应答")
    public void 摄像头立即拍摄命令应答(T0805 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        Integer replyId = header.getSerialNo();
        messageManager.response(message);
    }


    @Mapping(types = 数据压缩上报, desc = "数据压缩上报")
    public void gzipPack(T0901 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 终端RSA公钥, desc = "终端RSA公钥")
    public void 终端RSA公钥(T0A00_8A00 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        messageManager.response(message);
    }
}