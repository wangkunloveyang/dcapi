package com.datangwealth.modules.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

public class AppCommonUtils {
	public static String APP_CALL_FLAG = "0";
//	public static String API_CALL_FLAG = "1";
	

	/**
	 * AES加密与生成数字签名
	 * 
	 * @param data
	 * @return
	 */
	public static String encrypAndSign(String data, String osFlag) {
		
		//IOS
		if ("1".equals(osFlag)) {
			return encrypAndSignIos(data);
		} else if ("3".equals(osFlag)) {
			return encrypAndSignJsp(data);
		}
		
		JSONObject obj = new JSONObject();
		if (data != null && data.length() > 0) {
			try {
				String aesData = AppAESUtil.encrypt(data, CTEConstance.getEnvProperty("appServerAesKey"));
				String sign = AppDSAUtil.sign(aesData.getBytes(),CTEConstance.getEnvProperty("serverDsaPriKey"));
				obj.put("data2", aesData);
				obj.put("data1", sign);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj.toString();
	}
	
	public static String encrypAndSignJsp(String data) {

		String ret = "";
		if (data != null && data.length() > 0) {
			try {
				JSONObject json = JSONObject.parseObject(data);
				String retMsg = json.getString("retMsg");
				if (retMsg != null && retMsg.length() > 0 ) {
					json.put("retMsg", java.net.URLEncoder.encode(retMsg, "UTF-8"));
				}
				String str=json.toJSONString();
				byte[]b= Base64.encodeBase64URLSafe(str.getBytes("UTF-8"));
				ret=new String(b,"UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * AES解密与校验数字签名
	 * @param data
	 * @return
	 */
	public static String decrypAndSign(String data, String callFlag, String osFlag) {
		//IOS
		if ("1".equals(osFlag)) {
			return decrypAndSignIos(data, callFlag);
		} else if ("3".equals(osFlag)) {
			return decrypAndSignJsp(data);
		}
		
		String retData = null;
		if (data != null && data.length() > 0) {
			JSONObject objData = (JSONObject) JSONObject.parse(data);
			String aesData = (String) objData.get("data2");
			String sign = (String) objData.get("data1");
			try {
				if (AppDSAUtil.verify(aesData.getBytes(), CTEConstance.getEnvProperty("appDsaPubKey"), sign)) {
					if (aesData != null && aesData.length() > 0) {
						retData = AppAESUtil.decrypt(aesData, CTEConstance.getEnvProperty("appServerAesKey"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retData;
	}
	
	/**
	 * jsp页面
	 * @param data
	 * @return
	 */
	public static String decrypAndSignJsp(String data) {

		if (data != null && data.length() > 0) {

			try {
				//BASE64Decoder decorder = new BASE64Decoder();
				//byte[] d = decorder.decodeBuffer(data);
				byte[] d= Base64.decodeBase64(data);
				return new String(d,"UTF-8");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}


	/**
	 * AES加密与生成数字签名
	 * @param data
	 * @return
	 */
	public static String encrypAndSignIos(String data) {
		JSONObject obj = new JSONObject();
		if (data != null && data.length() > 0) {
			try {
				String aesData = IOSSecurityUtil.encrypt(data);
				String sign = IOSSecurityUtil.sign(aesData);
				obj.put("data2", aesData);
				obj.put("data1", sign);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj.toString();
	}

	/**
	 * AES解密与校验数字签名
	 * @param data
	 * @return
	 */
	public static String decrypAndSignIos(String data, String callFlag) {
		String retData = null;
		if (data != null && data.length() > 0) {
			JSONObject objData = (JSONObject) JSONObject.parse(data);
			String aesData = (String) objData.get("data2");
			String sign = (String) objData.get("data1");
			try {
				if (IOSSecurityUtil.verify(aesData, sign)) {
					if (aesData != null && aesData.length() > 0) {
						retData = IOSSecurityUtil.decrypt(aesData);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retData;
	}


	/**
	 * 从byte数组中取得图片的扩展名 
	 * </pre>
	 * @param b
	 * @return
	 */
	public static String getImageExtension(byte[] b) {
		
		String ext = ".jpg";
		
		if (b.length == 0) {
			return ext;
		}
		
		byte[] array = new byte[2];
		array[0] = b[0];
		array[1] = b[1];
		
		String header = bytesToHexString(array);
		
		if (header.startsWith("8950")) {
			ext = ".png";
		} else if (header.startsWith("ffd8")) {
			ext = ".jpg";
		} else if (header.startsWith("424d")) {
			ext = ".bmp";
		}
		
		return ext;
	}
	
	/**
	 * byte数组转换成16进制字符串
	 * </pre>
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }
	    return stringBuilder.toString();  
	}
}
