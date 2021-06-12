package org.yzh.web.sign;

public class EncodeUtil {
	public static byte[] toBE(long data) {
		byte[] buffer = new byte[8];
        buffer[0] = (byte)(data >>> 56);
        buffer[1] = (byte)(data >>> 48);
        buffer[2] = (byte)(data >>> 40);
        buffer[3] = (byte)(data >>> 32);
        buffer[4] = (byte)(data >>> 24);
        buffer[5] = (byte)(data >>> 16);
        buffer[6] = (byte)(data >>>  8);
        buffer[7] = (byte)(data >>>  0);
        return buffer;
	}
	
}
