package com.online.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailSender {

  private static final Logger         logger = LoggerFactory.getLogger(MailSender.class);
  private final        JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private              String         username;

  public void send(String emailTo, String subject, String message) {
    logger.info("Preparing to send email to: {}", emailTo);
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    try {
      mailMessage.setFrom(username);
      mailMessage.setTo(emailTo);
      mailMessage.setSubject(subject);
      mailMessage.setText(message);

      javaMailSender.send(mailMessage);
      logger.info("Email successfully sent to: {}", emailTo);
    } catch (Exception e) {
      logger.error("Failed to send email to: {} with subject: {}", emailTo, subject, e);
    }
  }

}
