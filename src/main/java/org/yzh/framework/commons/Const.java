package org.yzh.framework.commons;

public class Const {
    public static String platNum = "01234";
    //图片存储地址
//    public static final String photoPath = "http:\\\\192.168.100.53:8080/uploads/traintimeimg/";
    public static final String photoPath = "F:\\jt808_img/";

    public static final String uploadPhotoUrl = "http://192.168.100.53:8080/xh/trainTimeImgUpload";
    public static final String logoutUrl = "http://192.168.100.53:8080/xh/studentTrainFinish";
    //相关指定是否转发
    public static final boolean isMsgReplay = false;
    //相关指令是否需鉴权
    public static final boolean isAuth = false;
    //终端测试手机号
    public static final String phone = "17299841738";
    //起始标记
    public static final byte[] delimiter = new byte[]{0x7e};
    //是否发送心跳
    public static final boolean isHeartBeat = false;

    public static void setPlatNum(String platNum1){
        platNum = platNum1;
    }

    public static String getPlatNum(){
        return platNum;
    }

}
