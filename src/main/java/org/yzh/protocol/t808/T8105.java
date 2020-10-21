package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.终端控制)
public class T8105 extends AbstractMessage<Header> {

    private int command;
    private String parameter;

    public T8105() {
    }

    public T8105(String mobileNo, int command, String parameter) {
        super(new Header(mobileNo, JT808.终端控制));
        this.command = command;
        this.parameter = parameter;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "命令字")
    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    @Field(index = 1, type = DataType.STRING, desc = "命令参数")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}