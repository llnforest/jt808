package org.yzh.web.controller;

import com.tmri.comm.pps.ProtocolConstants;
import com.tmri.comm.pps.client.IObjectByteArrayConverter;
import com.tmri.comm.pps.client.NetServiceInvoker;
import com.tmri.comm.pps.client.bean.AesResult;
import com.tmri.comm.pps.client.bean.AesToken;
import com.tmri.comm.pps.client.bean.CryptInfo;
import com.tmri.comm.pps.comm.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.web.commons.ObjectJsonSerializer;
import org.yzh.web.commons.config.CommonConfig;
import org.yzh.web.commons.config.TimeConfImpl;
import org.yzh.web.model.bean.NetTrffDrvTrainingrecords;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Controller
@RequestMapping(value = "/time")
@Slf4j
public class TimeController {
    private TimeConfImpl conf = new TimeConfImpl();

    private NetServiceInvoker invoker;//调用服务端的封装类
    private String siteId = "000001";//用户互认接口，本接口该参数无效
    private static AesToken token;//存储AES对称加密秘钥类
    private static String result;

    public void before() {
        log.info("before");
        //第一步：构造NetServiceInvoker，需要两个参数：分别是：BaseHttpCryptData和IObjectByteArrayConverter
        invoker = new NetServiceInvoker(
                //参数一：NetServiceInvoker的加解密封装类，采用模板模式，只需要实现如下接口
                new BaseHttpCryptData() {
                    //(1)互联网服务平台公钥，由互联网服务平台统一分配.
                    @Override
                    public String getPublicKey(CryptInfo cryptInfo) {
                        return conf.getPublicKey();
                    }

                    //(2)第三方接入平台私钥，由互联网服务平台统一分配.
                    @Override
                    public String getPrivateKey() {
                        return conf.getPrivateKey();
                    }

                    //(3)第三方接入平台RSA算法密码，由互联网服务平台统一分配.
                    @Override
                    public String getPassword() {
                        return conf.getPassword();
                    }


                    //(4)返回clientID,由互联网服务平台统一分配.
                    @Override
                    public String getClientId() {
                        return conf.getClientId();
                    }

                    //(5)第三方接入平台获取AesToken算法。请参考实现
                    @Override
                    public AesToken getToken(CryptInfo info) {
                        return getTokenHard(info);
                    }
                },
                //参数二：NetServiceInvoker的java对象序列化封装类。要求将java对象序列化为json后，转化为byte[].其他格式，服务端不识别
                new IObjectByteArrayConverter() {
                    @Override
                    public byte[] convert(Object o) {
                        String serialize = ObjectJsonSerializer.serialize(o);
                        if (StringUtils.isNotBlank(serialize)) {
                            try {
                                return serialize
                                        .getBytes(ProtocolConstants.APP_CHARSET_UTF8);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        return new byte[0];
                    }

                    //可以不实现
                    @Override
                    public Object toBean(byte[] bytes) {
                        return null;
                    }
                });

    }

    @RequestMapping(value = "/sendTime", method = RequestMethod.POST)
    @ResponseBody
    public String sendTime(@RequestParam(value = "sfzhm") String sfzhm, @RequestParam(value = "pxkm") String pxkm, @RequestParam(value = "pxxs") String pxxs, @RequestParam(value = "pxjl") String pxjl, @RequestParam(value = "jly") String jly,@RequestParam(value = "shdw") String shdw,@RequestParam(value = "shr") String shr,@RequestParam(value = "shrq") String shrq,@RequestParam(value = "gxsj") String gxsj,@RequestParam(value = "fzjg") String fzjg,@RequestParam(value = "sign") String sign) throws Exception{
        String verifyString = sfzhm+pxkm+pxxs+gxsj+ CommonConfig.signKey;
        String verifySign = DigestUtils.md5DigestAsHex(verifyString.getBytes());
        if(!verifySign.equalsIgnoreCase(sign)){
            log.error("签名错误：{}",verifySign);
            return "{\"code\":\"X000\",\"data\":null,\"message\":\"签名错误\"}";
        }
        log.info("sendTime");
        before();
        String apiUrl = conf.getServerUrl() + "/m/pub/drv/trainingrecords";//请求服务API地址，固定

        // 构造Request对象
        NetClientRequest clientRequest = buildRequest();
        // InterfaceId 与 FunctionId对应接口编号
        clientRequest.setInterfaceId("X001");
        clientRequest.setFunctionId("X001");
        clientRequest.setRequestUrl(apiUrl);

        // 获取Token令牌用于AES加解密
        AesToken tokenHard = getTokenHard(new CryptInfo(
                clientRequest.getCryptType(), clientRequest.getClientId(),
                clientRequest.getMajor(), clientRequest.getMinor()));
        clientRequest.setClientId(conf.getClientId());// 必须，标明客户端身份。
        clientRequest.setMinor(tokenHard.getVersion());// 必须,token的版本就是我们协议请求的minor（小版本）

        // 构造业务请求参数
        NetTrffDrvTrainingrecords netTrffDrvTrainingrecords = new NetTrffDrvTrainingrecords();
        netTrffDrvTrainingrecords.setSfzmhm(sfzhm); //身份证明号码
        netTrffDrvTrainingrecords.setPxkm(pxkm); //培训科目 3
        netTrffDrvTrainingrecords.setPxxs(pxxs); //培训学时 45
        netTrffDrvTrainingrecords.setPxjl(pxjl); //培训距离 120
        netTrffDrvTrainingrecords.setJly(jly); //教练员 冯波
        netTrffDrvTrainingrecords.setShdwmc(shdw); //审核单位名称 池州市运管处
        netTrffDrvTrainingrecords.setShr(shr);  //审核人 培训科
        netTrffDrvTrainingrecords.setShrq(shrq); //审核日期 2016-12-26
        netTrffDrvTrainingrecords.setGxsj(gxsj); //更新时间 2016-12-26 13:00:00
        netTrffDrvTrainingrecords.setFzjg(fzjg); //发证机关 皖R

        if (netTrffDrvTrainingrecords.getSfzmhm() != null) {
            clientRequest.setData(ObjectJsonSerializer.serialize(netTrffDrvTrainingrecords));
        }

        // 执行请求，获取返回结果
        NetClientResponse result = invoker.doPost(clientRequest);

        this.result = result.getData().toString();
        log.info(String.format("学员身份证：%s  响应结果：%s",sfzhm,this.result));
        return this.result;
    }

    protected NetClientRequest buildRequest() {
        NetClientRequest clientRequest = new NetClientRequest();
        clientRequest.setSiteId(siteId);
        clientRequest.setServerId(conf.getServerId());// 互联网省份代码
        clientRequest.setClientId(conf.getClientId());//分配给第三方系统的代码
        clientRequest.setMajor(String.valueOf(ProtocolConstants.HEADER_MARJOR));
        clientRequest.setCompressType(HttpCompressData.CompressType.GZIP);
        clientRequest.setCryptType(IHttpCryptData.CryptType.AES_CRYPT);//普通请求采用aes算法，aes算法中的token，使用rsa算法加密请求。
        return clientRequest;
    }

    public AesToken getTokenHard(CryptInfo info) {
        if (this.token == null || isExpired(this.token)) {
            NetClientRequest clientRequest = buildRequest();
            //线上环境 请切换此url为 http://gab.122.gov.cn/ws/m/token/generate
//            clientRequest.setRequestUrl("http://58.214.240.91:9002/ws/m/token/generate");//生成token服务类。
//            clientRequest.setRequestUrl(conf.getServerUrl() + "/m/token/generate");//生成token服务类。
            clientRequest.setRequestUrl("http://gab.122.gov.cn/ws/m/token/generate");//生成token服务类。

            clientRequest.setCryptType(IHttpCryptData.CryptType.RSA_CRYPT);//token生成过程采用rsa算法。
            clientRequest.setCompressType(IHttpCompressData.CompressType.GZIP);
            clientRequest.setClientId(info.getClientId());
            try {
                NetClientResponse response = invoker.doPost(clientRequest);
                System.out.println("code:{},mes:{},data:{}"
                        + response.getData().toString());
                AesResult token = ObjectJsonSerializer.deSerialize(
                        AesResult.class, response.getData().toString());
                this.token = token.getData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.token;
    }

    protected boolean isExpired(AesToken token) {
        if (StringUtils.isBlank(token.getExpiry()))
            return true;
        DateTime time = new DateTime(new Date(Long.valueOf(token.getExpiry())));
        System.out.println(time.toDate());
        return DateTime.now().isAfter(time);
    }
}
