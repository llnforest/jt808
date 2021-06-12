package org.yzh.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.Bytes;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.transform.Attribute;
import org.yzh.protocol.commons.transform.ParameterType;
import org.yzh.protocol.commons.transform.TerminalParameterUtils;
import org.yzh.protocol.commons.transform.attribute.*;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.StringUtil;
import org.yzh.web.endpoint.JT808Endpoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.yzh.protocol.commons.JT808.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Endpoint
public class JTClientEndpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    private AtomicInteger serialNo = new AtomicInteger();

    private String mobileNo = "17299841738";

    @Mapping(types = 平台登录应答, desc = "平台登录应答")
    public T81F0 平台登录应答(T81F0 message) {
        message.getResult();
        return null;
    }


    @Mapping(types = 平台通用应答, desc = "平台通用应答")
    public T0001 平台通用应答(T0001 message) {
//        Header header = message.getHeader();
//        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
//        //TODO
//        result.setSerialNo(header.getSerialNo());
//        result.setReplyId(header.getMessageId());
//        return result;
        return null;
    }


    //-------------------------完成调试----------------------
    private T0001 T0001(Header header){
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 查询终端参数, desc = "查询终端参数")
    public T0104 查询终端参数(Header header) {
        T0104 result = new T0104(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());

        ParameterType[] values = ParameterType.values();
        int paramNum = 0;
        for (int i = 0; i < values.length; i++) {
            ParameterType p = values[i];
            log.info("pid：{}",p.id);
            //兼容同一参数多值传递的
            switch (p.type) {
                case BYTE:
                case WORD:
                case DWORD:
                    result.addParameter(new BytesParameter(p.id, 1));
                    break;
                default:
                    result.addParameter(new BytesParameter(p.id, "abc"));
                    break;
            }
            paramNum ++;
        }
        result.setPacketNum(paramNum);
        return result;
    }

    @Mapping(types = 查询指定终端参数, desc = "查询指定终端参数")
    public T0104 查询指定终端参数(T8106 message) {
        T0104 result = new T0104(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(message.getHeader().getSerialNo());

        ParameterType[] values = ParameterType.values();
        byte[] bytes = message.getId();
        int[] idArr = Bytes.byteToIntArr(bytes);

        int paramNum = 0;
        for (int i = 0; i < values.length; i++) {
            ParameterType p = values[i];
            if(!StringUtil.intArrLookupInt(idArr,p.id)) continue;
            //兼容同一参数多值传递的
            switch (p.type) {
                case BYTE:
                case WORD:
                case DWORD:
                    result.addParameter(new BytesParameter(p.id, 1));
                    break;
                default:
                    result.addParameter(new BytesParameter(p.id, "abc"));
                    break;
            }
            paramNum ++;
        }
        result.setPacketNum(paramNum);
        return result;
    }


    @Mapping(types = 设置终端参数, desc = "设置终端参数")
    public T0001 设置终端参数(T8103 message) {
        log.info("设置终端参数：{}",message);
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        List<BytesParameter> list = message.getBytesParameters();
        for(int i = 0; i < list.size();i ++){
            BytesParameter p = list.get(i);
            log.info("id:{}",p.getId());
            log.info("value:{}", TerminalParameterUtils.toValue(p.getId(),p.getValue()));
        }
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 命令上报学时记录, desc = "命令上报学时记录")
    public T8900_0900_time_up_command_answer 命令上报学时记录应答(T8900_0900_time_up_command message) {
        T8900_0900_time_up_command_answer result = new T8900_0900_time_up_command_answer(mobileNo, serialNo.addAndGet(1));
        result.setTerminalNo(message.getTerminalNo());
        result.setResult(1);
        return result;
    }

    @Mapping(types = 设置禁训状态, desc = "设置禁训状态")
    public T8900_0900_terminal_status_answer 设置禁训状态应答(T8900_0900_terminal_status message) {
        T8900_0900_terminal_status_answer result = new T8900_0900_terminal_status_answer(mobileNo, serialNo.addAndGet(1));
        result.setTerminalNo(message.getTerminalNo());
        result.setResult(1);
        result.setStatus(message.getStatus());
        result.setMsgContent("操作成功");
        log.info("设置禁训状态：{}",result);

        return result;
    }

    @Mapping(types = 查询计时终端应用参数, desc = "查询计时终端应用参数")
    public T8900_0900_terminal_param_search_answer 设置计时终端应用参数应答(T8900_0900_terminal_param_search message) {
        T8900_0900_terminal_param_search_answer result = new T8900_0900_terminal_param_search_answer(mobileNo, serialNo.addAndGet(1));
        result.setTerminalNo(message.getTerminalNo());
        result.setResult(1);//0默认应答 1成功 2失败 9其他错误
        result.setPhotoTime(1);
        result.setUpSet(1);
        result.setIsReadAdd(1);
        result.setClassDelayTime(3);
        result.setUpTime(0);
        result.setCoachDelayTime(30);
        result.setVerifyTime(30);
        result.setIsCoachAcross(1);
        result.setIsStudentAcross(1);
        result.setResponseTime(50);
        return result;
    }

    @Mapping(types = 终端控制, desc = "终端控制")
    public T0001 终端控制(T8105 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 位置信息查询, desc = "位置信息查询")
    public T0201 位置信息查询应答(T8201 message) {
        T0201 result = new T0201();
        result.setWarningMark(Integer.parseInt("00001001100110100001010100011100",2));
        result.setStatus(Integer.parseInt("100011100",2));
        result.setLatitude(116307629);
        result.setLongitude(40058359);
        result.setDriveSpeed(5);
        result.setStarSpeed(3);
        result.setDirection(99);
        result.setDateTime( LocalDateTime.of(2020, 7, 7, 19, 23, 59));
        Map<Integer, Attribute> attributes = new TreeMap();
        attributes.put(Mileage.attributeId, new Mileage(11));
        attributes.put(Oil.attributeId, new Oil(22));
        attributes.put(Speed.attributeId, new Speed(33));
        attributes.put(AlarmEventId.attributeId, new AlarmEventId(44));
        attributes.put(TirePressure.attributeId, new TirePressure((byte) 55, (byte) 55, (byte) 55));
        attributes.put(CarriageTemperature.attributeId, new CarriageTemperature(2));
        attributes.put(OverSpeedAlarm.attributeId, new OverSpeedAlarm((byte) 66, 66));
        attributes.put(InOutAreaAlarm.attributeId, new InOutAreaAlarm((byte) 77, 77, (byte) 77));
        attributes.put(RouteDriveTimeAlarm.attributeId, new RouteDriveTimeAlarm(88, 88, (byte) 88));
        attributes.put(Signal.attributeId, new Signal(99));
        attributes.put(IoState.attributeId, new IoState(10));
        attributes.put(AnalogQuantity.attributeId, new AnalogQuantity(20));
        attributes.put(SignalStrength.attributeId, new SignalStrength(30));
        attributes.put(GnssCount.attributeId, new GnssCount(40));
        result.setAttributes(attributes);
        return result;
    }

    @Mapping(types = 临时位置跟踪控制, desc = "临时位置跟踪控制")
    public T0001 临时位置跟踪控制(T8202 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 立即拍照, desc = "立即拍照")
    public T8900_0900_photo_command_answer 立即拍照应答(T8900_0900_photo_command message) {
        T8900_0900_photo_command_answer result = new T8900_0900_photo_command_answer(mobileNo, serialNo.addAndGet(1));
        result.setTerminalNo(message.getTerminalNo());
        result.setResult(1);
        result.setUpMode(message.getUpMode());
        result.setChannelNo(2);
        result.setPhotoSize(0x02);
        return  result;
    }

    @Mapping(types = 查询照片, desc = "查询照片")
    public T8900_0900_photo_search_command_answer 查询照片应答(T8900_0900_photo_search_command message) {
        T8900_0900_photo_search_command_answer result = new T8900_0900_photo_search_command_answer(mobileNo, serialNo.addAndGet(1));
        result.setTerminalNo(message.getTerminalNo());
        result.setResult(1);
        return  result;
    }

    @Mapping(types = 上传指定照片, desc = "上传指定照片")
    public T8900_0900_photo_up_only_answer 上传指定照片应答(T8900_0900_photo_up_only message) {
        T8900_0900_photo_up_only_answer result = new T8900_0900_photo_up_only_answer(mobileNo, serialNo.addAndGet(1));
        result.setTerminalNo(message.getTerminalNo());
        result.setResult(0);
        return  result;
    }

    @Mapping(types = 上报照片查询结果应答, desc = "上报照片查询结果应答")
    public void 上报照片查询结果应答(T8900_0900_photo_search_result_up_answer message) {
        //TODO:上报照片查询结果应答逻辑
        log.info("收到返回的上报照片查询结果应答结果：{}",message.getResult());
    }

    @Mapping(types = 照片上传初始化应答, desc = "照片上传初始化应答")
    public void 上传指定照片应答(T8900_0900_photo_up_init_answer message) {
        //TODO:照片上传初始化应答逻辑
        log.info("收到返回的初始化应答结果：{}",message.getResult());

    }

    //-------------------------待调试----------------------

    @Mapping(types = 服务器补传分包请求, desc = "服务器补传分包请求")
    public T0001 服务器补传分包请求(T8003 message) {
        return  this.T0001(message.getHeader());

    }

    @Mapping(types = 查询服务器时间应答, desc = "查询服务器时间应答")
    public T0001 查询服务器时间应答(T8004 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 查询终端属性, desc = "查询终端属性")
    public T0001 查询终端属性(T0107 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 下发终端升级包, desc = "下发终端升级包")
    public T0001 下发终端升级包(T8108 message) {
        return  this.T0001(message.getHeader());
    }



    @Mapping(types = 人工确认报警消息, desc = "人工确认报警消息")
    public T0001 人工确认报警消息(T8203 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 服务器向终端发起链路检测请求, desc = "服务器向终端发起链路检测请求")
    public T0001 服务器向终端发起链路检测请求(Header header) {
        return  this.T0001(header);
    }

    @Mapping(types = 文本信息下发, desc = "文本信息下发")
    public T0001 文本信息下发(T8300 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 事件设置, desc = "事件设置")
    public T0001 事件设置(T8301 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 提问下发, desc = "提问下发")
    public T0001 提问下发(T8302 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 信息点播菜单设置, desc = "信息点播菜单设置")
    public T0001 信息点播菜单设置(T8303 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 信息服务, desc = "信息服务")
    public T0001 信息服务(T8304 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 电话回拨, desc = "电话回拨")
    public T0001 电话回拨(T8400 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 设置电话本, desc = "设置电话本")
    public T0001 设置电话本(T8401 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 车辆控制, desc = "车辆控制")
    public T0001 车辆控制(T8500 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = {删除圆形区域, 删除矩形区域, 删除多边形区域, 删除路线,}, desc = "删除圆形区域")
    public T0001 删除圆形区域(T8601 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 设置圆形区域, desc = "设置圆形区域")
    public T0001 设置圆形区域(T8600 message) {
        return  this.T0001(message.getHeader());
    }


    @Mapping(types = 设置矩形区域, desc = "设置矩形区域")
    public T0001 设置矩形区域(T8602 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 设置多边形区域, desc = "设置多边形区域")
    public T0001 设置多边形区域(T8604 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 设置路线, desc = "设置路线")
    public T0001 设置路线(T8606 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 查询区域或线路数据, desc = "查询区域或线路数据")
    public T0001 查询区域或线路数据(T8608 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 行驶记录仪数据采集命令, desc = "行驶记录仪数据采集命令")
    public T0001 行驶记录仪数据采集命令(Header header) {
        return  this.T0001(header);
    }

    @Mapping(types = 行驶记录仪参数下传命令, desc = "行驶记录仪参数下传命令")
    public T0001 行驶记录仪参数下传命令(T8701 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 上报驾驶员身份信息请求, desc = "上报驾驶员身份信息请求")
    public T0001 上报驾驶员身份信息请求(Header header) {
        return  this.T0001(header);
    }

    @Mapping(types = 多媒体数据上传应答, desc = "多媒体数据上传应答")
    public T0001 多媒体数据上传应答(T8800 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 摄像头立即拍摄命令, desc = "摄像头立即拍摄命令")
    public T0001 摄像头立即拍摄命令(T8801 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 存储多媒体数据检索, desc = "存储多媒体数据检索")
    public T0001 存储多媒体数据检索(T8802 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 存储多媒体数据上传, desc = "存储多媒体数据上传")
    public T0001 存储多媒体数据上传(T8803 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 录音开始命令, desc = "录音开始命令")
    public T0001 录音开始命令(T8804 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 单条存储多媒体数据检索上传命令, desc = "单条存储多媒体数据检索上传命令")
    public T0001 单条存储多媒体数据检索上传命令(T8805 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 数据下行透传, desc = "数据下行透传")
    public T0001 数据下行透传(T8900_0900 message) {
        return  this.T0001(message.getHeader());
    }

    @Mapping(types = 平台RSA公钥, desc = "平台RSA公钥")
    public T0001 平台RSA公钥(T0A00_8A00 message) {
        return  this.T0001(message.getHeader());
    }
}