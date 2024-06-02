package tfg.daniel.riskreal.riskrealApp.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import tfg.daniel.riskreal.riskrealApp.model.EmailRequest;

@Service
public class EmailService {
	
	
	private final JavaMailSender javaMailSender;	
	private final TemplateEngine templateEngine;
	
	public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}
	
	/**
	 * Method sendMail.
	 * 
	 * Send a email.
	 * 
	 */
	public void sendMail(EmailRequest email) throws MessagingException {
		try {
			MimeMessage message = this.javaMailSender.createMimeMessage();
			MimeMessageHelper helper =
					new MimeMessageHelper(message, 
							true, "UTF-8");
			// Subject and to properties
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			
			Context context = new Context(); 	
			context.setVariable("body", email.getBody());
			
			// Email template
			String contentHTML = templateEngine.process("email", context);
			
			// We send the properties and the template
			helper.setText(contentHTML, true); //True for using html
			
			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Error al enviar email: " + e.getMessage(), e);
		}
	}
	
}

