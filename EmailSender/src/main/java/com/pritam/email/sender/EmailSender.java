package com.pritam.email.sender;

import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailSender {

	@Autowired
	JavaMailSender mailSender;

	private final String UTF_8 = "UTF-8";

	public void sendMimeMessage(InternetAddress[] to, InternetAddress from, String subject, String body, 
								boolean isBodyHtml, @Nullable File[] attachments, @Nullable Map<String,File> imgs) throws MessagingException{
		log.info("Sending Mime message...");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, UTF_8);

		messageHelper.setTo(to);
		messageHelper.setFrom(from);
        messageHelper.setSubject(subject);
        messageHelper.setText(body, isBodyHtml);

		if (attachments != null && attachments.length != 0){
			for (File attachment : attachments) {
				messageHelper.addAttachment(attachment.getName(), attachment);
			}
		}
		if (imgs != null && !imgs.isEmpty() ){

			for (Map.Entry<String, File> entry : imgs.entrySet()) {
				File img = entry.getValue();
				String contentID = entry.getKey();
				if (img.exists() ){
					messageHelper.addInline(contentID, img);
				}
				
			}
			
		}

        mailSender.send(message);
		log.info("Mime message send successfully!");

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
			message.setCc(cc);
		}
		mailSender.send(message);
		log.info("Simple Message Sent");
		log.debug("Message Details:\n{}", message.toString() );


	}



}
