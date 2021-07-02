package org.yzh.framework.commons.transform;

import java.util.List;

/**
 * 字节数组与int之间的转换
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Bytes {

    public static int getInt16(byte[] memory, int index) {
        return ((memory[index] & 0xff) << 8) | (memory[index + 1] & 0xff);
    }

    public static int getInt32(byte[] bytes, int start) {
        return  ((bytes[start]     & 0xff) << 24) |
                ((bytes[start + 1] & 0xff) << 16) |
                ((bytes[start + 2] & 0xff) <<  8) |
                ((bytes[start + 3] & 0xff));
    }

    public static byte[] setInt16(byte[] memory, int index, int value) {
        memory[index] = (byte) (value >>> 8);
        memory[index + 1] = (byte) (value);
        return memory;
    }

    public static byte[] setInt32(byte[] memory, int index, int value) {
        memory[index]     = (byte) (value >>> 24);
        memory[index + 1] = (byte) (value >>> 16);
        memory[index + 2] = (byte) (value >>>  8);
        memory[index + 3] = (byte) (value);
        return memory;
    }

    public static byte[] hexListToByte(List<String> hexArr){
        byte[] bytes = new byte[hexArr.size() * 4];
        int index = 0;
        for(String idString:hexArr){
            int id = Integer.parseInt(idString.replaceAll("^0[x|X]", ""),16);
            bytes[index] = (byte)((id>>3*8)&0xff);
            bytes[index+1] = (byte)((id>>2*8)&0xff);
            bytes[index+2] = (byte)((id>>1*8)&0xff);
            bytes[index+3] = (byte)((id>>0*8)&0xff);
            index += 4;
        }
        return bytes;
    }

    public static int[] byteToIntArr(byte[] byteArr){
        int[] valueArr = new int[byteArr.length/4];
        int j = 0,value = 0;
        for(byte i:byteArr){
            if(j%4 == 0 && j != 0){
                valueArr[j/4 - 1] = value;
                j = 0;
                value = 0;
            }
            value +=(int)((i&0xff)<<j*8);
            j ++;
        }
        if(j > 0){
            valueArr[byteArr.length/4 -1] = value;
        }
        return valueArr;
    }

    /**
     * byte[]转int
     * @param bytes 需要转换成int的数组
     * @return int值
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value=0;
        for(int i = 0; i < bytes.length; i++) {
            int shift= (bytes.length-1-i) * 8;
            value +=(bytes[i] & 0xFF) << shift;
        }
        return value;
    }
}