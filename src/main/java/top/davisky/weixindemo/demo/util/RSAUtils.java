/**   
 * @Title: RSAUtils.java 
 * @Package com.foreveross.common.util 
 * @Description: 
* @author lixiaodong   
 * @date 2016年10月10日 下午4:15:18 
 *  
 */
package top.davisky.weixindemo.demo.util;



import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: RSAUtils
 * @Description: RSA加密工具类
 * @author lixiaodong
 * @email  lixiaodong@foreveross.com
 * @date   2016年10月10日 下午5:07:07 
 *
 */
public class RSAUtils {
	/** 公钥 */
	private  String publicKey;
	/** 私钥 */
	private  String privateKey;

    /**
     * 加密算法RSA
     */
    public  final String KEY_ALGORITHM = "RSA";
    
    /**
     * 签名算法
     */
    public  final String SIGNATURE_ALGORITHM = "MD5withRSA";


    
    /**
     * RSA最大加密明文大小
     */
    private  final int MAX_ENCRYPT_BLOCK = 117;
    
    /**
     * RSA最大解密密文大小
     */
    private  final int MAX_DECRYPT_BLOCK = 128;
    
    public RSAUtils(){
    	 try {
			createKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public RSAUtils(String publicKey, String privateKey){
		this.publicKey=publicKey;
		this.privateKey=privateKey;
	}
	
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     * 
     * @return
     * @throws Exception
     */
    public  void createKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        publicKey = Base64Utils.encode(pubKey.getEncoded());
        privateKey= Base64Utils.encode(priKey.getEncoded());
    }
    
  
    /**
     * 
     * @Title: getPrivateKey 
     * @Description: 获取私钥
     * @return
     * @throws Exception 
     * String
     */
    public  String getPrivateKey()  {
        return privateKey;
    }

    /**
     * 
     * @Title: getPublicKey 
     * @Description: 获取公钥
     * @return
     * @throws Exception 
     * String
     */
    public  String getPublicKey() {
        return publicKey;
    }
    
    
    
    /**
     * 
     * @Title: decryptByPrivateKey 
     * @Description: 私钥解密
     * @param encryptedData 已加密数据
     * @return
     * @throws Exception 
     * byte[]
     */
    public  byte[] decryptByPrivateKey(byte[] encryptedData)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

   

  
    
    /**
     * 
     * @Title: encryptByPrivateKey 
     * @Description: 私钥加密
     * @param data  源数据
     * @return
     * @throws Exception 
     * byte[]
     */
    public  byte[] encryptByPrivateKey(byte[] data)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    
    
    /**
     * 
     * @Title: decryptByPublicKey 
     * @Description: 公钥解密
     * @param encryptedData 已加密数据
     * @return
     * @throws Exception 
     * byte[]
     */
    public  byte[] decryptByPublicKey(byte[] encryptedData)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    
   

    /**
     * 
     * @Title: sign 
     * @Description: 签名 
     * @param data 签名的数据
     * @return
     * @throws Exception 
     * String
     */
     public  String sign(byte[] data) throws Exception {
         byte[] keyBytes = Base64Utils.decode(privateKey);
         PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
         KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
         PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
         Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
         signature.initSign(privateK);
         signature.update(data);
         return Base64Utils.encode(signature.sign());
     }

   
     /**
      * 
      * @Title: verify 
      * @Description: 校验数字签名
      * @param data data 已加密数据
      * @param
      * @return
      * @throws Exception 
      * boolean
      */
     public  boolean verify(byte[] data, String sign)
             throws Exception {
         byte[] keyBytes = Base64Utils.decode(publicKey);
         X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
         KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
         PublicKey publicK = keyFactory.generatePublic(keySpec);
         Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
         signature.initVerify(publicK);
         signature.update(data);
         return signature.verify(Base64Utils.decode(sign));
     }

     /**
      * 
      * @Title: encryptByPublicKey 
      * @Description: 公钥加密
      * @param data 源数据
      * @return
      * @throws Exception 
      * byte[]
      */
     public  byte[] encryptByPublicKey(byte[] data)
             throws Exception {
         byte[] keyBytes = Base64Utils.decode(publicKey);
         X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
         KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
         Key publicK = keyFactory.generatePublic(x509KeySpec);
         // 对数据加密
         Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
         cipher.init(Cipher.ENCRYPT_MODE, publicK);
         int inputLen = data.length;
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         int offSet = 0;
         byte[] cache;
         int i = 0;
         // 对数据分段加密
         while (inputLen - offSet > 0) {
             if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                 cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
             } else {
                 cache = cipher.doFinal(data, offSet, inputLen - offSet);
             }
             out.write(cache, 0, cache.length);
             i++;
             offSet = i * MAX_ENCRYPT_BLOCK;
         }
         byte[] encryptedData = out.toByteArray();
         out.close();
         return encryptedData;
     }
     
