package org.isbel8ai.training.clinic.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);

    void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException;
}
