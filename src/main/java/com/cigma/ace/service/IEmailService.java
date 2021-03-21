package com.cigma.ace.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IEmailService {
    void sendSimpleMail(String to, String subject, String body);
    void sendHtmlMail(String to, String subject, String body) throws MessagingException, IOException;
}
