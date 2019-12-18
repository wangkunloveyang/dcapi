package com.datangwealth.modules.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.datangwealth.modules.common.utils.AppUtils;
/**
 * aop环绕controller拦截参数做统一加解密
 * @author jason
 *
 */
@Aspect  
@Component  
public class DecodeAop {
	private static final Logger log = LoggerFactory.getLogger(DecodeAop.class);

	@Pointcut("execution(public * com.datangwealth.modules.controller.*.*(..))")
	public void decodePointCut() {
	};

	@Around(value = "decodePointCut()")
	public Object decode(ProceedingJoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = attributes.getRequest();
		String param = req.getParameter("param");
		String osFlag = req.getParameter("osFlag");
		String method_name = joinPoint.getSignature().getName();
		String class_name = joinPoint.getTarget().getClass().getName();
		try {
			//参数解密
			param = AppUtils.decryptParam(osFlag, param);
			req.setAttribute("param", param);
			log.info("==============["+class_name+"."+method_name+"]解密后的参数param："+param);
			//继续执行
			String result = (String) joinPoint.proceed();
			log.info("==============["+class_name+"."+method_name+"]返回结果："+result);
			//结果加密
			result = AppUtils.encryptParam(osFlag, result);
			return result;
		} catch (Throwable e) {
			log.error("参数解析失败",e);
			JSONObject resultJSON=new JSONObject();
			resultJSON.put("retCode", "-1");
			resultJSON.put("retMsg", "系统异常，请联系相关负责人！");
			//结果加密
			return AppUtils.encryptParam(osFlag, resultJSON.toString());
		}

	}
}
