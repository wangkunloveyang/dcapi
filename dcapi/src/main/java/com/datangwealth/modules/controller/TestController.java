package com.datangwealth.modules.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.datangwealth.modules.servcie.TestService;

@RestController
@RequestMapping(value = "/TestController")
public class TestController {
	@Autowired
	private TestService testService;
	
	@ResponseBody
	@RequestMapping(value = "getName")
	public String getName(HttpServletRequest request, HttpServletResponse response)  {
		String param = (String)request.getAttribute("param");
		String objectResult = testService.getUserInfo(param);
		return objectResult;
	}
}
