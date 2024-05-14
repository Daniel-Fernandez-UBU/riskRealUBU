package tfg.daniel.riskreal.riskrealApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String logout() {
        return "/logout";
    }
    
    @GetMapping("/resetPassword")
    public String resetPassword() {
    	return "/resetPassword";
    }

}
