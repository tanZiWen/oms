package com.prosnav.core.jwt.utils;

import java.util.List;

import com.prosnav.core.jwt.domain.User;

public class OauthClient {
	public static OauthClient instance() {
		return new OauthClient();
	}

	public User queryUserById() {
		return null;
	}
	
	public List<User> queryAllUsers() {
		return null;
	}
}
