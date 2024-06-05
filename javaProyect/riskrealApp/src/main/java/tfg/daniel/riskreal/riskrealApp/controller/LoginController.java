package tfg.daniel.riskreal.riskrealApp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Class LoginController.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
@Controller
public class LoginController {
	
	
    /**
     * Login error.
     *
     * @return the string
     */
    @GetMapping("/loginError")
    public String loginError() {
        return "loginError";
    }
    
    /**
     * Login.
     *
     * @return the string
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * Logout.
     *
     * @param lang the lang
     * @param model the model
     * @return the string
     */
    @GetMapping("/logout")
    public String logout(@RequestParam(name = "lang", required = false) String lang, Model model) {
        if (lang != null && !lang.isEmpty()) {
            model.addAttribute("lang", lang);
        } 
        return "logout"; 
    }
    
    /**
     * Reset password.
     *
     * @return the string
     */
    @GetMapping("/resetPassword")
    public String resetPassword() {
    	return "/resetPassword";
    }

}
