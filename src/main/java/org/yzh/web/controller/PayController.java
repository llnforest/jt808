package org.yzh.web.controller;

import com.abc.pay.client.Constants;
import com.abc.pay.client.JSON;
import com.abc.pay.client.ebus.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.web.commons.AES256Util;
import org.yzh.web.commons.Base64Util;
import org.yzh.web.commons.BeanHelper;
import org.yzh.web.commons.config.PayConfig;
import org.yzh.web.model.entity.CzFinanceDrawSchools;
import org.yzh.web.model.entity.CzJsSchoolAccounts;
import org.yzh.web.model.entity.CzJsStudentReserves;
import org.yzh.web.model.entity.CzOrderRefunds;
import org.yzh.web.service.CzFinanceDrawSchoolsService;
import org.yzh.web.service.CzJsSchoolAccountsService;
import org.yzh.web.service.CzJsStudentReservesService;
import org.yzh.web.service.CzOrderRefundsService;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@Controller
@RequestMapping(value = "/pay")
@Slf4j
public class PayController extends BaseController {

    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    @Autowired
    private PayConfig payConfig;

    /**
     * @return java.lang.String
     * @Description 下单
     * @Param [orderId, ip]
     * @Author lynn 9:26 2022/2/14
     **/
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() throws InterruptedException {
        logger.info("come in");
        Thread.currentThread().sleep(5000);
        logger.info("come out");
        String token = "16644340953664016886";
        String password = AES256Util.encode("method=invokePayFromBrowser&tokenID="+token);
        String param = Base64Util.base64Encode(password);
        log.info("password:"+password);

        String decodeParam = Base64Util.base64Decode(param);
        log.info("decodeParam:"+decodeParam);
        decodeParam = "AGWUe1qUG4ZuH/DC3u/e3G4XQyDsCqnifmakhmVxKRzxAjOmduktcA6ky8OrEK625lWf+OEM8CVl1ooWUYg20g==";
        String yuanwen = AES256Util.decode(decodeParam);
        log.info("yuanwen"+yuanwen);
        String yuanwen1 = AES256Util.decode(password);
        log.info("yuanwen1"+yuanwen1);
        try {
            logger.info(Base64Utils.encodeToString(password.getBytes("GBK")));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
        }
        //下单成功
        return "{\"param\":\"" + param + "\"}";
    }

