package tfg.daniel.riskreal.riskrealApp.services;

import jakarta.mail.MessagingException;
import tfg.daniel.riskreal.riskrealApp.model.EmailRequest;


public interface IEmailService {
	
	public void sendMail (EmailRequest email) throws MessagingException;
	

}
