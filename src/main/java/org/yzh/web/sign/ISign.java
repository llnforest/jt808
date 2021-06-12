package org.yzh.web.sign;

import java.security.PrivateKey;

public interface ISign {
	public String sign(String data, long timestamp, PrivateKey key,String version) throws Exception;
	public String sign(byte[] bytes, long timestamp, PrivateKey key,String version) throws Exception;
	public byte[] signTest(String data, long timestamp, PrivateKey key,String version) throws Exception;
}
