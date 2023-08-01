//package org.yzh.web.controller;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufUtil;
//import io.netty.buffer.Unpooled;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.yzh.framework.codec.MessageEncoder;
//import org.yzh.framework.commons.transform.Bcd;
//import org.yzh.framework.orm.model.AbstractMessage;
//import org.yzh.framework.session.MessageManager;
//import org.yzh.framework.session.SessionManager;
//import org.yzh.protocol.codec.JTMessageDecoder;
//import org.yzh.protocol.codec.JTMessageEncoder;
//import org.yzh.protocol.t808.T0001;
//import org.yzh.web.Application;
//
//import java.io.*;
//
//@Api(description = "测试接口")
//@Controller
//@RequestMapping
//public class DemoController {
//    private static final Logger log = LoggerFactory.getLogger(Application.class);
//    @Autowired
//    private MessageManager messageManager;
//
//    @Autowired
//    private SessionManager sessionManager;
//
//    @Autowired
//    private MessageEncoder messageEncoder;
//
//    private static JTMessageDecoder elucidator;
//
//    private static JTMessageEncoder darkRepulsor;
//
//    @ApiOperation(value = "获得通用应答")
//    @GetMapping("demo/test")
//    public void test() {
//        int a = -2089706292;
//        String b = Integer.toBinaryString(a);
//        long c = Long.parseLong(b, 2);
//        log.info("结果：{}", c);
//        log.info("结果：{}", String.valueOf(c));
//
////                String url = "http://192.168.100.53:8080/xh/trainTimeImgUpload";
////        String param = "photoNum=照片编号&devNum=abcdefg&photoPath=/public/in/upload";
////        String result = HttpRequestUtil.sendPost(url, param, false);
////        log.info("结果：{}", result);
////        Map<String,String> map = (Map<String,String>)JSON.parse(result);
////        log.info(map.get("msg"));
////        log.info(map.get("data"));
//        //    JSONObject maps =  JSONObject.parseObject(result);
//
//        //  maps.getString("")
////        JSONObject jsonObject= JSONObject.fromObject(result);
////        ResponseData maps=(ResponseData)JSONObject.toBean(jsonObject, ResponseData.class);
////        System.out.println("这个是用JSON类来解析JSON字符串!!!");
////        log.info("结果：{}",maps.getCode());
////        for(int i = 0;i < list.size();i++){
////            Map<String,String> map1 = (Map<String, String>) list.get(i);
////            log.info("value:{}",map1.get("ll"));
////        }
//
////        for (Object map : maps.entrySet()){
////            log.info("key:{},value:{}",((Map.Entry)map).getKey(),((Map.Entry)map).getValue());
////            if(((Map.Entry)map).getKey().equals("data")){
////                List<Map<String,String>> list = (List<Map<String,String>>)((Map.Entry)map).getValue();
////                for(int i = 0;i < list.size();i++){
////                    Map<String,String> map1 = list.get(i);
////                    log.info("value:{}",map1.get("ll"));
////                }
////            }
////        }
//
//    }
//
//    @ApiOperation(value = "获得通用应答")
//    @GetMapping("demo/test1")
//    public void test1() {
//        String file = "F://jt808_img/fd6lM3nhl5SElvmV_0624163944.jpeg";
//        try {
//            FileInputStream is = new FileInputStream(file);
//            // 设置数据缓冲
//            byte[] bs = new byte[1024 * 2];
//            // 读取到的数据长度
//            int len;
//            // 输出的文件流保存图片至本地
//            OutputStream os = new FileOutputStream("http://192.168.100.53:8080/uploads/traintimeimg/a.jpg");
//            while ((len = is.read(bs)) != -1) {
//                os.write(bs, 0, len);
//            }
//            os.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        log.info("ok");
////        Map<String,String> map = (Map<String,String>)JSON.parse(result);
//
//    }
//
//
//    public void writeValue(ByteBuf buf, String value, int strLen) {
//        char[] chars = new char[strLen];
//        int i = strLen - value.length();
//        if (i >= 0) {
//            value.getChars(0, strLen - i, chars, i);
//            while (i > 0)
//                chars[--i] = '0';
//        } else {
//            value.getChars(-i, strLen - i, chars, 0);
//        }
//        byte[] src = Bcd.from(chars);
//        buf.writeBytes(src);
//    }
//
//    /**
//     * @功能: BCD码转为10进制串(阿拉伯数据)
//     * @参数: BCD码
//     * @结果: 10进制串
//     */
//    public static String bcd2Str(byte[] bytes) {
//        StringBuffer temp = new StringBuffer(bytes.length * 2);
//        for (int i = 0; i < 2; i++) {
//            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
//            temp.append((byte) (bytes[i] & 0x0f));
//        }
//        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
//                .toString().substring(1) : temp.toString();
//    }
//
//    /**
//     * @功能: 10进制串转为BCD码
//     * @参数: 10进制串
//     * @结果: BCD码
//     */
//    public static byte[] str2Bcd(String asc) {
//        int len = asc.length();
//        int mod = len % 2;
//        if (mod != 0) {
//            asc = "0" + asc;
//            len = asc.length();
//        }
//        byte abt[] = new byte[len];
//        if (len >= 2) {
//            len = len / 2;
//        }
//        byte bbt[] = new byte[len];
//        abt = asc.getBytes();
//        int j, k;
//        for (int p = 0; p < asc.length() / 2; p++) {
//            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
//                j = abt[2 * p] - '0';
//            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
//                j = abt[2 * p] - 'a' + 0x0a;
//            } else {
//                j = abt[2 * p] - 'A' + 0x0a;
//            }
//            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
//                k = abt[2 * p + 1] - '0';
//            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
//                k = abt[2 * p + 1] - 'a' + 0x0a;
//            } else {
//                k = abt[2 * p + 1] - 'A' + 0x0a;
//            }
//            int a = (j << 4) + k;
//            byte b = (byte) a;
//            bbt[p] = b;
//        }
//        return bbt;
//    }
//
////    @ApiOperation(value = "获得通用应答")
////    @GetMapping("res/common")
////    public void getCommonRes(){
////        T0001 request = new T0001("19156017290",1);
////        request.setResultCode(0);
////        request.setSerialNo(0);
////        request.setReplyId(JT808.终端通用应答);
//////        T0001 response = messageManager.request(request, T0001.class);
//////        T0001 response = messageManager.response(request);
////        ByteBuf byteBuf = messageEncoder.encode(request);
////        log.info("111");
////        log.info(ByteBufUtil.hexDump(byteBuf));
//////        log.info(byteBuf);
////    }
//
//    @ApiOperation(value = "获得编码")
//    @GetMapping("res/encode")
//    public void getEncode() {
//        darkRepulsor = new JTMessageEncoder("org.yzh.protocol");
//        T0001 request = new T0001("19156017290", 1);
//        request.setResultCode(0);
//        request.setSerialNo(0);
//        request.setReplyId(0X8001);
//        ByteBuf byteBuf = darkRepulsor.encode(request);
//        System.out.println();
//        System.out.println(ByteBufUtil.hexDump(byteBuf));
//    }
//
//    @ApiOperation(value = "获得解码")
//    @GetMapping("res/decode")
//    public void getDecode() {
//        elucidator = new JTMessageDecoder("org.yzh.protocol");
//        String hex = "0001000501915601729000010000800100a1";
//        AbstractMessage decode = elucidator.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
//        log.info(hex);
//        System.out.print(decode);
//    }
//
//
//}
