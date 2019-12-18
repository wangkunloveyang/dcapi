package com.datangwealth.modules.dao;

import org.apache.ibatis.annotations.Mapper;
import com.datangwealth.modules.entity.UserInfo;

@Mapper
public interface IUserInfo {
	public UserInfo getUserInfo(String id);
	public void isnerta();
	public void isnertb();
}
