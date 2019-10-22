package com.kelly.api.account.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.smtp.SMTPTransport;

public class RecoveryEmail {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// for example, smtp.mailgun.org
    private static final String SMTP_SERVER = "localhost";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private final String FROM = "AccountRecovery@KellyVittitoe.com";

    private final String EMAIL_SUBJECT = "Test Send Email via SMTP (HTML)";
    private final String EMAIL_TEXT = "<h1>Hello Java Mail \n ABC123</h1>";
    
    public RecoveryEmail(String email) {
    	send(email);
    }

    private void send(String email) {
    	Properties prop = System.getProperties();
    	prop.put("mail.smtp.auth", "true");
    	
    	Session session = Session.getInstance( prop, null);
    	Message msg = new MimeMessage(session);
    	
    	try {
			msg.setFrom(new InternetAddress(FROM));
			msg.setRecipients(
					Message.RecipientType.TO, 
					InternetAddress.parse(email, false)
			);
			msg.setSubject(EMAIL_SUBJECT);
			msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));
			
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());
			
            logger.info("Response: " + t.getLastServerResponse());
            logger.info("Recovery email sent to " + email);
            t.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    
    class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}
