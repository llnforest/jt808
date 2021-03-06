package org.yzh.framework.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.TcpClientUtils;
import org.yzh.framework.commons.TcpServerUtils;
import org.yzh.framework.orm.model.AbstractHeader;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Session {

    private static final Logger log = LoggerFactory.getLogger(Session.class.getSimpleName());

    public static final AttributeKey<Session> KEY = AttributeKey.newInstance(Session.class.getName());

    protected final Channel channel;

    private volatile int serialNo = 0;
    private volatile int packetNo = 1;
    private volatile boolean isAuth = false;
    private volatile String phone = "";

    private boolean registered = false;
    private String clientId;

    private final long creationTime;
    private volatile long lastAccessedTime;
    private Map<String, Object> attributes;
    private Object subject;
    private Object snapshot;
    private int protocolVersion = 0;

    private SessionManager sessionManager;

    protected Session(Channel channel, SessionManager sessionManager) {
        this.channel = channel;
        this.sessionManager = sessionManager;
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
        this.attributes = new TreeMap<>();

    }

    public void writeObject(Object message) {
        log.info("<<<<<<<<<<消息下发{},{}", this, message);
        channel.writeAndFlush(message);
    }

    public int getId() {
        return channel.id().hashCode();
    }

    public int serialNo() {
        return serialNo;
    }

    public int nextSerialNo() {
        if (serialNo >= 0xffff)
            serialNo = 0;
        return serialNo++;
    }

    public int nextPacketNo(){
        if (packetNo >= 0xffff)
            packetNo = 1;
        return packetNo ++;
    }

    public void setIsAuth(boolean isAuth){
        this.isAuth = isAuth;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public String setPhone(String phone){
        return this.phone = phone;
    }
    public String getPhone(){
        return phone;
    }
    public boolean isRegistered() {
        return registered;
    }

    /**
     * 注册到SessionManager
     */
    public void register(AbstractHeader header) {
        this.register(header, null);
    }

    public void register(AbstractHeader header, Object subject) {
        this.clientId = header.getClientId();
        this.registered = true;
        this.subject = subject;
        sessionManager.put(clientId, this);
    }

    public String getClientId() {
        return clientId;
    }


    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public long access() {
        lastAccessedTime = System.currentTimeMillis();
        return lastAccessedTime;
    }

    public Channel getChannel(){
        return this.channel;
    }

    public Collection<String> getAttributeNames() {
        return attributes.keySet();
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }


    public Object getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Object snapshot) {
        this.snapshot = snapshot;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String clientId, int protocolVersion) {
        this.protocolVersion = protocolVersion;
        this.sessionManager.putVersion(clientId, protocolVersion);
    }

    public void invalidate() {
        if(!phone.equals("")){
            TcpClientUtils.removeClientChannel(phone);
            TcpServerUtils.removeMsgByPhone(phone);
        }
        isAuth = false;
        phone = "";
        channel.close();
        sessionManager.callSessionDestroyedListener(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Session that = (Session) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(66);
        sb.append("[ip=").append(channel.remoteAddress());
        sb.append(", cid=").append(clientId);
        sb.append(", reg=").append(registered);
        sb.append(']');
        return sb.toString();
    }
}