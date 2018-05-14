package com.xinyijia.backend;

import com.xinyijia.backend.domain.UserInfo;
import com.xinyijia.backend.mapper.UserInfoMapper;
import com.xinyijia.backend.utils.MailSenderInfo;
import com.xinyijia.backend.utils.MailUtils;
import com.xinyijia.backend.utils.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XinyijiaBackendApplicationTests {
@Autowired
	UserInfoMapper userInfoMapper;
	@Test
	public void contextLoads() {


	}

	@Test
	public void testMail(){
		MailSenderInfo mailSenderInfo = new MailSenderInfo();
		mailSenderInfo.setSubject("sdasd");
		mailSenderInfo.setToAddress(new String[]{"378097217@qq.com"});
		mailSenderInfo.setContent("dasd");
		MailUtils.sendHtmlMail(mailSenderInfo);
	}

	@Test
	public void insert(){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("tanjia2");
		userInfo.setPassword("22");
		userInfo.setEmail("ddd");
		System.out.println(userInfoMapper.insert(userInfo));
	}

	@Test
	public void testToken(){
		RandomUtil.getGlobalToken();

	}

}
