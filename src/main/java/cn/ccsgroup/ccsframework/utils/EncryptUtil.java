package cn.ccsgroup.ccsframework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.thoughtworks.xstream.core.util.Base64Encoder;

public class EncryptUtil {
	public static String MD2 = "MD2";
	public static String MD5 = "MD5";
	public static String SHA1 = "SHA-1";
	public static String SHA256 = "SHA-256";
	public static String SHA384 = "SHA-384";
	public static String SHA512 = "SHA-512";

	/**
	 * @param str
	 *            加密明文
	 * @param algorithm
	 *            加密算法
	 * @return 加密结果字符串
	 */
	public static String encrypt(String str, String algorithm) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.reset();
			messageDigest.update(str.getBytes());
			byte[] res = messageDigest.digest();
			return byte2hex(res);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * 将byte数组转换为16进制表示
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byte2hex(byte[] byteArray) {
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}

		}
		return md5StrBuff.toString();
	}
	
	public static String getSignature(String timestamp, String nonce, String token,String algorithm) {
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		String[] arrTmp = { token, timestamp, nonce };
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		// 2.将三个参数字符串拼接成一个字符串进行sha1加密
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String expectedSignature = encrypt(sb.toString(),algorithm);
		return expectedSignature;
	}
	
	public static boolean validate(String signature, String timestamp, String nonce,String token,String algorithm) {
		String expectedSignature = getSignature(timestamp, nonce, token,algorithm);
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于
		if (expectedSignature.equals(signature)) {
			return true;
		}
		return false;
	}
	
	public static String encryptBASE64(String loginId,String password,String timestamp) throws Exception {
		byte[] key = new String(loginId+":"+password+":"+timestamp).getBytes();
		return new Base64Encoder().encode(key);
	}
	
	public static String decryptBASE64(String key) throws Exception {
		return new String((new Base64Encoder()).decode(key));
	}
}
