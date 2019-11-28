package com.yhr.simpleEmail;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication
public class SimpleEmailApplication {

    public static void main(String[] args) {
        /*SMTP server info*/
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "sentFrom";
        String password = "password";

        /*outgoing email info*/
        String mailTo = "receivedBy";
        String subject = "Simple email testing";
        String message = "Hi Sid, This is a testing mail.";

		/*
		Here I used Gmail SMTP server.
		Replace "sentFrom", "password" with relevant sender's info and "receivedBy" with relevant receiver's address
		*/

        SimpleEmailApplication mailer = new SimpleEmailApplication();

        try {
            mailer.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            System.out.println("Email successfully sent to " + mailTo + ".");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
    }

    public void sendPlainTextEmail(String host, String port, final String userName, final String password, String toAddress, String subject, String message) throws AddressException, MessagingException {

        /* setting SMTP server properties*/
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");


        /*creating a new session with an authenticator*/
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        /*creating the new e-mail message*/
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        /*setting the plain text message*/
        msg.setText(message);

        /*sending the e-mail*/
        Transport.send(msg);

    }

}
