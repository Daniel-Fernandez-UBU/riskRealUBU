
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;
import tfg.daniel.riskreal.riskrealApp.services.JsonService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;


/**
 * Class HomeController.
 *  
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
@Controller
public class HomeController {
	
	/** The user repository. */
	@Autowired
    private UserRepository userRepository; 
	
	/** Injected JsonService class */
	@Autowired
	private JsonService jsonService;
	
	/** The custom config. */
	@Autowired
	private CustomConfig customConfig;
	
	/** The json path lang. */
	private String jsonPathLang;;
	
	@PostConstruct
	public void init() {
	    this.jsonPathLang = customConfig.getQuizFilePath();
	}

		
    /**
     * Home.
     *
     * @param model the model
     * @param session the session
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @GetMapping("/")
    public String home(Model model,  HttpSession session) {
    	File file = new File(jsonPathLang);
        List<String> jsonFiles = new ArrayList<>();
        
        // We control that the path exists and that there are json files on it
        if (file.exists()) {
            // We control that the path exists and that there are json files on it
            jsonFiles = jsonService.getJsonFiles(file);

            if (!jsonFiles.isEmpty()) {
                model.addAttribute("jsonFiles", jsonFiles);
            }
        }
        
        // If the user have to change the password
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (changePasswordRequired(authentication)) {
        	model.addAttribute("email", authentication.getName());
        	return "changePassword";
        }
        
        System.out.println(checkAuthenticated(authentication));
        
        model.addAttribute("isAuthenticated", checkAuthenticated(authentication));
       
        return "home";
    }
    
    @PostMapping("/descriptionQuiz")
    public String description(Model model) {
    	model.addAttribute("lang", "es");
        return "/quiz/description";
    }
    
    @GetMapping("/dynamic/{filename}")
    public ResponseEntity<byte[]> serveAsset(@PathVariable String filename) {
        try {
            Resource resource = new ClassPathResource("static/" + filename);
            if (resource.exists()) {
                byte[] data = resource.getInputStream().readAllBytes();
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(data);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
        
    
    /**
     * Method changePasswordRequired().
     *
     * @param authentication the authentication
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
    
    /**
     * Method checkAuthenticated.
     * 
     * @param authentication
     * @return
     */
    private boolean checkAuthenticated(Authentication authentication) {
        if (authentication != null) {
            Optional<User> userOpt = userRepository.findById(authentication.getName());
            if (userOpt.isPresent()) {
            	return true;
            }
        }
        return false;
    }
	
}


