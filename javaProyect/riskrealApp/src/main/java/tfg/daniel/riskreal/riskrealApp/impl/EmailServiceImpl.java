package tfg.daniel.riskreal.riskrealApp.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import tfg.daniel.riskreal.riskrealApp.model.EmailRequest;
import tfg.daniel.riskreal.riskrealApp.services.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {
	
	private final JavaMailSender javaMailSender;	
	private final TemplateEngine templateEngine;
	
	public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}
	
	// Metodo para enviar correo.
	@Override
	public void sendMail(EmailRequest email) throws MessagingException {
		try {
			MimeMessage message = this.javaMailSender.createMimeMessage();
			MimeMessageHelper helper =
					new MimeMessageHelper(message, 
							true, "UTF-8");
			// Propiedades desitinatario y asunto
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			
			// helper.setText(email.getBody()); // Texto plano
			
			Context context = new Context(); 	
			context.setVariable("message", email.getBody());
			// Nombre de la plantilla email.html
			String contentHTML = templateEngine.process("email", context);
			
			// Enviamos las propiedades y añadimos la plantilla dle email
			helper.setText(contentHTML, true); //True por lo del html
			
			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Error al enviar email: " + e.getMessage(), e);
		}
	}
	
}
