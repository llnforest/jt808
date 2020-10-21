package org.yzh.web.model.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.protocol.commons.Charsets;

import java.io.*;
import java.time.LocalDate;

public class DeviceInfo {

    /** 签发日期 */
    private LocalDate issuedAt;
    /** 有效期 （日） */
    private int validAt;
    /** 车牌颜色 */
    private byte plateColor;
    /** 车牌号 */
    private String plateNo;
    /** 设备ID */
    private String deviceId;

    public DeviceInfo() {
    }

    public LocalDate getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDate issuedAt) {
        this.issuedAt = issuedAt;
    }

    public int getValidAt() {
        return validAt;
    }

    public void setValidAt(int validAt) {
        this.validAt = validAt;
    }

    public byte getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(byte plateColor) {
        this.plateColor = plateColor;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public static DeviceInfo formBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bis)) {

            DeviceInfo result = new DeviceInfo();
            byte[] temp;
            dis.read(temp = new byte[3]);
            result.setIssuedAt(Bcd.toDate(temp));
            result.setValidAt(dis.readUnsignedByte());
            int len = dis.readUnsignedByte();
            dis.read(temp = new byte[len]);
            result.setDeviceId(new String(temp, Charsets.GBK));

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(DeviceInfo deviceToken) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(32);
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.write(Bcd.from(deviceToken.getIssuedAt()));
            dos.writeByte(deviceToken.getValidAt());
            byte[] bytes = deviceToken.getDeviceId().getBytes(Charsets.GBK);
            dos.writeByte(bytes.length);
            dos.write(bytes);

            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("issuedAt", issuedAt)
                .append("validAt", validAt)
                .append("plateColor", plateColor)
                .append("plateNo", plateNo)
                .append("deviceId", deviceId)
                .toString();
    }
}