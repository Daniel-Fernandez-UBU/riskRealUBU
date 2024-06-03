package tfg.daniel.riskreal.riskrealApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tfg.daniel.riskreal.riskrealApp.model.Profile;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.repository.ProfileRepository;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;

/**
 * Class RegisterController.
 * 
 * Controller that have all the methods related to the registration in the app.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 */
@Controller
public class RegisterController {

    /** The user repository. */
    @Autowired
    private UserRepository userRepository;

    /** The profile repository. */
    @Autowired
    private ProfileRepository profileRepository;
    
    /** The password encoder. */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Register.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/register")
    public String register(Model model) {
    	
    	User user = new User();
    	
    	model.addAttribute("user", user);
    	
        return "register";
    }
    
    /**
     * Save user profile.
     *
     * @param user the user
     * @return the string
     */
    @PostMapping("/register/done")
    public String saveUserProfile(@ModelAttribute("user") User user) {

        // Encode the plain text password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set status active by default
        user.setStatus(1);
        // Save the user in the db.
        userRepository.save(user);
                
        String profile;
        
        if (user.getRol().equalsIgnoreCase("manager")) {
        	profile = "ADMIN";
        } else if (user.getRol().equalsIgnoreCase("employee")) {
        	profile = "CUSTOMER";
        } else {
        	profile="GUEST";
        }

        // Save profile in the db.
        Profile userProfile = new Profile();
        userProfile.setUsername(user.getEmail());
        userProfile.setProfile(profile);
        profileRepository.save(userProfile);
        System.out.println(userProfile.toString());

        // Return to login page
        return "redirect:/login";
    }
    
    /**
     * Update user profile.
     *
     * @param user the user
     * @return the string
     */
    @PostMapping("/update/done")
    public String updateUserProfile(@ModelAttribute("user") User user) {
    	
    	    	
    	User currentUser = null;
        // Encode the plain text password
    	
    	if (user.getPassword().isBlank()) {
        	
            Optional<User> userOpt = userRepository.findById(user.getEmail());
            if (userOpt.isPresent()) {
            	currentUser = userOpt.get();
            } 
            
            user.setPassword(currentUser.getPassword());
            
    	} else {
    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    	}
    	
        // Set status active by default
        user.setStatus(1);
        // Update the user in the db.
        userRepository.save(user);
                
        String profile;
        
        if (user.getRol().equalsIgnoreCase("manager")) {
        	profile = "ADMIN";
        } else if (user.getRol().equalsIgnoreCase("employee")) {
        	profile = "CUSTOMER";
        } else {
        	profile="GUEST";
        }

        // Update profile in the db.
        Profile userProfile = new Profile();
        userProfile.setUsername(user.getEmail());
        userProfile.setProfile(profile);
        profileRepository.save(userProfile);

        // Return to login page
        return "redirect:/user/profile";
    }
    
    /**
     * Change password.
     *
     * @param passwordNew the password new
     * @param email the email
     * @return the string
     */
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("passwordNew") String passwordNew, @RequestParam("email") String email) {
    	
    	Optional<User> userOpt = userRepository.findById(email);
    	
    	if (userOpt.isPresent()) {
    		User user = userOpt.get();
    		user.setPassword(passwordEncoder.encode(passwordNew));
    		user.setStatus(1);
    		userRepository.save(user);
    	}
    	
    	return "redirect:/";
    	
    }
    /**
     * Method userProfile.
     * 
     * @param model
     * 
     * @return profile view
     */
    @GetMapping("/user/profile")
    public String userProfile(Model model) {
    	User user = null;
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOpt = userRepository.findById(authentication.getName());
        if (userOpt.isPresent()) {
        	user = userOpt.get();
        } 
        
        model.addAttribute("user", user);        
                
    	return "profile";
    }
}
