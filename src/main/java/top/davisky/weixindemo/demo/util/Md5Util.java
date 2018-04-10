package top.davisky.weixindemo.demo.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Md5处理工具类
 * @author zhanngle
 */
public class Md5Util {


	/**
	 * 为输入的字符串生成MD5码
	 */
	public static String toMd5(String str) {

		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("参数不能为空!");
		}

		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String code = hexString.toString().toUpperCase();
		return code;
	}
	
	
	/**
	 * 随机生成UUID值
	 * @return
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}
