/**   
 * @Title: Base64Utils.java 
 * @Package com.foreveross.common.util 
 * @Description: 
* @author lixiaodong   
 * @date 2016年10月10日 下午4:34:34 
 *  
 */
package top.davisky.weixindemo.demo.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;


/**
 * 
 * @ClassName: Base64Utils
 * @Description: 加密Base64工具类
 * @author lixiaodong
 * @email  lixiaodong@foreveross.com
 * @date   2016年10月10日 下午5:05:12 
 *
 */
public class Base64Utils {

	/**
	 * 文件读取缓冲区大小
	 */
	private static final int CACHE_SIZE = 1024;

	/**
	 * <p>
	 * BASE64字符串解码为二进制数据
	 * </p>
	 * 
	 * @param base64
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode(String base64) throws Exception {
		return Base64.decodeBase64(base64.getBytes());
	}

	/**
	 * <p>
	 * 二进制数据编码为BASE64字符串
	 * </p>
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] bytes) throws Exception {
		
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * <p>
	 * 将文件编码为BASE64字符串
	 * </p>
	 * <p>
	 * 大文件慎用，可能会导致内存溢出
	 * </p>
	 * 
	 * @param filePath
	 *            文件绝对路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeFile(String filePath) throws Exception {
		byte[] bytes = fileToByte(filePath);
		return encode(bytes);
	}

	/**
	 * <p>
	 * BASE64字符串转回文件
	 * </p>
	 * 
	 * @param filePath
	 *            文件绝对路径
	 * @param base64
	 *            编码字符串
	 * @throws Exception
	 */
	public static void decodeToFile(String filePath, String base64) throws Exception {
		byte[] bytes = decode(base64);
		byteArrayToFile(bytes, filePath);
	}

	/**
	 * <p>
	 * 文件转换为二进制数组
	 * </p>
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) throws Exception {
		byte[] data = new byte[0];
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				out.write(cache, 0, nRead);
				out.flush();
			}
			out.close();
			in.close();
			data = out.toByteArray();
		}
		return data;
	}

	/**
	 * <p>
	 * 二进制数据写文件
	 * </p>
	 * 
	 * @param bytes
	 *            二进制数据
	 * @param filePath
	 *            文件生成目录
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
		InputStream in = new ByteArrayInputStream(bytes);
		File destFile = new File(filePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		destFile.createNewFile();
		OutputStream out = new FileOutputStream(destFile);
		byte[] cache = new byte[CACHE_SIZE];
		int nRead = 0;
		while ((nRead = in.read(cache)) != -1) {
			out.write(cache, 0, nRead);
			out.flush();
		}
		out.close();
		in.close();
	}
	public static void main(String[] args) throws Exception {
/*		String aa = 	encode("qwe123".getBytes());
		System.out.println(aa);*/
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCB3AAuaiHTtuKN8RgwZTVmASi3Zh/SjCWty3OzDiRaXkWJE+hBXdBi89LRoUdkXZw+B4Wv1PckOnAIpeVLKEPL1f9ATH5SRIBCNCZibsmFFCzrnXNy5AeuZCjQlIhqGTJqd3z5LmVPydBXJ9+WteWgzCE1IVQk3BF3mVWiok4gIwIDAQAB";
		//decodeToFile(Base64Utils.class.getResource("/").getPath()+"res/publicKey",publicKey);
	    System.out.println(Base64Utils.class.getResource("/").getPath()+"res/publicKey");
	}
}