package com.datangwealth.modules.common.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;



@Service
public class CTEConstance {
	
	
	private static Environment env;
	
	@Resource
	private Environment envAutowired;
	
	@PostConstruct
    public void init() {
       env = envAutowired;
    }


	public static String getEnvProperty(String name) {
		return env.getProperty(name);
	}

}
