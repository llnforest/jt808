package org.yzh.web.sign;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.PrivateKey;

public class Sign implements ISign {

	public String sign(String data, long timestamp, PrivateKey key,String version) throws Exception {
		String signType = version.equals("e1")?"SHA1":(version.equals("e3")?"SHA-512":"SHA-256");
		MessageDigest md = MessageDigest.getInstance(signType);
		md.update(data.getBytes("utf-8"));
		md.update(EncodeUtil.toBE(timestamp));
		byte[] hash = md.digest();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal(hash);
		return HexBin.encode(encrypted);
	}

	public String sign(byte[] bytes, long timestamp, PrivateKey key,String version) throws Exception {
		String signType = version.equals("e1")?"SHA1":(version.equals("e3")?"SHA-512":"SHA-256");
		MessageDigest md = MessageDigest.getInstance(signType);
		md.update(bytes);
		md.update(EncodeUtil.toBE(timestamp));
		byte[] hash = md.digest();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal(hash);
		return HexBin.encode(encrypted);
	}

	@Override
	public byte[] signTest(String data, long timestamp, PrivateKey key, String version) throws Exception {
		String signType = version.equals("e1")?"SHA1":(version.equals("e3")?"SHA-512":"SHA-256");
		MessageDigest md = MessageDigest.getInstance(signType);
		md.update(data.getBytes("utf-8"));
		md.update(EncodeUtil.toBE(timestamp));
		byte[] hash = md.digest();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal(hash);
		return encrypted;
	}

}
