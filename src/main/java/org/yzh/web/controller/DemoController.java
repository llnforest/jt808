package org.yzh.web.controller;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.codec.MessageEncoderWrapper;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0001;
import org.yzh.web.Application;
import org.yzh.web.component.mybatis.Page;
import org.yzh.web.component.mybatis.PageInfo;
import org.yzh.web.component.mybatis.Pagination;
import org.yzh.web.model.vo.Location;
import org.yzh.web.model.vo.LocationQuery;
import org.yzh.web.service.LocationService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

@Api(description = "测试接口")
@Controller
@RequestMapping
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MessageEncoder messageEncoder;

    private static JTMessageDecoder elucidator;

    private static JTMessageEncoder darkRepulsor;

    @ApiOperation(value = "获得通用应答")
    @GetMapping("demo/test")
    public String test(){
        if(0X8001 == 32769){
            log.info("ok1");
        }
        String hex = "0X8001";
        Integer x = Integer.parseInt(hex.substring(2),16);
        System.out.println(x);
        Integer a = 32769;
        String h = a.toHexString(a);
        System.out.println(h);
        log.info("ok");
        return "111";


    }

//    @ApiOperation(value = "获得通用应答")
//    @GetMapping("res/common")
//    public void getCommonRes(){
//        T0001 request = new T0001("19156017290",1);
//        request.setResultCode(0);
//        request.setSerialNo(0);
//        request.setReplyId(JT808.终端通用应答);
////        T0001 response = messageManager.request(request, T0001.class);
////        T0001 response = messageManager.response(request);
//        ByteBuf byteBuf = messageEncoder.encode(request);
//        log.info("111");
//        log.info(ByteBufUtil.hexDump(byteBuf));
////        log.info(byteBuf);
//    }

    @ApiOperation(value = "获得编码")
    @GetMapping("res/encode")
    public void getEncode(){
        darkRepulsor = new JTMessageEncoder("org.yzh.protocol");
        T0001 request = new T0001("19156017290",1);
        request.setResultCode(0);
        request.setSerialNo(0);
        request.setReplyId(0X8001);
        ByteBuf byteBuf = darkRepulsor.encode(request);
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }

    @ApiOperation(value = "获得解码")
    @GetMapping("res/decode")
    public void getDecode(){
        elucidator = new JTMessageDecoder("org.yzh.protocol");
        String hex = "0001000501915601729000010000800100a1";
        AbstractMessage decode = elucidator.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
        log.info(hex);
        System.out.print(decode);
    }


}