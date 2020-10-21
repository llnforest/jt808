package org.yzh.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.t808.*;
import org.yzh.web.endpoint.JT808Endpoint;

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

    private String mobileNo = "12345678901";

    @Mapping(types = 平台通用应答, desc = "平台通用应答")
    public T0001 平台通用应答(T0001 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 服务器补传分包请求, desc = "服务器补传分包请求")
    public T0001 服务器补传分包请求(T8003 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 查询服务器时间应答, desc = "查询服务器时间应答")
    public T0001 查询服务器时间应答(T8004 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 终端注册应答, desc = "终端注册应答")
    public T0001 终端注册应答(T8100 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 设置终端参数, desc = "设置终端参数")
    public T0001 设置终端参数(T8103 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 查询终端参数, desc = "查询终端参数")
    public T0001 查询终端参数(Header header) {
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 终端控制, desc = "终端控制")
    public T0001 终端控制(T8105 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 查询指定终端参数, desc = "查询指定终端参数")
    public T0001 查询指定终端参数(T8106 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 查询终端属性, desc = "查询终端属性")
    public T0001 查询终端属性(T0107 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 下发终端升级包, desc = "下发终端升级包")
    public T0001 下发终端升级包(T8108 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 位置信息查询, desc = "位置信息查询")
    public T0200 位置信息查询(Header header) {
        T0200 result = new T0200();
        //TODO

        return result;
    }

    @Mapping(types = 临时位置跟踪控制, desc = "临时位置跟踪控制")
    public T0001 临时位置跟踪控制(T8202 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 人工确认报警消息, desc = "人工确认报警消息")
    public T0001 人工确认报警消息(T8203 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 服务器向终端发起链路检测请求, desc = "服务器向终端发起链路检测请求")
    public T0001 服务器向终端发起链路检测请求(Header header) {
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 文本信息下发, desc = "文本信息下发")
    public T0001 文本信息下发(T8300 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 事件设置, desc = "事件设置")
    public T0001 事件设置(T8301 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 提问下发, desc = "提问下发")
    public T0001 提问下发(T8302 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 信息点播菜单设置, desc = "信息点播菜单设置")
    public T0001 信息点播菜单设置(T8303 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 信息服务, desc = "信息服务")
    public T0001 信息服务(T8304 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 电话回拨, desc = "电话回拨")
    public T0001 电话回拨(T8400 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 设置电话本, desc = "设置电话本")
    public T0001 设置电话本(T8401 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 车辆控制, desc = "车辆控制")
    public T0001 车辆控制(T8500 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = {删除圆形区域, 删除矩形区域, 删除多边形区域, 删除路线,}, desc = "删除圆形区域")
    public T0001 删除圆形区域(T8601 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 设置圆形区域, desc = "设置圆形区域")
    public T0001 设置圆形区域(T8600 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }


    @Mapping(types = 设置矩形区域, desc = "设置矩形区域")
    public T0001 设置矩形区域(T8602 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 设置多边形区域, desc = "设置多边形区域")
    public T0001 设置多边形区域(T8604 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 设置路线, desc = "设置路线")
    public T0001 设置路线(T8606 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 查询区域或线路数据, desc = "查询区域或线路数据")
    public T0001 查询区域或线路数据(T8608 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 行驶记录仪数据采集命令, desc = "行驶记录仪数据采集命令")
    public T0001 行驶记录仪数据采集命令(Header header) {
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 行驶记录仪参数下传命令, desc = "行驶记录仪参数下传命令")
    public T0001 行驶记录仪参数下传命令(T8701 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 上报驾驶员身份信息请求, desc = "上报驾驶员身份信息请求")
    public T0001 上报驾驶员身份信息请求(Header header) {
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 多媒体数据上传应答, desc = "多媒体数据上传应答")
    public T0001 多媒体数据上传应答(T8800 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 摄像头立即拍摄命令, desc = "摄像头立即拍摄命令")
    public T0001 摄像头立即拍摄命令(T8801 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 存储多媒体数据检索, desc = "存储多媒体数据检索")
    public T0001 存储多媒体数据检索(T8802 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 存储多媒体数据上传, desc = "存储多媒体数据上传")
    public T0001 存储多媒体数据上传(T8803 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 录音开始命令, desc = "录音开始命令")
    public T0001 录音开始命令(T8804 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 单条存储多媒体数据检索上传命令, desc = "单条存储多媒体数据检索上传命令")
    public T0001 单条存储多媒体数据检索上传命令(T8805 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 数据下行透传, desc = "数据下行透传")
    public T0001 数据下行透传(T8900_0900 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 平台RSA公钥, desc = "平台RSA公钥")
    public T0001 平台RSA公钥(T0A00_8A00 message) {
        Header header = message.getHeader();
        T0001 result = new T0001(mobileNo, serialNo.addAndGet(1));
        //TODO
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }
}