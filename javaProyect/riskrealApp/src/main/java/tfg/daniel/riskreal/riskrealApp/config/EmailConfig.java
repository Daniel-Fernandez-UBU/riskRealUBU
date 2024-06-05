package tfg.daniel.riskreal.riskrealApp.config;

/**
 * Class EmailConfig.
 * 
 * This class config the email properties and define some methods for send emails.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {
	
	/** String email properties from email.properties file.  */
	@Value("${email.username}")
	private String email;
	
	/** String password properties from email.properties file.  */
	@Value("${email.password}")
	private String password;
	
	/** String auth properties from email.properties file.  */
	@Value("${email.auth}")
	private String auth;
	
	/** String tls properties from email.properties file.  */
	@Value("${email.tls.enable}")
	private String tls;
	
	/** String host properties from email.properties file.  */
	@Value("${email.host}")
	private String host;
	
	/** String port properties from email.properties file.  */
	@Value("${email.port}")
	private String port;
	
	/**
	 * Method getMailProperties().
	 * 
	 * @return properties with the smpt server config.
	 */
	private Properties getMailProperties() {
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", tls);
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		
		return properties;
	}
	
	/**
	 * Method javaMailSender.
	 * 
	 * Define a JavaMailSender object with the smpt server properties, username and password.
	 * 
	 * @return mailSender object.
	 */
	@Bean
	JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setJavaMailProperties(getMailProperties());
		mailSender.setUsername(this.email);
		mailSender.setPassword(this.password);
				
		return mailSender;
	}	
}
