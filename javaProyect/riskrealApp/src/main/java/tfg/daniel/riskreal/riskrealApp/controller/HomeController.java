
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
import org.springframework.web.bind.annotation.PostMapping;
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
    	
    	HashMap<String, Quiz> jsonQuiz = new HashMap<>();
    	String currentLang = "";
    	File file = new File(jsonPathLang);
        List<String> jsonFiles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (session.getAttribute("session.current.locale") == null) {
        	currentLang = Locale.getDefault().toString().split("_")[0];
        } else {
        	currentLang = session.getAttribute("session.current.locale").toString();
        }

        List<Quiz> quizList = new ArrayList<>();
        List<String> imagesList = new ArrayList<>();
        Quiz quiz = null;
        
        // We control that the path exists and that there are json files on it
        if (file.exists() && checkAuthenticated(authentication)) {
            // We control that the path exists and that there are json files on it
            jsonFiles = jsonService.getJsonFiles(file);

            if (!jsonFiles.isEmpty()) {
            	for (String jsonQ : jsonFiles) {
            		quiz = jsonService.getJsonQuiz(jsonPathLang + "/" + jsonQ);
                	for (String image : quiz.getImage()) {
                		imagesList.add(customConfig.getQuizImagePath() + image);
                	}
                	quiz.setImage(imagesList);
                	jsonQuiz.put(jsonQ, quiz);
            	}
                model.addAttribute("jsonFiles", jsonFiles);
                model.addAttribute("jsonQuiz", jsonQuiz);
            }
        } else {
        	String testJson = customConfig.getTestQuiz() + currentLang + ".json";
        	jsonFiles.add(testJson);
        	File testFile = new File(jsonPathLang + "/" + testJson);
        	if (testFile.exists()) {
        		quiz = jsonService.getJsonQuiz(jsonPathLang + "/" + testJson);
            	for (String image : quiz.getImage()) {
            		imagesList.add(customConfig.getQuizImagePath() + image);
            	}
            	quiz.setImage(imagesList);
            	quizList.add(quiz);
            	jsonQuiz.put(testJson, quiz);
            	
            	model.addAttribute("quizList", quizList);
            	model.addAttribute("jsonQuiz", jsonQuiz);
        	}
        	
        	
        	
        }
        
        // If the user have to change the password
        
        if (changePasswordRequired(authentication)) {
        	model.addAttribute("email", authentication.getName());
        	return "changePassword";
        }
        
        
        model.addAttribute("isAuthenticated", checkAuthenticated(authentication));
        
        Quiz responseQuiz = new Quiz();
    	
    	model.addAttribute("responseQuiz", responseQuiz);
       
        return "home";
    }
    
    @PostMapping("/descriptionQuiz")
    public String description() {
        return "/quiz/description";
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


