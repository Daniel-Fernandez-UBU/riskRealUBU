
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;


@Controller
@PropertySource("classpath:custom.properties")
public class HomeController {
	
	@Autowired
    private UserRepository userRepository; 
	
	
	@Value("${json.quiz.file.path.lang}")
	private String jsonPathLang;

		
    @GetMapping("/")
    public String home(Model model,  HttpSession session) throws IOException {
        // Obtener la lista de archivos JSON en la carpeta ClassPathResource
        //ClassPathResource resource = new ClassPathResource("");
        //File file = resource.getFile();

                        
        // Create a new session for the user
        //session.invalidate();
        /**
        if (session.getAttribute("emailMessage") != null) {
            model.addAttribute("emailMessage", session.getAttribute("emailMessage"));
            // Eliminar el mensaje de la sesión después de agregarlo al modelo
            session.removeAttribute("emailMessage");
        }*/
    	
    	File file = new File(jsonPathLang);
        File[] files = file.listFiles();
        List<String> jsonFiles = new ArrayList<>();
        for (File jsonFile : files) {
            if (jsonFile.isFile() && jsonFile.getName().endsWith(".json")) {
                jsonFiles.add(jsonFile.getName());
            }
        }
		
        model.addAttribute("jsonFiles", jsonFiles);
        
        // If the user have to change the password
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (changePasswordRequired(authentication)) {
        	model.addAttribute("email", authentication.getName());
        	return "changePassword";
        }
       
        return "home";
    }
    
    
    /**
     * Method changePasswordRequired().
     * @param authentication
     * @return true o false
     */
    private boolean changePasswordRequired(Authentication authentication) {
        if (authentication != null) {
            Optional<User> userOpt = userRepository.findById(authentication.getName());
            if (userOpt.isPresent()) {
            	User user = userOpt.get();
            	if (user.getStatus() == 2) {
            		return true;
            	}
            }
        }
        return false;
    }
	
}


