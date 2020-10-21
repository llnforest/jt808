package org.yzh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0001;
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

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @ApiOperation(value = "获得通用应答")
    @GetMapping("res/common")
    public T0001 getCommonRes(){
        T0001 request = new T0001("19156017290",1);
        request.setResultCode(0);
        request.setSerialNo(0);
        request.setReplyId(JT808.终端通用应答);
        T0001 response = messageManager.request(request, T0001.class);

        return response;

    }
}