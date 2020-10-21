package org.yzh.protocol.commons.transform;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.yzh.framework.orm.model.DataType;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ParameterType {

    Ox0001(0x0001, DataType.DWORD, "终端心跳发送间隔，单位为秒(s)"),
    Ox0002(0x0002, DataType.DWORD, "TCP消息应答超时时间，单位为秒(s)"),
    Ox0003(0x0003, DataType.DWORD, "TCP消息重传次数"),
    Ox0004(0x0004, DataType.DWORD, "UDP消息应答超时时间，单位为秒(s)"),
    Ox0005(0x0005, DataType.DWORD, "UDP消息重传次数"),
    Ox0006(0x0006, DataType.DWORD, "SMS消息应答超时时间，单位为秒(s)"),
    Ox0007(0x0007, DataType.DWORD, "SMS消息重传次数"),

    Ox0010(0x0010, DataType.STRING, "主服务器APN，无线通信拨号访问点。若网络制式为CDMA，则该处为PPP拨号号码"),
    Ox0011(0x0011, DataType.STRING, "主服务器无线通信拨号用户名"),
    Ox0012(0x0012, DataType.STRING, "主服务器无线通信拨号密码"),
    Ox0013(0x0013, DataType.STRING, "主服务器地址，IP或域名"),
    Ox0014(0x0014, DataType.STRING, "备份服务器APN，无线通信拨号访问点"),
    Ox0015(0x0015, DataType.STRING, "备份服务器无线通信拨号用户名"),
    Ox0016(0x0016, DataType.STRING, "备份服务器无线通信拨号密码"),
    Ox0017(0x0017, DataType.STRING, "备份服务器地址，IP或域名（2019版 以冒号分割主机和端口，多个服务器使用分号分隔）"),
    Ox0018(0x0018, DataType.DWORD, "服务器TCP端口(2013版)"),
    Ox0019(0x0019, DataType.DWORD, "服务器UDP端口(2013版)"),


    Ox001A(0x001A, DataType.STRING, "道路运输证IC卡认证主服务器IP地址或域名"),
    Ox001B(0x001B, DataType.DWORD, "道路运输证IC卡认证主服务器TCP端口"),
    Ox001C(0x001C, DataType.DWORD, "道路运输证IC卡认证主服务器UDP端口"),
    Ox001D(0x001D, DataType.STRING, "道路运输证IC卡认证主服务器IP地址或域名，端口同主服务器"),

    Ox0020(0x0020, DataType.DWORD, "位置汇报策略，0：定时汇报；1：定距汇报；2：定时和定距汇报"),
    Ox0021(0x0021, DataType.DWORD, "位置汇报方案，0：根据ACC状态；1：根据登录状态和ACC状态，先判断登录状态，若登录再根据ACC状态"),
    Ox0022(0x0022, DataType.DWORD, "驾驶员未登录汇报时间间隔，单位为秒(s),>0"),

    Ox0023(0x0023, DataType.STRING, "从服务器APN。该值为空时，终端应使用主服务器相同配置"),
    Ox0024(0x0024, DataType.STRING, "从服务器无线通信拨号用户名。该值为空时，终端应使用主服务器相同配置"),
    Ox0025(0x0025, DataType.STRING, "从服务器无线通信拨号密码。该值为空时，终端应使用主服务器相同配置"),
    Ox0026(0x0026, DataType.STRING, "从服务器备份地址、IP或域名。主服务器IP地址或域名，端口同主服务器"),

    Ox0027(0x0027, DataType.DWORD, "休眠时汇报时间间隔，单位为秒(s),>0"),
    Ox0028(0x0028, DataType.DWORD, "紧急报警时汇报时间间隔，单位为秒(s),>0"),
    Ox0029(0x0029, DataType.DWORD, "缺省时间汇报间隔，单位为秒(s),>0"),

    Ox002C(0x002C, DataType.DWORD, "缺省距离汇报间隔，单位为米(m),>0"),
    Ox002D(0x002D, DataType.DWORD, "驾驶员未登录汇报距离间隔，单位为米(m),>0"),
    Ox002E(0x002E, DataType.DWORD, "休眠时汇报距离间隔，单位为米(m),>0"),
    Ox002F(0x002F, DataType.DWORD, "紧急报警时汇报距离间隔，单位为米(m),>0"),
    Ox0030(0x0030, DataType.DWORD, "拐点补传角度，<180°"),
    Ox0031(0x0031, DataType.WORD, "电子围栏半径，单位为米"),
    Ox0032(0x0032, DataType.BYTES, "违规行驶时段范围，精确到分"),

    Ox0040(0x0040, DataType.STRING, "监控平台电话号码"),
    Ox0041(0x0041, DataType.STRING, "复位电话号码，可采用此电话号码拨打终端电话让终端复位"),
    Ox0042(0x0042, DataType.STRING, "恢复出厂设置电话号码，可采用此电话号码拨打终端电话让终端恢复出厂设置"),
    Ox0043(0x0043, DataType.STRING, "监控平台SMS电话号码"),
    Ox0044(0x0044, DataType.STRING, "接收终端SMS文本报警号码"),
    Ox0045(0x0045, DataType.DWORD, "终端电话接听策略，0：自动接听；1：ACC ON时自动接听，OFF时手动接听"),
    Ox0046(0x0046, DataType.DWORD, "每次最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制"),
    Ox0047(0x0047, DataType.DWORD, "当月最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制"),
    Ox0048(0x0048, DataType.STRING, "监听电话号码"),
    Ox0049(0x0049, DataType.STRING, "监管平台特权短信号码"),

    Ox0050(0x0050, DataType.DWORD, "报警屏蔽字。与位置信息汇报消息中的报警标志相对应，相应位为1则相应报警被屏蔽"),
    Ox0051(0x0051, DataType.DWORD, "报警发送文本SMS开关，与位置信息汇报消息中的报警标志相对应，相应位为1则相应报警时发送文本SMS"),
    Ox0052(0x0052, DataType.DWORD, "报警拍摄开关，与位置信息汇报消息中的报警标志相对应，相应位为1则相应报警时摄像头拍摄"),
    Ox0053(0x0053, DataType.DWORD, "报警拍摄存储标志，与位置信息汇报消息中的报警标志相对应，相应位为1则对相应报警时牌的照片进行存储，否则实时长传"),
    Ox0054(0x0054, DataType.DWORD, "关键标志，与位置信息汇报消息中的报警标志相对应，相应位为1则对相应报警为关键报警"),
    Ox0055(0x0055, DataType.DWORD, "最高速度，单位为公里每小时(km/h)"),
    Ox0056(0x0056, DataType.DWORD, "超速持续时间，单位为秒(s)"),
    Ox0057(0x0057, DataType.DWORD, "连续驾驶时间门限，单位为秒(s)"),
    Ox0058(0x0058, DataType.DWORD, "当天累计驾驶时间门限，单位为秒(s)"),
    Ox0059(0x0059, DataType.DWORD, "最小休息时间，单位为秒(s)"),
    Ox005A(0x005A, DataType.DWORD, "最长停车时间，单位为秒(s)"),

    Ox005B(0x005B, DataType.DWORD, "超速预警差值"),
    Ox005C(0x005C, DataType.DWORD, "疲劳驾驶预警插值"),
    Ox005D(0x005D, DataType.DWORD, "碰撞报警参数"),
    Ox005E(0x005E, DataType.DWORD, "侧翻报警参数"),

    Ox0064(0x0064, DataType.DWORD, "定时拍照参数"),
    Ox0065(0x0065, DataType.DWORD, "定时拍照参数"),

    Ox0070(0x0070, DataType.DWORD, "图像/视频质量，1-10,1最好"),
    Ox0071(0x0071, DataType.DWORD, "亮度，0-255"),
    Ox0072(0x0072, DataType.DWORD, "对比度，0-127"),
    Ox0073(0x0073, DataType.DWORD, "饱和度，0-127"),
    Ox0074(0x0074, DataType.DWORD, "色度，0-255"),

    Ox0080(0x0080, DataType.DWORD, "车辆里程表读数，1/10km"),
    Ox0081(0x0081, DataType.WORD, "车辆所在的省域ID"),
    Ox0082(0x0082, DataType.WORD, "车辆所在的市域ID"),
    Ox0083(0x0083, DataType.STRING, "公安交通管理部门颁发的机动车号牌"),
    Ox0084(0x0084, DataType.BYTE, "车牌颜色，按照JT/T415-2006的5.4.12"),

    Ox0090(0x0090, DataType.BYTE, "定位模式"),
    Ox0091(0x0091, DataType.BYTE, "波特率"),
    Ox0092(0x0092, DataType.BYTE, "模块详细定位数据输出频率"),
    Ox0093(0x0093, DataType.DWORD, "模块详细定位数据采集频率，单位为秒，默认为1"),
    Ox0094(0x0094, DataType.BYTE, "模块详细定位数据上传方式"),
    Ox0095(0x0095, DataType.DWORD, "模块详细定位数据上传设置"),
    Ox0100(0x0100, DataType.DWORD, "总线通道1 采集时间间隔(ms)，0 表示不采集"),
    Ox0101(0x0101, DataType.WORD, "总线通道1 上传时间间隔(s)，0 表示不上传"),
    Ox0102(0x0102, DataType.DWORD, "总线通道2 采集时间间隔(ms)，0 表示不采集"),
    Ox0103(0x0103, DataType.WORD, "总线通道2 上传时间间隔(s)，0 表示不上传"),
    Ox0110(0x0110, DataType.BYTES, "总线ID 单独采集设置");

    public int id;
    public DataType type;
    public String desc;

    ParameterType(int id, DataType type, String desc) {
        this.id = id;
        this.type = type;
        this.desc = desc;
    }
}