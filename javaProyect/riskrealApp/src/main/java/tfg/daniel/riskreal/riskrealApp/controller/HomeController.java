
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;
import tfg.daniel.riskreal.riskrealApp.services.JsonService;



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
    	
    	// To store filename and quiz object
    	HashMap<String, Quiz> jsonQuiz = new HashMap<>();
    	String currentLang = "";
    	File file = new File(jsonPathLang);
        List<String> jsonFiles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // To check if have selected language or not
        if (session.getAttribute("session.current.locale") == null) {
        	currentLang = Locale.getDefault().toString().split("_")[0];
        } else {
        	currentLang = session.getAttribute("session.current.locale").toString();
        }

        Quiz quiz = null;
        
        // We control that the path exists and that there are json files on it
        if (file.exists() && checkAuthenticated(authentication)) {
            jsonFiles = jsonService.getJsonFiles(file);
            if (!jsonFiles.isEmpty()) {
            	for (String jsonQ : jsonFiles) {
            		quiz = jsonService.getJsonQuiz(jsonPathLang + "/" + jsonQ);
                	jsonQuiz.put(jsonQ, quiz);
            	}
                session.setAttribute("jsonQuiz", jsonQuiz);
            }
        } else {
        	String testJson = customConfig.getTestQuiz() + currentLang + ".json";
        	jsonFiles.add(testJson);
        	File testFile = new File(jsonPathLang + "/" + testJson);
        	if (testFile.exists()) {
        		quiz = jsonService.getJsonQuiz(jsonPathLang + "/" + testJson);
            	jsonQuiz.put(testJson, quiz);
        	}  
            session.setAttribute("jsonQuiz", jsonQuiz);

        }
        
        // If the user have to change the password
        if (changePasswordRequired(authentication)) {
        	model.addAttribute("email", authentication.getName());
        	return "/users/changePassword";
        }
        

        model.addAttribute("isAuthenticated", checkAuthenticated(authentication));
        model.addAttribute("jsonFiles", jsonFiles);
        model.addAttribute("jsonQuiz", jsonQuiz);
       
        return "home";
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


