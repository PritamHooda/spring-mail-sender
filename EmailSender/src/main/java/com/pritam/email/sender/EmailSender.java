package com.pritam.email.sender;

import jakarta.annotation.Nullable;
import jakarta.mail.internet.InternetAddress;
import lombok.extern.log4j.Log4j2;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailSender {

	@Autowired
	JavaMailSender mailSender;

	private final String UTF_8 = "UTF-8";

	public void sendMimeMessage(InternetAddress[] to, InternetAddress from, String subject, String body, 
								boolean isBodyHtml, @Nullable File[] attachments, @Nullable File[] imgs){
		//TODO
	}

	public void sendSimpleTextMessage(String to, String from, String subject, String body,
										@Nullable String[] cc, @Nullable String[] bcc){
		log.info("Sending Simple Text Message");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(from);
		message.setSubject(subject);
		message.setText(body);
		if (bcc != null){
			message.setBcc(bcc);
		}
		if (cc != null) {
			message.setBcc(cc);
		}
		mailSender.send(message);
		log.info("Simple Message Sent");
		log.debug("Message Details:\n{}", message.toString() );


	}



}
