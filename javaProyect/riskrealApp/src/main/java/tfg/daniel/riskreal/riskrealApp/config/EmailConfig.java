package tfg.daniel.riskreal.riskrealApp.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {
	
	@Value("${email.username}")
	private String email;
	
	@Value("${email.password}")
	private String password;
	
	private Properties getMailProperties() {
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		return properties;
	}
	
	@Bean
	JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setJavaMailProperties(getMailProperties());
		mailSender.setUsername(this.email);
		mailSender.setPassword(this.password);
				
		return mailSender;
	}
	
	@Bean
	ResourceLoader resourceLoader() {
		return new DefaultResourceLoader();
	}
}
