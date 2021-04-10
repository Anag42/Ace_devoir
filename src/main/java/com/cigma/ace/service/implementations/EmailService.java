package com.cigma.ace.service.implementations;

import com.cigma.ace.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void sendSimpleMail(String to, String subject, String body){
    	SimpleMailMessage msg = new SimpleMailMessage();
    	 msg.setTo(to);
    	 msg.setSubject(subject);
    	 msg.setText(body);
    	 mailSender.send(msg); 

    }
    

    @Override
    public void sendHtmlMail(String to, String subject, String body) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(message);
    }


	@Override
	public void sendHtmlMailWithAttachment(String to, String subject, String body, String filename,
			ByteArrayResource resource) throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.addAttachment(filename, resource);
        mailSender.send(message);
	}
    
    
}
