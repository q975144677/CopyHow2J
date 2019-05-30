package com.how2j.copy;

import com.how2j.copy.pojo.Item;
import com.how2j.copy.service.*;
import com.how2j.copy.util.EmailSender;
import com.how2j.copy.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CopyApplicationTests {
	@Autowired
	SpringTemplateEngine springTemplateEngine ;
	@Autowired
	NeedToBuyService needToBuyService ;

	@Autowired
	StageService stageService ;

	@Autowired
	ItemService itemService ;
	@Autowired
	StepService stepService ;
	@Autowired
	TitleService titleService ;
	@Autowired
	ContentService contentService ;
	@Autowired
	ReReviewService reReviewService ;
	@Autowired
	ReviewService reviewService ;
	@Autowired
	BuyService buyService ;
	@Value("${username}")
	String postUsername;
	@Value("${password}")
	String postPassword;
	@Value("${host}")
	String postHost;

	@Autowired
	RedisUtil ru ;

@Autowired
UserService userService ;

@Autowired
	EmailSender emailSender ;
@Test
public void setS2pringTemplateEngine(){
emailSender.sendEmail("33","q975144677@163.com");

}
	@Test
	public void uuid(){
		System.out.println(userService.getByEmail("3"));
		System.out.println(userService.getByPhone("51"));
		//System.out.println(RandomUtil.randomUUID());
	}

	@Test
	public void eeee(){
		List<Item> list = itemService.list();
		System.out.println(ru.set("6", list));
Object o = ru.get(6+"");
List<Item> list1 = (List<Item>)o;
		System.out.println(list1);
		Object o2 = ru.get(555+"");
		List<Item> list51 = (List<Item>)o2;
		System.out.println(list51);
		//System.out.println(ru.lGetListSize(6+""));

	}

	@Test
	public void contextLoads() {

		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		String to = "q975144677@163.com";
		sender.setDefaultEncoding("UTF-8");
		String postUsername = "q975144677@163.com";
		String postPassword = "163sqm";
		sender.setUsername(postUsername);
		sender.setPassword(postPassword);
		String postHost = "smtp.163.com";
		sender.setHost(postHost);
		Properties properties = new Properties() ;
		properties.setProperty("mail.smtp.auth","true");
		properties.setProperty("mail.smtp.starttls.enable","true");
		properties.setProperty("spring.mail.properties.mail.smtp.starttls.required","true");
		sender.setJavaMailProperties(properties);

		try {
			StringBuilder sb = new StringBuilder();
			for(int i = 0 ; i < 6 ; i ++){
				sb.append((int)(Math.random()*10));
			}
			MimeMessage mm = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mm, true);
			helper.setFrom(postUsername);
			helper.setTo(to);
			helper.setSubject("How2J仿站验证码");
			//html 转换为 thymeleaf
			Context context = new Context();
			context.setVariable("check", sb.toString());
			String text = springTemplateEngine.process("check", context);
			helper.setText(text, true);
			sender.send(mm);

		}catch (Exception e ){
			e.printStackTrace();

		}
	}

}
