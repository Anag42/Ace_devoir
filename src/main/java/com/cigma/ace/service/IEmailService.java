package com.cigma.ace.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.core.io.ByteArrayResource;

public interface IEmailService {
    void sendSimpleMail(String to, String subject, String body);
    void sendHtmlMail(String to, String subject, String body) throws MessagingException, IOException;
    void sendHtmlMailWithAttachment(String to, String subject, String body, String filename, ByteArrayResource resource) throws MessagingException, IOException;
}
