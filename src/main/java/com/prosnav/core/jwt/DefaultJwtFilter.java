package com.prosnav.core.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.DeserializationFeature;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.core.jwt.utils.SpringContextUtil;

public class DefaultJwtFilter extends BasicJwtFilter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private StringRedisTemplate redisTemplate;

	@Override
	public Object loadUser(String userName, String jwt) {
//		ValueOperations<String, String> ops = redisTemplate.opsForValue();
//		String userKey = this.userKey + userName;
		String userStr; // = ops.get(userKey);
		//if(StringUtils.isEmpty(userStr)) {
			userStr = requestUser(jwt);
			//ops.set(userKey, userStr);
		//}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		User user = null;
		try {
			logger.debug(userStr);
			 user = mapper.readValue(userStr, User.class);
			
		} catch (Exception e) {
			throw new RuntimeException("Build user failed.", e);
		} 
		
		return user;
	}

	@Override
	protected void initComponent() {
		// TODO Auto-generated method stub
//		redisTemplate = (StringRedisTemplate) SpringContextUtil.getBean("redisTemplate");
		
	}
}
