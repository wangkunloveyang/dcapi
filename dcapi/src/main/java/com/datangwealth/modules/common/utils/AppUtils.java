package com.datangwealth.modules.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 参数处理类
 * @author user
 *
 */
public class AppUtils {
	
	private static final Logger logger= LoggerFactory.getLogger(AppUtils.class);
	
	/**
	 * APP端参数解密
	 * @param osFlag
	 * @param versionNo
	 * @param param
	 * @return
	 */
	public static String decryptParam(String osFlag,String param) {
		logger.info("param:" + param);
		// 校验数字签名、解密
		param = AppCommonUtils.decrypAndSign(param, AppCommonUtils.APP_CALL_FLAG, osFlag);
		
		return param;
	 }
	
	/**
	 * APP端返回结果加密
	 * @param osFlag
	 * @param result
	 * @return
	 */
	public static String encryptParam(String osFlag,String result) {
		//生成数字签名，加密
		result = AppCommonUtils.encrypAndSign(result, osFlag);
		logger.info("result:" + result);
		return result;
	 }
	

}
