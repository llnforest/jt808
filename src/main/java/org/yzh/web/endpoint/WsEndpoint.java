package org.yzh.web.endpoint;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.WsHandlerUtils;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.model.ResponseModel;
import org.yzh.web.protocol.JT808Beans;
import org.yzh.web.reply.*;
import org.yzh.web.service.TerminalAppConfigService;
import org.yzh.web.service.TerminalConfigService;
import org.yzh.web.service.TerminalStatusService;

import java.util.Map;

/**
 * websocket通信
 */
public class WsEndpoint {
    private static final Logger log = LoggerFactory.getLogger(WsEndpoint.class.getSimpleName());

    /**
     * 终端心跳
     * @param map
     * @return
     */
    public void _0x0002(Map<String,Object> map,Channel channel){

        log.info("map:{}",map);
    }

    /**
     * 终端鉴权
     * @param map
     * @return
     */
    public ResponseModel _0x0102(Map<String,Object> map,Channel channel){
        Map<String,String> data = (Map<String, String>) map.get("data");
        long nowTimeStamp = System.currentTimeMillis() + 8*3600*1000;
        long frontTimeStamp = Long.parseLong(data.get("timestamp"));
        if(Math.abs(nowTimeStamp - frontTimeStamp) < 30000){
            return new ResponseModel("1","时间戳错误，鉴权失败");
        }
        String key = "LyxlvGAqiQu9qzZ3ohCd0urcN3ibuFvL";
        String sign = "key="+key+"&plat_id="+data.get("plat_id")+"&timestamp="+data.get("timestamp");
        String token = DigestUtils.md5DigestAsHex(sign.getBytes());

        if(token.equals(data.get("token"))){
            WsHandlerUtils.setWsChannelMap(channel);
            return new ResponseModel();
        }else{
            return new ResponseModel("1","鉴权失败");
        }
    }

    /**
     * 设置终端参数
     * @param map
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8103( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            TerminalConfigService service = BeanHelper.getBean("terminalConfigServiceImpl");
            service.updateTerminalConfigStatus(phone,1);//下发中
            Msg0x8103.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.T8103(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 查询终端参数
     * @param map
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8104( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8104.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.T8104(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 查询指定终端参数
     * @param map
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8106( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8104.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.T8106(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 终端控制
     * @param map
     * @param channel
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8105( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8105.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.T8105(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 位置信息查询
     * @param map
     * @param channel
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8201( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8201.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.T8201(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 临时位置跟踪控制
     * @param map
     * @param channel
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8202( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8202.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.T8202(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 命令上报学时记录
     * @param map
     * @return
     */
    public ResponseModel _0x8205(Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8205.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.t8900_0900_time_up_command(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 设置终端应用参数
     * @param map
     * @return
     */
    public ResponseModel _0x8501(Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            TerminalAppConfigService service = BeanHelper.getBean("terminalAppConfigServiceImpl");
            service.updateTerminalAppConfigStatus(phone,1);//下发中
            Msg0x8501.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.t8900_0900_terminal_set(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 查询计时终端应用参数
     * @param map
     * @return
     */
    public ResponseModel _0x8503(Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8503.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.t8900_0900_terminal_param_search(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 设置禁训状态
     * @param map
     * @param channel
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8502( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            TerminalStatusService service = BeanHelper.getBean("terminalStatusServiceImpl");
            service.updateTerminalStatusStatus(phone,1,null);//下发中
            Msg0x8502.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.t8900_0900_terminal_status(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 立即拍照
     * @param map
     * @param channel
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8301( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8301.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.t8900_0900_photo_command(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 查询照片
     * @param map
     * @param channel
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8302( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8302.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.t8900_0900_photo_search_command(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

    /**
     * 上传指定照片
     * @param map
     * @param channel
     * @return
     */
    @ResponseBody
    public ResponseModel _0x8304( Map<String,Object> map,Channel channel){
        String phone = (String)map.get("phone");
        Channel tcpChannel = TcpClientUtils.getClientChannel(phone);
        if(tcpChannel != null){
            Msg0x8304.setMsg(phone,channel);
            TcpClientUtils.write(tcpChannel,JT808Beans.H2019(JT808Beans.t8900_0900_photo_up_only(map),phone));
            return new ResponseModel("0","正在下发中");

        }else{
            return new ResponseModel("1","终端不在线");
        }
    }

}
