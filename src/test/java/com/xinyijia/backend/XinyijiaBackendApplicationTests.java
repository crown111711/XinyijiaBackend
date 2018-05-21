package com.xinyijia.backend;

import com.xinyijia.backend.domain.UserInfo;
import com.xinyijia.backend.mapper.UserInfoMapper;
import com.xinyijia.backend.utils.HostUtil;
import com.xinyijia.backend.utils.MailSenderInfo;
import com.xinyijia.backend.utils.MailUtils;
import com.xinyijia.backend.utils.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

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
		System.out.println(System.currentTimeMillis());
	}

	@Test
	public void testIp(){
		System.out.println(HostUtil.getIp());
	}

	@Test
	public void getProperty(){
		Properties props = System.getProperties();
		System.out.println(props.getProperty("os.name"));
		System.out.println(System.getProperty("os.name" ));
	}

	@Test
	public void testStringSub() {
		String name = "http://localhost:8090/xyj/api/attachment/showImage/ddf";
		int index = name.lastIndexOf("/");
		String name1 = name.substring(index + 1);
		System.out.println(name1);
	}
}
