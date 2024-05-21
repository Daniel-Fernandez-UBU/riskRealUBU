package tfg.daniel.riskreal.riskrealApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tfg.daniel.riskreal.riskrealApp.model.Profile;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.repository.ProfileRepository;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/done")
    public String registrarUsuario(
            @RequestParam String password,
            @RequestParam String company,
            @RequestParam String rol,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String gender,
            @RequestParam String age) {

        // Crear y guardar la entidad User
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setCompany(Integer.parseInt(company));
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setGender(gender);
        user.setRol(rol);
        user.setAge(age);
        user.setStatus(1);
        userRepository.save(user);
        
        System.out.println(user.toString());
        
        String profile;
        
        if (rol.equalsIgnoreCase("manager")) {
        	profile = "ADMIN";
        } else if (rol.equalsIgnoreCase("employee")) {
        	profile = "CUSTOMER";
        } else {
        	profile="GUEST";
        }

        // Crear y guardar la entidad Profile
        Profile userProfile = new Profile();
        userProfile.setUsername(email);
        userProfile.setProfile(profile);
        profileRepository.save(userProfile);
        System.out.println(userProfile.toString());

        // Redirigir a una página de éxito o hacer otra acción
        return "redirect:/";
    }
    
    @PostMapping("/register/done2")
    public String saveUserProfile(@ModelAttribute("user") User user) {

        // Encode the plaintext password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set status active by default
        user.setStatus(1);
        //userRepository.save(user);
        
        System.out.println(user.toString());
        
        String profile;
        
        if (user.getRol().equalsIgnoreCase("manager")) {
        	profile = "ADMIN";
        } else if (user.getRol().equalsIgnoreCase("employee")) {
        	profile = "CUSTOMER";
        } else {
        	profile="GUEST";
        }

        // Create a save profile
        Profile userProfile = new Profile();
        userProfile.setUsername(user.getEmail());
        userProfile.setProfile(profile);
        //profileRepository.save(userProfile);
        System.out.println(userProfile.toString());

        // Return to home page for login
        return "redirect:/";
    }
    
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
}
