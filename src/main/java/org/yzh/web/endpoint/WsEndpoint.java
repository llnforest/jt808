package org.yzh.web.endpoint;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.WsHandlerUtils;
import org.yzh.framework.session.Session;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.model.ResponseModel;
import org.yzh.web.protocol.JT808Beans;
import org.yzh.web.reply.*;

import java.util.Map;

/**
 * websocket通信
 */
public class WsEndpoint {


}
