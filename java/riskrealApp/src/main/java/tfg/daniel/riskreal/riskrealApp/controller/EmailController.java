package tfg.daniel.riskreal.riskrealApp.controller;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import tfg.daniel.riskreal.riskrealApp.model.EmailRequest;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;
import tfg.daniel.riskreal.riskrealApp.services.EmailService;
import tfg.daniel.riskreal.riskrealApp.model.User;

/**
 * Class EmailController.
 * 
 * Class for the email sender controller.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Controller
public class EmailController {
    
    /** The email service. */
    @Autowired
    private EmailService emailService;
    
    /** The user repository. */
    @Autowired
    private UserRepository userRepository; 
    
    /** The password encoder. */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /** The message source. */
    @Autowired
    private MessageSource messageSource;

    
    /**
     * Send email view.
     *
     * @param to the to
     * @param subject the subject
     * @param body the body
     * @param session the session
     * @param locale the locale
     * @return the string
     */
    @PostMapping("/send-email")
    private String sendEmailView(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("body") String body, 
    		HttpSession session, Locale locale) {
        
        try {

            Optional<User> optUser = userRepository.findById(to);            
                        
            // Generate a random password
            String passRandom = RandomStringUtils.random(10, 65, 122, true, true, null, new SecureRandom());
            
            body = body + ":\n" + passRandom;
            
            EmailRequest email = new EmailRequest();
            email.setBody(body);
            email.setSubject(subject);
            email.setTo(to);    
            
            if (userRepository.existsById(to)) {
            	// Get the user object
            	User user = optUser.get();
            	user.setPassword(passwordEncoder.encode(passRandom));
            	user.setStatus(2);
            	userRepository.save(user);
                emailService.sendMail(email);
                session.setAttribute("emailMessage", messageSource.getMessage("email.success", null, locale));
                session.setAttribute("type", "success");
            } else {
            	session.setAttribute("emailMessage", messageSource.getMessage("user.not.registered", null, locale));
            	session.setAttribute("type", "info");
            	 
            }
        } catch (Exception e) {
        	session.setAttribute("emailMessage", messageSource.getMessage("email.error", null, locale));
        	session.setAttribute("type", "danger");
        }
        
        return "redirect:/";
        
   }

    
}
