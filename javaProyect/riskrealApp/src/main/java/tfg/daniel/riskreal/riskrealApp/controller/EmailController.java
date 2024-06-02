package tfg.daniel.riskreal.riskrealApp.controller;

import java.security.SecureRandom;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import tfg.daniel.riskreal.riskrealApp.model.EmailRequest;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;
import tfg.daniel.riskreal.riskrealApp.services.EmailService;
import tfg.daniel.riskreal.riskrealApp.model.User;

@Controller
public class EmailController {
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserRepository userRepository; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @PostMapping("/send-email")
    private String sendEmailView(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("body") String body, 
    		HttpSession session) {
        
        try {

            Optional<User> optUser = userRepository.findById(to);
            System.out.println(optUser.toString());
            
                        
            // Generate a random password
            String passRandom = RandomStringUtils.random(10, 65, 122, true, true, null, new SecureRandom());
            System.out.println("RandomPassword es: " + passRandom);
            
            body = body + ":\n" + passRandom;
            
            EmailRequest email = new EmailRequest();
            email.setBody(body);
            email.setSubject(subject);
            email.setTo(to);
            
            if (optUser.isPresent()) {
            	// Get the user object
            	User user = optUser.get();
            	user.setPassword(passwordEncoder.encode(passRandom));
            	user.setStatus(2);
            	userRepository.save(user);
                emailService.sendMail(email);
                session.setAttribute("emailMessage", "Correo electrónico enviado correctamente.");
            } else {
            	session.setAttribute("emailMessage", "El usuario no está registrado en el sistema.");
            	 
            }
        } catch (Exception e) {
        	session.setAttribute("emailMessage", "Error al enviar el correo electrónico.");
        	return "redirect:/"; 
        }
        
        return "redirect:/";
        
   }

    
}
