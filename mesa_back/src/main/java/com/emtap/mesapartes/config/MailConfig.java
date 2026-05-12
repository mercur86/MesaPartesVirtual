package com.emtap.mesapartes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuración de dos JavaMailSender para dos cuentas Outlook corporativas.
 *
 * tramiteMailSender → tramite.documentario@epsgrau.com.pe (envía notificación a
 * mesa)
 * mesapartesMailSender → mesadepartesvirtual@epsgrau.com.pe (envía respuesta al
 * ciudadano)
 */
@Configuration
public class MailConfig {

    @Value("${mail.tramite.username}")
    private String tramiteUsername;

    @Value("${mail.tramite.password}")
    private String tramitePassword;

    @Value("${mail.mesapartes.username}")
    private String mesapartesUsername;

    @Value("${mail.mesapartes.password}")
    private String mesapartesPassword;

    @Bean(name = "tramiteMailSender")
    public JavaMailSender tramiteMailSender() {
        return buildSender(tramiteUsername, tramitePassword);
    }

    @Bean(name = "mesapartesMailSender")
    public JavaMailSender mesapartesMailSender() {
        return buildSender(mesapartesUsername, mesapartesPassword);
    }

    private JavaMailSender buildSender(String username, String password) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setDefaultEncoding("UTF-8");

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");
        props.put("mail.debug", "false");

        return sender;
    }
}