    /**
     * @return java.lang.String
     * @Description 下单
     * @Param [orderId, ip]
     * @Author lynn 9:26 2022/2/14
     **/
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public String createOrder(@RequestParam(value = "orderId") String orderId, @RequestParam(value = "ip") String ip) {
        log.info(orderId);
        //1、生成订单对象
        PaymentRequest PaymentRequest = new PaymentRequest();
        CzJsStudentReserves model = new CzJsStudentReserves();
        model.setOrderId(Long.valueOf(orderId));
        CzJsStudentReservesService service = BeanHelper.getBean(CzJsStudentReservesService.class);
        CzJsStudentReserves czJsStudentReserves = service.find(model);
        logger.info(czJsStudentReserves);
        if (czJsStudentReserves == null || czJsStudentReserves.getDeletedAt() != null) {
            return "{\"status\":1,\"data\":\"\",\"msg\":\"订单不存在\"}";
        }

        PaymentRequest.dicOrder.put("PayTypeID", Constants.PAY_TYPE_DIRECTPAY);                   //设定交易类型
        Date createdAt = czJsStudentReserves.getCreatedAt();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        PaymentRequest.dicOrder.put("OrderDate", sdf1.format(createdAt));                  //设定订单日期 （必要信息 - YYYY/MM/DD）
        PaymentRequest.dicOrder.put("OrderTime", sdf2.format(createdAt));                   //设定订单时间 （必要信息 - HH:MM:SS）

        PaymentRequest.dicOrder.put("OrderNo", String.valueOf(czJsStudentReserves.getOrderId()));                       //设定订单编号 （必要信息）
        PaymentRequest.dicOrder.put("CurrencyCode", "156");             //设定交易币种
        PaymentRequest.dicOrder.put("OrderAmount", String.valueOf(czJsStudentReserves.getApplyFee()));
        PaymentRequest.dicOrder.put("OrderDesc", "学员报名缴费");                   //设定订单说明

        PaymentRequest.dicOrder.put("CommodityType", "0101");           //设置商品种类
        PaymentRequest.dicOrder.put("BuyIP", ip);                           //IP

        //2、订单明细
        LinkedHashMap orderitem = new LinkedHashMap();
        orderitem.put("ProductName", "学员报名收费");//商品名称

        PaymentRequest.orderitems.put(1, orderitem);

        //3、生成支付请求对象
        String paymentType = Constants.PAY_TYPE_ABC;
        PaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
        String paymentLinkType = Constants.PAY_LINK_TYPE_NET;
        PaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
        if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE)) {
            PaymentRequest.dicRequest.put("UnionPayLinkType", Constants.UPMPLINK_TYPE_WAP);  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
        }
        PaymentRequest.dicRequest.put("ReceiveAccount", payConfig.abcAccount);      //设定收款方账号
        PaymentRequest.dicRequest.put("ReceiveAccName", payConfig.abcName);      //设定收款方户名
        PaymentRequest.dicRequest.put("NotifyType", Constants.NOTIFY_TYPE_SERVER);              //设定通知方式
        PaymentRequest.dicRequest.put("ResultNotifyURL", payConfig.abcNotifyUrl);    //设定通知URL地址
        PaymentRequest.dicRequest.put("MerchantRemarks", "报名缴费");    //设定附言

        PaymentRequest.dicRequest.put("IsBreakAccount", "1");      //设定交易是否分账、交易是否支持向二级商户入账
        PaymentRequest.dicRequest.put("SplitAccTemplate", "");  //分账模版编号

        //4、添加分账信息
        LinkedHashMap map = new LinkedHashMap();
        map.put("SplitMerchantID", czJsStudentReserves.getBankAccount());
        map.put("SplitAmount", String.valueOf(czJsStudentReserves.getApplyFee()));
        PaymentRequest.dicSplitAccInfo.put(1, map);

        //JSON json = PaymentRequest.postRequest();
        JSON json = PaymentRequest.extendPostRequest(1);
        String jsonString = json.getIJsonString();
        String returnCode = json.GetKeyValue("ReturnCode");
        String errorMessage = json.GetKeyValue("ErrorMessage");
        if (returnCode.equals("0000")) {
            String oneQRForAll = json.GetKeyValue("OneQRForAll");
            String token = oneQRForAll.split("token=")[1];
            String param = AES256Util.encode("method=invokePayFromBrowser&tokenID="+token);
            service.updateToken(czJsStudentReserves.getId(), token);
//            String param = Base64Util.base64Encode(passString);
            //下单成功
            return "{\"status\":0,\"data\":" + jsonString + ",\"msg\":\"" + errorMessage + "\",\"param\":\"" + param + "\"}";
        } else {
            //下单失败
            return "{\"status\":1,\"data\":"+jsonString+",\"msg\":\"" + errorMessage + "\"}";
        }
    }


    /**
     * @return java.lang.String
     * @Description 微信下单
     * @Param [orderId, ip ,openid]
     * @Author lynn 9:26 2022/2/14
     **/
    @RequestMapping(value = "/createOrderByWx", method = RequestMethod.POST)
    @ResponseBody
    public String createOrderByWx(@RequestParam(value = "orderId") String orderId, @RequestParam(value = "ip") String ip,@RequestParam(value = "openid") String openid) {
        log.info(orderId);
        //1、生成订单对象
        UnifiedPaymentRequest PaymentRequest = new UnifiedPaymentRequest();
        CzJsStudentReserves model = new CzJsStudentReserves();
        model.setOrderId(Long.valueOf(orderId));
        CzJsStudentReservesService service = BeanHelper.getBean(CzJsStudentReservesService.class);
        CzJsStudentReserves czJsStudentReserves = service.find(model);
        logger.info(czJsStudentReserves);
        if (czJsStudentReserves == null || czJsStudentReserves.getDeletedAt() != null) {
            return "{\"status\":1,\"data\":\"\",\"msg\":\"订单不存在\"}";
        }

        PaymentRequest.dicOrder.put("PayTypeID", Constants.PAY_TYPE_JSAPI);                   //设定交易类型
        Date createdAt = czJsStudentReserves.getCreatedAt();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        PaymentRequest.dicOrder.put("OrderDate", sdf1.format(createdAt));                  //设定订单日期 （必要信息 - YYYY/MM/DD）
        PaymentRequest.dicOrder.put("OrderTime", sdf2.format(createdAt));                   //设定订单时间 （必要信息 - HH:MM:SS）
//        PaymentRequest.dicOrder.put("orderTimeoutDate", request.getParameter("orderTimeoutDate"));     //设定订单有效期
        PaymentRequest.dicOrder.put("OrderNo", String.valueOf(czJsStudentReserves.getOrderId()));  //设定订单编号 （必要信息）
        PaymentRequest.dicOrder.put("CurrencyCode", "156");             //设定交易币种
        PaymentRequest.dicOrder.put("OrderAmount", String.valueOf(czJsStudentReserves.getApplyFee()));      //设定交易金额
        PaymentRequest.dicOrder.put("OrderDesc", "学员报名缴费");                   //设定订单说明

        PaymentRequest.dicOrder.put("AccountNo",payConfig.wxAppId);                    //appid
        PaymentRequest.dicOrder.put("OpenID",openid);                       //openid
        PaymentRequest.dicOrder.put("BuyIP", ip);                           //IP

        //2、订单明细
        LinkedHashMap orderitem = new LinkedHashMap();
        orderitem.put("ProductName", "学员报名收费");//商品名称

        PaymentRequest.orderitems.put(1, orderitem);


        //3、生成支付请求对象
        String paymentType = Constants.PAY_TYPE_WX;
        PaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
        PaymentRequest.dicRequest.put("CommodityType", "0101");           //设置商品种类

        String paymentLinkType = Constants.PAY_LINK_TYPE_NET;
        PaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
        if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE)) {
            PaymentRequest.dicRequest.put("UnionPayLinkType", Constants.UPMPLINK_TYPE_WAP);  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
        }
