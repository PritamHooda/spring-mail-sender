package com.pritam.email.sender;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${email.sender.long.body.char.count}")
	private int longBodyCharCount;

	private static final String TEST_BODY 			= "Test Text - This is a test message body";
	private static final String TEST_SUBJECT 		= "Test subject - IGNORE this is a test mail";
	private static final String TEST_TO_ADDRESS 	= "reciever@example.com";
	private static final String TEST_FROM_ADDRESS 	= "sender@exmaple.com";
	private static final String[] TEST_CC 			= {"cc.email@exmaple.com","cc2.email@examile.com"};
	private static final String[] TEST_BCC 			= {"bcc.email@exmaple.com"};

	@BeforeEach
	void setup(){
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.start();
		checkBeforeMessages();
	}

	@AfterEach
	void tearDown(){
		greenMail.stop();
	}

	@Test
	public void testSimpleMessage() throws MessagingException, IOException{
		log.info("Sending test simple message...");
				
		mailSender.sendSimpleTextMessage(TEST_TO_ADDRESS, TEST_FROM_ADDRESS,
		 TEST_SUBJECT, TEST_BODY, TEST_CC, TEST_BCC);
		
		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		log.debug("greenmail message length: " + receivedMessages.length);

		// NOTE: The reason why recieved message will be 4 
		// ... is because we are sending one message to TEST_TO_ADDRESS, two messages to TEST_CC 
		// ... and one message to TEST_BCC
		assertThat(receivedMessages).hasSize(4);
		MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getSubject()).isEqualTo(TEST_SUBJECT);

        assertThat(receivedMessage.getContent().toString()).isEqualTo(TEST_BODY);
		for (MimeMessage message : receivedMessages) {
			log.debug("message recipient: " + Arrays.toString(
				message.getRecipients(RecipientType.TO) ) );
		}
		//assert recipient address
		assertThat(receivedMessage.getRecipients(RecipientType.TO)[0].toString() ).isEqualTo(TEST_TO_ADDRESS);
		//assert cc address
		assertThat(Arrays.toString( 
			receivedMessage.getRecipients(RecipientType.CC)
			) ).isEqualTo(Arrays.toString(TEST_CC));

		// Verify BCC recipient received the message
		assertThat(greenMail.getReceivedMessagesForDomain("bcc.email@exmaple.com")).hasSize(1);
	}

	@Test
	public void testEmptyCC_And_BCC() throws MessagingException, IOException{
		log.info("testing email with empty CC and BCC");
		
		mailSender.sendSimpleTextMessage(TEST_TO_ADDRESS, TEST_FROM_ADDRESS,
			TEST_SUBJECT, TEST_BODY, null, null);

		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		log.debug("greenmail message length: " + receivedMessages.length);

		// NOTE: One message excepted because we are not sending any CC or BCC
		assertThat(receivedMessages).hasSize(1);
		
		MimeMessage receivedMessage = receivedMessages[0];
		assertThat(receivedMessage.getSubject()).isEqualTo(TEST_SUBJECT);
		assertThat(receivedMessage.getContent().toString()).contains(TEST_BODY);
		
		//assert recipient address
		assertThat(receivedMessage.getRecipients(RecipientType.TO)[0].toString()).isEqualTo(TEST_TO_ADDRESS);
		
		//assert CC and BCC are empty
		assertThat(receivedMessage.getRecipients(RecipientType.CC)).isNull();
		assertThat(receivedMessage.getRecipients(RecipientType.BCC)).isNull();
	}
	
	
	/**
	 * This test case is to test the email sender with a long message body.
	 * The message body is appended with the character "A" for an ammount that is defined in the properties file.
	 * @throws MessagingException
	 * @throws IOException
	 */
	@Test
	public void testLongMessageBody() throws MessagingException, IOException{
		log.info("testing email with long message body");
		String longBody = "This is a long message body that is longer than the max length of the message body \n";
		longBody += "A".repeat(longBodyCharCount);
		log.debug("long body: " + longBody);

		mailSender.sendSimpleTextMessage(TEST_TO_ADDRESS, TEST_FROM_ADDRESS,
			TEST_SUBJECT, longBody, null, null);

		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		log.debug("greenmail message length: " + receivedMessages.length);

		assertThat(receivedMessages).hasSize(1);
		MimeMessage receivedMessage = receivedMessages[0];

		log.debug("received message: " + receivedMessage.getContent().toString());
        assertThat(receivedMessage.getContent().toString()).isEqualTo(longBody);
		
	}

	// TODO: test for attachments

	// TODO: test for special characters in the message body

	// TODO: test multiple TO addresses

	// TODO: test for MIME message

	// negetive test cases
	// TODO: test for invalid email addresses

	// TODO: test for empty email addresses

	// TODO: test for null email addresses

	// permormance test cases
	
	
	private void checkBeforeMessages(){
		// messages in greenmail before sending
		log.debug("Checking before messages...");
		MimeMessage[] beforeMessages = greenMail.getReceivedMessages();
		log.debug(beforeMessages.length);
	}

	@SpringBootApplication
	static class TestConfiguration{

	}

}
