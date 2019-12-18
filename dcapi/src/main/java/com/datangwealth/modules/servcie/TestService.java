package com.datangwealth.modules.servcie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.datangwealth.modules.dao.IUserInfo;
import com.datangwealth.modules.entity.UserInfo;

@Service
public class TestService {
	
	@Autowired
	private IUserInfo userInfo;
	
	@Transactional
	public String getUserInfo(String param) {
		JSONObject response = new JSONObject();
		
		JSONObject paramobj = JSONObject.parseObject(param);
		String id = paramobj.getString("id");
		UserInfo info = userInfo.getUserInfo(id);
		
		userInfo.isnerta();
		int a = 1/0;
		userInfo.isnertb();
		response.put("name", info.getUserName());
		return response.toJSONString();
	}

}