//        PaymentRequest.dicRequest.put("ReceiveAccount", payConfig.abcAccount);      //设定收款方账号
//        PaymentRequest.dicRequest.put("ReceiveAccName", payConfig.abcName);      //设定收款方户名
        PaymentRequest.dicRequest.put("NotifyType", Constants.NOTIFY_TYPE_SERVER);              //设定通知方式
        PaymentRequest.dicRequest.put("ResultNotifyURL", payConfig.wxNotifyUrl);    //设定通知URL地址
        PaymentRequest.dicRequest.put("MerchantRemarks", "报名缴费");    //设定附言

//        PaymentRequest.dicRequest.put("ReceiveMark",request.getParameter("ReceiveMark"));             //交易是否直接入二级商户账户
//        PaymentRequest.dicRequest.put("ReceiveMerchantType",request.getParameter("ReceiveMerchantType")); //设定收款方账户类型

        PaymentRequest.dicRequest.put("IsBreakAccount", "1");      //设定交易是否分账、交易是否支持向二级商户入账
        PaymentRequest.dicRequest.put("SplitAccTemplate", "");  //分账模版编号

        //4、添加分账信息
        LinkedHashMap map = new LinkedHashMap();
        map.put("SplitMerchantID", czJsStudentReserves.getBankAccount());
        map.put("SplitAmount", String.valueOf(czJsStudentReserves.getApplyFee()));
        PaymentRequest.dicSplitAccInfo.put(1, map);

        //JSON json = PaymentRequest.postRequest();
        JSON json = PaymentRequest.extendPostRequest(1);
        String jsonString = json.getIJsonString();

        String returnCode = json.GetKeyValue("ReturnCode");
        String errorMessage = json.GetKeyValue("ErrorMessage");
        if (returnCode.equals("0000")) {
            String oneQRForAll = json.GetKeyValue("OneQRForAll");
            String token = oneQRForAll.split("token=")[1];
            service.updateToken(czJsStudentReserves.getId(), token);
            //下单成功
            return "{\"status\":0,\"data\":" + jsonString + ",\"msg\":\"" + errorMessage + "\"}";
        } else {
            //下单失败
            return "{\"status\":1,\"data\":"+jsonString+",\"msg\":\"" + errorMessage + "\"}";
        }
    }

    /**
     * @return java.lang.String
     * @Description 支付宝下单
     * @Param [orderId, ip ,openid]
     * @Author lynn 9:26 2022/2/14
     **/
    @RequestMapping(value = "/createOrderByAliPay", method = RequestMethod.POST)
    @ResponseBody
    public String createOrderByAliPay(@RequestParam(value = "orderId") String orderId, @RequestParam(value = "ip") String ip) {
        log.info(orderId);
        //1、生成订单对象
        AlipayRequest PaymentRequest = new AlipayRequest();
        CzJsStudentReserves model = new CzJsStudentReserves();
        model.setOrderId(Long.valueOf(orderId));
        CzJsStudentReservesService service = BeanHelper.getBean(CzJsStudentReservesService.class);
        CzJsStudentReserves czJsStudentReserves = service.find(model);
        logger.info(czJsStudentReserves);
        if (czJsStudentReserves == null || czJsStudentReserves.getDeletedAt() != null) {
            return "{\"status\":1,\"data\":\"\",\"msg\":\"订单不存在\"}";
        }

        PaymentRequest.dicOrder.put("PayTypeID", Constants.PAY_TYPE_ALI_APP);                   //设定交易类型
        Date createdAt = czJsStudentReserves.getCreatedAt();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        PaymentRequest.dicOrder.put("OrderDate", sdf1.format(createdAt));                  //设定订单日期 （必要信息 - YYYY/MM/DD）
        PaymentRequest.dicOrder.put("OrderTime", sdf2.format(createdAt));                   //设定订单时间 （必要信息 - HH:MM:SS）
//        PaymentRequest.dicOrder.put("orderTimeoutDate", request.getParameter("orderTimeoutDate"));     //设定订单有效期
        PaymentRequest.dicOrder.put("OrderNo", String.valueOf(czJsStudentReserves.getOrderId()));  //设定订单编号 （必要信息）
        PaymentRequest.dicOrder.put("CurrencyCode", "156");             //设定交易币种
        PaymentRequest.dicOrder.put("OrderAmount", String.valueOf(czJsStudentReserves.getApplyFee()));      //设定交易金额
        PaymentRequest.dicOrder.put("OrderDesc", "学员报名缴费");                   //设定订单说明

        PaymentRequest.dicOrder.put("AccountNo",payConfig.wxAppId);                    //appid
//        PaymentRequest.dicOrder.put("OpenID",openid);                       //openid
        PaymentRequest.dicOrder.put("BuyIP", ip);                           //IP

        //2、订单明细
        LinkedHashMap orderitem = new LinkedHashMap();
        orderitem.put("ProductName", "学员报名收费");//商品名称

        PaymentRequest.orderitems.put(1, orderitem);


        //3、生成支付请求对象
        String paymentType = Constants.PAY_TYPE_ALIPAY;
        PaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
        PaymentRequest.dicRequest.put("CommodityType", "0101");           //设置商品种类

        String paymentLinkType = Constants.PAY_LINK_TYPE_NET;
        PaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
        if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE)) {
            PaymentRequest.dicRequest.put("UnionPayLinkType", Constants.UPMPLINK_TYPE_WAP);  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
        }
