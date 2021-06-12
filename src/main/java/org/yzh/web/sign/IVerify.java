package org.yzh.web.sign;

import java.security.cert.X509Certificate;

public interface IVerify {
	public boolean verify(String data, long timestamp, String encodedEncryptedStr,
                          X509Certificate userCert, String version) throws Exception;
	public boolean verify(byte[] bytes, long timestamp, String encodedEncryptedStr,
						  X509Certificate userCert, String version) throws Exception;

	public boolean verify(String data, long timestamp, byte[] encryptedStr,
						  X509Certificate userCert, String version) throws Exception;

	public boolean verify(byte[] data, byte[] encryptedStr,
						  X509Certificate userCert, String version) throws Exception;
}
