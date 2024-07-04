package com.pritam.email.sender;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmailSenderTests {
	@Autowired
	EmailSender mailSender;

	private GreenMail greenMail;

	@BeforeAll
	void init(){
		greenMail = new GreenMail(ServerSetup.SMTP);
	}

	@BeforeEach
	void setup(){
		greenMail.start();
	}

	@AfterEach
	void tearDown(){
		greenMail.stop();
	}

	@Test
	public void testSimpleMessage(){
		mailSender.sendSimpleTextMessage(null, null, null, null, null, null);;

	}

	@SpringBootApplication
	static class TestConfiguration{

	}

}
