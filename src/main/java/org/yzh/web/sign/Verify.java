package org.yzh.web.sign;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.web.service.impl.JsDeviceServiceImpl;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class Verify implements IVerify {
	private static final Logger log = LoggerFactory.getLogger(JsDeviceServiceImpl.class.getSimpleName());
	public boolean verify(String data, long timestamp, String encodedEncryptedStr,
			X509Certificate userCert, String version) throws Exception {
		String signType = version.equals("e1")?"SHA1":(version.equals("e3")?"SHA-512":"SHA-256");
		MessageDigest md = MessageDigest.getInstance(signType);
		md.update(data.getBytes());
		md.update(EncodeUtil.toBE(timestamp));
		byte[] hash = md.digest();

		byte[] encryptedStr = HexBin.decode(encodedEncryptedStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, userCert);
		byte[] plain = cipher.doFinal(encryptedStr);
		boolean ok = Arrays.equals(hash, plain);
		return ok;
	}

	public boolean verify(byte[] bytes, long timestamp, String encodedEncryptedStr,
						  X509Certificate userCert, String version) throws Exception {
		String signType = version.equals("e1")?"SHA1":(version.equals("e3")?"SHA-512":"SHA-256");
		MessageDigest md = MessageDigest.getInstance(signType);
		md.update(bytes);
		md.update(EncodeUtil.toBE(timestamp));
		byte[] hash = md.digest();

		byte[] encryptedStr = HexBin.decode(encodedEncryptedStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, userCert);
		byte[] plain = cipher.doFinal(encryptedStr);
		boolean ok = Arrays.equals(hash, plain);
		return ok;
	}

	public boolean verify(String data, long timestamp, byte[] encryptedStr, X509Certificate userCert, String version) throws Exception {
		String signType = version.equals("e1")?"SHA1":(version.equals("e3")?"SHA-512":"SHA-256");
		MessageDigest md = MessageDigest.getInstance(signType);
		md.update(data.getBytes());
		md.update(EncodeUtil.toBE(timestamp));
		byte[] hash = md.digest();
		log.info("hash:{}",hash);


		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, userCert);
		byte[] plain = cipher.doFinal(encryptedStr);
		boolean ok = Arrays.equals(hash, plain);
		return ok;
	}

	@Override
	public boolean verify(byte[] data, byte[] encryptedStr, X509Certificate userCert, String version) throws Exception {
		String signType = version.equals("e1")?"SHA1":(version.equals("e3")?"SHA-512":"SHA-256");
		MessageDigest md = MessageDigest.getInstance(signType);
		md.update(data);
		byte[] hash = md.digest();
		log.info("hash:{}",hash);
		log.info("hashLength:{}",hash.length);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, userCert);
		byte[] plain = cipher.doFinal(encryptedStr);
		return  Arrays.equals(hash, plain);
	}


}
