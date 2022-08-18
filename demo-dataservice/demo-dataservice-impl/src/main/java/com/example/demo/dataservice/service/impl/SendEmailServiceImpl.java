package com.example.demo.dataservice.service.impl;

import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.example.demo.dataservice.service.ISendService;

/**
 * 注意配置文件后缀空格问题
 * @author Administrator
 *
 */
@Component
public class SendEmailServiceImpl implements ISendService, EnvironmentAware {
	
	private Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);

	private Environment env;

//	@PostConstruct
	private void init() {
		send();
	}

	@Override
	public boolean send() {
		return send("hello email!!!");
	}

	private boolean send(String context) {
		try {
			Properties props = new Properties();
			// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
			String emailHost = env.getProperty("email.host");
			props.put("mail.smtp.host", emailHost);
			// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
			props.put("mail.smtp.auth", "true");
			// 用props对象构建一个session
			Session session = Session.getDefaultInstance(props);
			session.setDebug(true);
			// 用session为参数定义消息对象
			MimeMessage message = new MimeMessage(session);

			// 发件人地址
			String emailFrom = env.getProperty("email.from");
			message.setFrom(new InternetAddress(emailFrom));

			// 收件人地址
			String[] eamilTo = env.getProperty("email.to").split(",");
			InternetAddress[] sendTo = new InternetAddress[eamilTo.length];
			for (int i = 0; i < eamilTo.length; i++) {
				sendTo[i] = new InternetAddress(eamilTo[i]);
			}
			message.addRecipients(Message.RecipientType.TO, sendTo);
			// 设置在发送给收信人之前给自己（发送方）抄送一份，不然会被当成垃圾邮件，报554错
			message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(emailFrom));

			// 设置标题
			String emailSubject = env.getProperty("email.subject");
			message.setSubject(emailSubject);// 加载标题

			// 设置文本内容和附件,向multipart对象中添加邮件的各个部分内容，
			Multipart multipart = new MimeMultipart();
			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(context);
			multipart.addBodyPart(contentPart);

			// 添加附件
			String emailAffix = env.getProperty("email.affix");
			if (!StringUtils.isBlank(emailAffix)) {
				BodyPart messageBodyPart = new MimeBodyPart();

				emailAffix = emailAffix.substring("classpath:".length());
				URL url = Thread.currentThread().getContextClassLoader().getResource(emailAffix);
				DataSource source = new FileDataSource(url.getFile());
				// 添加附件的内容
				DataHandler dh = new DataHandler(source);
				messageBodyPart.setDataHandler(dh);
				// 添加附件的标题
				messageBodyPart.setFileName(dh.getName());
				multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart);// 将multipart对象放到message中
			message.saveChanges(); // 保存邮件

			String username = env.getProperty("email.username");
			String password = env.getProperty("email.password");
			Transport transport = session.getTransport("smtp");// 发送邮件
			transport.connect(emailHost, username, password);// 连接服务器的邮箱
			transport.sendMessage(message, message.getAllRecipients());// 把邮件发送出去
			transport.close();// 关闭连接

			return true;
		} catch (Exception e) {
			logger.error("",e);
			return false;
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

}
