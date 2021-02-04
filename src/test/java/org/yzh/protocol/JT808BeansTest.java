package org.yzh.protocol;

import org.junit.Test;

import static org.yzh.protocol.BeanTest.selfCheck;
import static org.yzh.protocol.JT808Beans.*;

/**
 * JT/T 808协议单元测试类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JT808BeansTest {

    @Test
    public void testT0A00_8A00() {
//        selfCheck(H2013(T0A00_8A00()));
        selfCheck(H2019(T0A00_8A00()));
    }


    @Test
    public void testT0001() {
//        selfCheck(H2013(T0001()));
        selfCheck(H2019(T0001()));
    }

    @Test
    public void testT0002() {
//        selfCheck(H2013(T0002()));
        selfCheck(H2019(T0002()));
    }

    @Test
    public void testT0200() {
//        selfCheck(H2013(T0200Attributes()));
        selfCheck(H2019(T0200()));
    }



    @Test
    public void testT0100() {
//        selfCheck(H2013(T0100()));
        selfCheck(H2019(T0100()));
    }



    @Test
    public void testT8003() {
//        selfCheck(H2013(T8003()));
        selfCheck(H2019(T8003()));
    }





    @Test
    public void testT8900_0900() {
//        selfCheck(H2013(T8900_0900()));
        selfCheck(H2019(T8900_0900()));
    }

}