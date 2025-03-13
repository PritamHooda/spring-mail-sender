package com.pritam.email.sender.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class TestUtils {
    /**
     * Gets the sender's email address from a MimeMessage by extracting it from the "from" header.
     * This method exists to fix a issue with the current MimeMessage getSender method.
     * That method is based on RFC822 specification which is outdated now. 
     * 
     * @param message The MimeMessage to extract the sender from
     * @return The sender's email address as a String
     * @throws MessagingException if there is an error accessing the message headers
     */
    public static String getMimeSender(MimeMessage message) throws MessagingException {
        String fromHeader = message.getHeader("from")[0];
        char problemChar = fromHeader.charAt(0);

        return fromHeader;
        // return message.getHeader("from")[0].replace('"', '\0');
    
    }
}
