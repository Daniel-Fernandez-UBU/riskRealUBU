package tfg.daniel.riskreal.riskrealApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.tags.form.PasswordInputTag;

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
        user.setPassword(password);
        user.setCompany(Integer.parseInt(company));
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setGender(gender);
        user.setRol(rol);
        user.setAge(age);
        user.setStatus(true);
        //userRepository.save(user);
        
        System.out.println(user.toString());
        
        String profile;
        
        if (rol.equalsIgnoreCase("manager")) {
        	profile = "ADMIN";
        } else {
        	profile = "CUSTOMER";
        }

        // Crear y guardar la entidad Profile
        Profile userProfile = new Profile();
        userProfile.setUsername(email);
        userProfile.setProfile(profile);
       // profileRepository.save(userProfile);
        System.out.println(userProfile.toString());

        // Redirigir a una página de éxito o hacer otra acción
        return "redirect:/";
    }
}