    public static void main(String[] args) throws Exception{



    	/**登录信息DEMO*/
    	String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYl6iT4hbZt1kBiC0dp+UU7krO25wuxj7Z6HJ2QKFzOcerjo2DsiF7MpMQQtmml9lY///KC+BkTRbizNp8hSgCuVBOnOsxad57zoz5eLEP4ygDff5ihOLj4Xs+3etmOBYP6BpGuQ6gZW5YLoC9rfjZNsKblD2Q0+dpsEayCcemHQIDAQAB";
    	String privatekey="";
    	RSAUtils resUtils = new RSAUtils(publickey,privatekey);

    	String appid = "7";
    	String timestamp =System.currentTimeMillis()+"";
    	String sign = Md5Util.toMd5(appid+timestamp);
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("appid", appid);
    	map.put("timestamp", timestamp);
    	map.put("sign", sign);
    	String json = JsonUtil.toJson(map);
    	//json="{\"appid\":\"wx4fc4e716590caa3e\",\"orderNo\":\"20170915001\",\"openId\":\"oijr-vrPkNyINcZ-tC5PiTf1ZKjE\",\"orderName\":\"长安商城订单\",\"orderTitle\":\"手电\",\"weixinTotalFee\":\"1\",\"orderNotifyUrlBack\":\"http://cms.changan.com.cn/caecmg/server/weixin/orderPage/back\",\"orderNotifyUrlFront\":\"http://cms.changan.com.cn/caecmg/html/bssuccess.html?q=1\",\"bsId\":1,\"weixinTradeType\":\"JSAPI\"}";
    	System.out.println(json);
        System.out.println("开始加密");
    	String verify = Base64Utils.encode(resUtils.encryptByPublicKey(json.getBytes()));
    	System.out.println(verify);
    	verify = "ras+6pzmf9sW+h92TlVhxYsuqgUUuXMbG3DYgjIF8eO0QcW0y0J2DjDr8wKapLy4mxGUCKZYE9h2f4UBSJEiqXJYFRgbW/TCVeKzFYgq8j/zZauZ2f4K7ss0bv5Ai95bFI77dnCLmQyKAYmGQrCdN9Kkq2D9IVQogHA5itCnF8k=";
    	System.out.println("开始解密");
    	String decryptstr =    new String(resUtils.decryptByPrivateKey(Base64Utils.decode(verify)));
    	System.out.println(decryptstr);
    /*	String  aa= "RqVozQqZAlbEqTXvJQoVi8Eg22kDSOJqHQmJL5yBn4NT1wCApZyECisBxXiMEyE5TeaXg0ouKUaSEDALyCXp0JpQDPsgBSIPZDxy6g+iJr8d+azCaLUVTehmmqCs4jKcOT1Tr/vwQURs3b3IWxXLjaHtts4jG1igEwBoZmWRSBc=";
    	String de = new String(resUtils.decryptByPrivateKey(Base64Utils.decode(aa)));
    	System.out.println(de);*/
    	/*System.err.println("公钥加密——私钥解密");
    	System.out.println("公钥:"+resUtils.getPublicKey());
    	System.out.println("私钥:"+resUtils.getPrivateKey());
		String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
		System.out.println("\r加密前文字：\r\n" + source);
		byte[] data = source.getBytes();
		byte[] encodedData = resUtils.encryptByPublicKey(data);
		System.out.println("加密后文字：\r\n" + new String(encodedData));
		byte[] decodedData = resUtils.decryptByPrivateKey(encodedData);
		String target = new String(decodedData);
		System.out.println("解密后文字: \r\n" + target);
		System.err.println("私钥加密——公钥解密");
		String source1 = "这是一行测试RSA数字签名的无意义文字";
		System.out.println("原文字：\r\n" + source1);
		byte[] data1 = source.getBytes();
		byte[] encodedData1 = resUtils.encryptByPrivateKey(data1);
		System.out.println("加密后：\r\n" + new String(encodedData1));
		byte[] decodedData1 = resUtils.decryptByPublicKey(encodedData1);
		String target1 = new String(decodedData1);
		System.out.println("解密后: \r\n" + target1);
		System.err.println("私钥签名——公钥验证签名");
		String sign = resUtils.sign(encodedData1);
		System.err.println("签名:\r" + sign);
		boolean status = resUtils.verify(encodedData1, sign);
		System.err.println("验证结果:\r" + status);*/
	}
	
} 
