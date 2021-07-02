package org.yzh.protocol.commons;

/**
 * 中华人民共和国交通运输行业标准
 * 道路运输车辆卫星定位系统终端通信协议
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface JT808 {

    String 终端通用应答 = "0x0001";
    String 未鉴权通用应答 = "0x0000";
    String 终端心跳 = "0x0002";
    String 终端注销 = "0x0003";
    String 查询服务器时间 = "0x0004";//2019 new
    String 终端补传分包请求 = "0x0005";//2019 new
    String 终端注册 = "0x0100";
    String 终端鉴权 = "0x0102";//2019 modify
    String 查询终端参数应答 = "0x0104";
    String 查询终端属性应答 = "0x0107";
    String 终端升级结果通知 = "0x0108";
    String 位置信息汇报 = "0x0200";
    String 位置信息查询应答 = "0x0201";
    String 事件报告 = "0x0301";//2019 del
    String 提问应答 = "0x0302";//2019 del
    String 信息点播_取消 = "0x0303";//2019 del
    String 车辆控制应答 = "0x0500";
    String 查询区域或线路数据应答 = "0x0608";//2019 new
    String 行驶记录数据上传 = "0x0700";
    String 电子运单上报 = "0x0701";
    String 驾驶员身份信息采集上报 = "0x0702";//2019 modify
    String 定位数据批量上传 = "0x0704";
    String CAN总线数据上传 = "0x0705";
    String 多媒体事件信息上传 = "0x0800";
    String 多媒体数据上传 = "0x0801";
    String 存储多媒体数据检索应答 = "0x0802";
    String 摄像头立即拍摄命令应答 = "0x0805";
    String 数据上行透传 = "0x0900";
    String 数据下行透传 = "0x8900";

    //透传机制
    String 上报教练员登录 = "0x0900_0x0101";
    String 教练员登录应答 = "0x8900_0x8101";
    String 上报教练员登出 = "0x0900_0x0102";
    String 教练员登出应答 = "0x8900_0x8102";
    String 上报学员登录 = "0x0900_0x0201";
    String 学员登录应答 = "0x8900_0x8201";
    String 上报学员登出 = "0x0900_0x0202";
    String 学员登出应答 = "0x8900_0x8202";
    String 上报学时记录 = "0x0900_0x0203";
    String 命令上报学时记录 = "0x8900_0x8205";
    String 命令上报学时记录应答 = "0x0900_0x0205";
    String 立即拍照 = "0x8900_0x8301";
    String 立即拍照应答 = "0x0900_0x0301";
    String 查询照片 = "0x8900_0x8302";
    String 查询照片应答 = "0x0900_0x0302";
    String 上报照片查询结果 = "0x0900_0x0303";
    String 上报照片查询结果应答 = "0x8900_0x8303";
    String 上传指定照片 = "0x8900_0x8304";
    String 上传指定照片应答 = "0x0900_0x0304";
    String 照片上传初始化 = "0x0900_0x0305";
    String 照片上传初始化应答 = "0x8900_0x8305";
    String 照片上传数据包 = "0x0900_0x0306";
    String 设置计时终端应用参数 = "0x8900_0x8501";
    String 设置计时终端应用参数应答 = "0x0900_0x0501";
    String 设置禁训状态 = "0x8900_0x8502";
    String 设置禁训状态应答 = "0x0900_0x0502";
    String 查询计时终端应用参数 = "0x8900_0x8503";
    String 查询计时终端应用参数应答 = "0x0900_0x0503";



    String 数据压缩上报 = "0x0901";
    String 终端RSA公钥 = "0x0A00";


    String 平台登录请求 = "0x01f0";
    String 平台登录应答 = "0x81f0";
    String 平台登出请求 = "0x01f1";

    String 终端上行消息保留 = "0x0F00 - 0x0FFF";

    String 平台通用应答 = "0x8001";
    String 服务器补传分包请求 = "0x8003";
    String 查询服务器时间应答 = "0x8004";//2019 new
    String 终端注册应答 = "0x8100";

    String 设置终端参数 = "0x8103";
    String 查询终端参数 = "0x8104";
    String 终端控制 = "0x8105";
    String 查询指定终端参数 = "0x8106";
    String 查询终端属性 = "0x8107";
    String 下发终端升级包 = "0x8108";
    String 位置信息查询 = "0x8201";
    String 临时位置跟踪控制 = "0x8202";
    String 人工确认报警消息 = "0x8203";
    String 服务器向终端发起链路检测请求 = "0x8204";//2019 new
    String 文本信息下发 = "0x8300";//2019 modify
    String 事件设置 = "0x8301";//2019 del
    String 提问下发 = "0x8302";//2019 del
    String 信息点播菜单设置 = "0x8303";//2019 del
    String 信息服务 = "0x8304";//2019 del
    String 电话回拨 = "0x8400";
    String 设置电话本 = "0x8401";
    String 车辆控制 = "0x8500";
    String 设置圆形区域 = "0x8600";//2019 modify
    String 删除圆形区域 = "0x8601";
    String 设置矩形区域 = "0x8602";//2019 modify
    String 删除矩形区域 = "0x8603";
    String 设置多边形区域 = "0x8604";//2019 modify
    String 删除多边形区域 = "0x8605";
    String 设置路线 = "0x8606";
    String 删除路线 = "0x8607";
    String 查询区域或线路数据 = "0x8608";//2019 new
    String 行驶记录仪数据采集命令 = "0x8700";
    String 行驶记录仪参数下传命令 = "0x8701";
    String 上报驾驶员身份信息请求 = "0x8702";

    String 多媒体数据上传应答 = "0x8800";

    String 摄像头立即拍摄命令 = "0x8801";
    String 存储多媒体数据检索 = "0x8802";
    String 存储多媒体数据上传 = "0x8803";
    String 录音开始命令 = "0x8804";
    String 单条存储多媒体数据检索上传命令 = "0x8805";
    String 平台RSA公钥 = "0x8A00";

    String 平台下行消息保留 = "0x8F00 - 0x8FFF";
    String 厂商自定义上行消息 = "0xE000 - 0xEFFF";//2019 new
    String 商自定义下行消息 = "0xF000 - 0xFFFF";//2019 new
}