package tfg.daniel.riskreal.riskrealApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
    @GetMapping("/loginError")
    public String loginError() {
        return "loginError";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(@RequestParam(name = "lang", required = false) String lang, Model model) {
        if (lang != null && !lang.isEmpty()) {
            model.addAttribute("lang", lang);
        } 
        return "logout"; 
    }
    
    @GetMapping("/resetPassword")
    public String resetPassword() {
    	return "/resetPassword";
    }

}
