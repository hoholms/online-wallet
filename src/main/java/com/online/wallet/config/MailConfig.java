package com.online.wallet.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);
  @Value("${spring.mail.host}")
  private              String host;
  @Value("${spring.mail.username}")
  private              String username;
  @Value("${spring.mail.password}")
  private              String password;
  @Value("${spring.mail.port}")
  private              int    port;
  @Value("${spring.mail.protocol}")
  private              String protocol;
  @Value("${mail.debug}")
  private              String debug;

  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties properties = mailSender.getJavaMailProperties();

    properties.setProperty("mail.transport.protocol", protocol);
    properties.setProperty("mail.debug", debug);

    logger.info("MailSender configured");

    return mailSender;
  }

}