//        PaymentRequest.dicRequest.put("ReceiveAccount", payConfig.abcAccount);      //设定收款方账号
//        PaymentRequest.dicRequest.put("ReceiveAccName", payConfig.abcName);      //设定收款方户名
        PaymentRequest.dicRequest.put("NotifyType", Constants.NOTIFY_TYPE_SERVER);              //设定通知方式
        PaymentRequest.dicRequest.put("ResultNotifyURL", payConfig.alipayNotifyUrl);    //设定通知URL地址
        PaymentRequest.dicRequest.put("MerchantRemarks", "报名缴费");    //设定附言

//        PaymentRequest.dicRequest.put("ReceiveMark",request.getParameter("ReceiveMark"));             //交易是否直接入二级商户账户
//        PaymentRequest.dicRequest.put("ReceiveMerchantType",request.getParameter("ReceiveMerchantType")); //设定收款方账户类型

        PaymentRequest.dicRequest.put("IsBreakAccount", "1");      //设定交易是否分账、交易是否支持向二级商户入账
        PaymentRequest.dicRequest.put("SplitAccTemplate", "");  //分账模版编号

        //4、添加分账信息
        LinkedHashMap map = new LinkedHashMap();
        map.put("SplitMerchantID", czJsStudentReserves.getBankAccount());
        map.put("SplitAmount", String.valueOf(czJsStudentReserves.getApplyFee()));
        PaymentRequest.dicSplitAccInfo.put(1, map);

        //JSON json = PaymentRequest.postRequest();
        JSON json = PaymentRequest.extendPostRequest(1);
        String jsonString = json.getIJsonString();

        String returnCode = json.GetKeyValue("ReturnCode");
        String errorMessage = json.GetKeyValue("ErrorMessage");
        if (returnCode.equals("0000")) {
            String oneQRForAll = json.GetKeyValue("OneQRForAll");
            String token = oneQRForAll.split("token=")[1];
            service.updateToken(czJsStudentReserves.getId(), token);
            //下单成功
            return "{\"status\":0,\"data\":" + jsonString + ",\"msg\":\"" + errorMessage + "\"}";
        } else {
            //下单失败
            return "{\"status\":1,\"data\":"+jsonString+",\"msg\":\"" + errorMessage + "\"}";
        }
    }

    /**
     * @return java.lang.String
     * @Description 退款 //TODO
     * @Param [refundId]
     * @Author lynn 9:27 2022/2/14
     **/
    @RequestMapping(value = "/refundMoney", method = RequestMethod.POST)
    @ResponseBody
    public String refundMoney(@RequestParam(value = "refundId") String refundId) {

        CzOrderRefunds model = new CzOrderRefunds();
        model.setOrderId(Long.valueOf(refundId));
        CzOrderRefundsService service = BeanHelper.getBean(CzOrderRefundsService.class);
        CzOrderRefunds czOrderRefunds = service.find(model);
        if (czOrderRefunds == null || czOrderRefunds.getDeletedAt() != null) {
            return "{\"status\":1,\"data\":\"\",\"msg\":\"退款订单不存在\"}";
        }

        //1、生成订单对象
        RefundRequest refundRequest = new RefundRequest();

        Date createdAt = czOrderRefunds.getCreatedAt();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        refundRequest.dicRequest.put("OrderDate", sdf1.format(createdAt));  //订单日期（必要信息）
        refundRequest.dicRequest.put("OrderTime", sdf2.format(createdAt)); //订单时间（必要信息）
        //refundRequest.dicRequest.put("MerRefundAccountNo", request.getParameter("txtMerRefundAccountNo")); //商户退款账号
        //refundRequest.dicRequest.put("MerRefundAccountName", request.getParameter("txtMerRefundAccountName")); //商户退款名
        refundRequest.dicRequest.put("OrderNo", String.valueOf(czOrderRefunds.getRefundOrderId())); //原交易编号（必要信息）
        refundRequest.dicRequest.put("NewOrderNo", String.valueOf(czOrderRefunds.getOrderId())); //交易编号（必要信息）
        refundRequest.dicRequest.put("CurrencyCode", "156"); //交易币种（必要信息）
        refundRequest.dicRequest.put("TrxAmount", String.valueOf(czOrderRefunds.getRefundFee())); //退货金额 （必要信息）
        refundRequest.dicRequest.put("RefundType", "0"); //退货类型 （非必要信息）
        refundRequest.dicRequest.put("MerchantRemarks", czOrderRefunds.getRefundRemark());  //附言
        refundRequest.dicRequest.put("MerRefundAccountFlag", "");  //退款账簿上送标识 1：担保账户 2：商户二级账簿 3：退款账簿
        //如果需要专线地址，调用此方法：
        //tRequest.setConnectionFlag(true);

        LinkedHashMap map = new LinkedHashMap();
        map.put("SplitMerchantID", czOrderRefunds.getMerchantId());
        map.put("SplitAmount", String.valueOf(czOrderRefunds.getRefundFee()));
        refundRequest.dicSplitAccInfo.put(1, map);

//        String[] templalteSplitAcc_arr = new String[]{};
//        String[] templalteSplitAmount_arr = new String[]{};
//        templalteSplitAcc_arr = request.getParameterValues("TemplalteSplitAcc");
//        templalteSplitAmount_arr = request.getParameterValues("TemplalteSplitAmount");
//
//        if(templalteSplitAcc_arr != null) {
//            for (int i = 0; i < templalteSplitAcc_arr.length; i++)
//            {
//                LinkedHashMap templateMap = new LinkedHashMap();
//                templateMap.put("SplitAcc",templalteSplitAcc_arr[i]);
//                templateMap.put("SplitAmount",templalteSplitAmount_arr[i]);
//                tRequest.dicTemplalteSplitAccInfo.put(i+1, templateMap);
//            }
//        }

        //3、传送退款请求并取得退货结果
        JSON json = refundRequest.postRequest();

        //4、判断退款结果状态，进行后续操作
//        StringBuilder strMessage = new StringBuilder("");
        String msg = json.GetKeyValue("MSG");
        String returnCode = json.GetKeyValue("ReturnCode");
        String errorMessage = json.GetKeyValue("ErrorMessage");
        if (returnCode.equals("0000")) {
            //5、退款成功/退款受理成功
            return "{\"status\":0,\"data\":" + msg + ",\"msg\":\"" + errorMessage + "\"}";
        } else {
            //6、退款失败
            return "{\"status\":1,\"data\":\"\",\"msg\":\"" + errorMessage + "\"}";
        }
    }

    /**
     * @return java.lang.String
     * @Description 提现 //TODO
     * @Param [orderId, ip]
     * @Author lynn 9:27 2022/2/14
     **/
    @RequestMapping(value = "/cashMoney", method = RequestMethod.POST)
    @ResponseBody
    public String cashMoney(@RequestParam(value = "inscode") String inscode, @RequestParam(value = "money") String money) {
        // 1、获取驾校账户信息
        CzJsSchoolAccounts model = new CzJsSchoolAccounts();
        model.setInscode(inscode);
        CzJsSchoolAccountsService czJsSchoolAccountsService = BeanHelper.getBean(CzJsSchoolAccountsService.class);
        CzJsSchoolAccounts czJsSchoolAccounts = czJsSchoolAccountsService.find(model);
        if (czJsSchoolAccounts == null || czJsSchoolAccounts.getDeletedAt() != null) {
            return "{\"status\":1,\"data\":\"\",\"msg\":\"账户不存在\"}";
        }

        // 2、确认提现金额
        CzFinanceDrawSchoolsService czFinanceDrawSchoolsService = BeanHelper.getBean(CzFinanceDrawSchoolsService.class);
        CzFinanceDrawSchools czFinanceDrawSchools = new CzFinanceDrawSchools();
        czFinanceDrawSchools.setInscode(inscode);
        //保证线程安全
        synchronized (this){
            CzFinanceDrawSchools drawSchools = czFinanceDrawSchoolsService.find(czFinanceDrawSchools);
            if (drawSchools == null || drawSchools.getDeletedAt() != null) {
                return "{\"status\":1,\"data\":\"\",\"msg\":\"驾校不存在\"}";
            }
            BigDecimal applyFee = new BigDecimal(money).setScale(2);
            if (drawSchools.getApplyFee().compareTo(applyFee) == -1) {
                return "{\"status\":1,\"data\":\"\",\"msg\":\"驾校可提现金额不足\"}";
            }
            money = applyFee.toString();

            //1、生成订单对象
            OutPaymentRequest outPaymentRequest = new OutPaymentRequest();
            outPaymentRequest.dicRequest.put("SubMerId", czJsSchoolAccounts.getMerchantId());        //二级商户编号
            outPaymentRequest.dicRequest.put("Account", czJsSchoolAccounts.getBankAccount());          //收款账号
            outPaymentRequest.dicRequest.put("AccountName", czJsSchoolAccounts.getBankUser());  //收款账名
            outPaymentRequest.dicRequest.put("TrxAmount", money);      //出金金额
            outPaymentRequest.dicRequest.put("DrawingFlag", "0");          //是否关联支付订单编号
            outPaymentRequest.dicRequest.put("OrderNo", "");          //交易编号
            outPaymentRequest.dicRequest.put("Remark", "");          //备注
            outPaymentRequest.dicRequest.put("RecBankNo", "");          //他行行号

            JSON json = outPaymentRequest.postRequest();
    //        JSON json = tPaymentRequest.extendPostRequest(1);
            String msg = json.GetKeyValue("MSG");
            String returnCode = json.GetKeyValue("ReturnCode");
            String errorMessage = json.GetKeyValue("ErrorMessage");
            if (returnCode.equals("0000")) {
                //5、提现成功
                czFinanceDrawSchoolsService.cashResult(1, drawSchools, applyFee, czJsSchoolAccounts.getBankAccount(), "提现成功");
                return "{\"status\":0,\"data\":" + msg + ",\"msg\":\"" + errorMessage + "\"}";
            } else {
                //6、提现失败
                czFinanceDrawSchoolsService.cashResult(0, drawSchools, applyFee, czJsSchoolAccounts.getBankAccount(), errorMessage);
                return "{\"status\":1,\"data\":\"\",\"msg\":\"" + errorMessage + "\"}";
            }
        }
//        if (ReturnCode.equals("0000")) {
//
//        } else {
//
//        }
//        return "{\"status\":0,\"data\":\"\",\"msg\":\"提现成功\"}";
    }

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello() {
        return "helloWorld";
    }

    @RequestMapping(value = "/hi")
    public String hi() {
        return "test/hi";
    }


}
