package com.datangwealth.modules.common.utils;


import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
public class IOSSecurityUtil {

    private static String sKey = CTEConstance.getEnvProperty("iosSKey");
    private static String ivParameter = CTEConstance.getEnvProperty("iosSKey");
    private static String signKey = CTEConstance.getEnvProperty("iosSingKey");
	
    // 加密
    public static String encrypt(String sSrc) throws Exception {
        return encrypt(sSrc, sKey, ivParameter);
    }

    public static String encrypt(String sSrc, String key, String ivs) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return new Base64().encodeToString(encrypted);// 此处使用BASE64做转码。
    }
    
    // 解密
    public static String decrypt(String sSrc) throws Exception {
        return decrypt(sSrc, sKey, ivParameter);
    }

    public static String decrypt(String sSrc,String key,String ivs) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

	public static boolean verify(String content, String strSign) throws Exception {//content:aesdata;striSign:数字签名

		String md5 = IOSSecurityUtil.MD5(content);//数据内容
		String io = IOSSecurityUtil.decrypt(strSign, signKey, signKey);//数字签名
		/*strSign:数字签名字符串
		 *content:经过AES加密过的数据字符串*/
		if (md5.equals(io)) {
			return true;
		}
		return false;
		
	}

	public static String sign(String content) throws Exception {

		String md5 = IOSSecurityUtil.MD5(content);
		String sign = IOSSecurityUtil.encrypt(md5, signKey, signKey);
		
		return sign;
	}
	
    public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * <pre>
	 *
	 * </pre>
	 * @param args
	 */
	public static void main(String[] args) {

        //String key = "IXyZcrazyF2jdha1";
        String content = "Q5XnyKkAXWbrkLzKcjHMWdyoYzpc3xPq2IB+jbafot4=";
        String sign = "VPEMa4UneEZ4UWcQ1DUrhB10lzGwB4yCP+S9WmcHykmPRAQtYO3kje/bp7N8jR5v";
		try {
			boolean flg = IOSSecurityUtil.verify(content, sign);

			System.out.println(flg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

