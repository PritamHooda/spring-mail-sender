package com.pritam.email.sender;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import jakarta.mail.MessagingException;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;

@SpringBootTest
@Log4j2
class EmailSenderTests {
	@Autowired
	EmailSender mailSender;

	private GreenMail greenMail;

	@BeforeEach
	void setup(){
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.start();
	}

	@AfterEach
	void tearDown(){
		greenMail.stop();
	}

	@Test
	public void testSimpleMessage() throws MessagingException, IOException{
		log.info("Sending test simple message...");
		String body = "Test Text";
		String subject = "Test subject";
		String toAddress = "reciever@example.com";
		String fromAddress = "sender@exmaple.com";
		String[] cc = {"cc.email@exmaple.com","cc2.email@examile.com"};
		String[] bcc = {"bcc.email@exmaple.com"};

		// messages in greenmail before sending
		log.debug("Checking before messages...");
		MimeMessage[] beforeMessages = greenMail.getReceivedMessages();
		log.debug(beforeMessages.length);

		mailSender.sendSimpleTextMessage(toAddress, fromAddress,
		 subject, body, cc, bcc);
		
		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		log.debug("greenmail message length: " + receivedMessages.length);

		// Expect one recieved email for each address that we are sending to: (TO, CC, BCC)
		assertThat(receivedMessages).hasSize(4);
		MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getSubject()).isEqualTo(subject);
        assertThat(receivedMessage.getContent().toString()).contains(body);
		//assert recipient address
		assertThat(receivedMessage.getRecipients(RecipientType.TO)[0].toString() ).isEqualTo(toAddress);
		//assert cc address
		assertThat(Arrays.toString( 
			receivedMessage.getRecipients(RecipientType.CC)
			) ).isEqualTo(Arrays.toString(cc));
	
	}

	@SpringBootApplication
	static class TestConfiguration{

	}

}
