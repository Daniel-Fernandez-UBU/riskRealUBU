
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;

import java.util.ArrayList;

@Controller
public class HomeController {
	
	  
	
    @GetMapping("/")
    public String home(Model model,  HttpServletRequest request, HttpSession session) throws IOException {
        // Obtener la lista de archivos JSON en la carpeta ClassPathResource
        ClassPathResource resource = new ClassPathResource("");
        File file = resource.getFile();
        File[] files = file.listFiles();
        List<String> jsonFiles = new ArrayList<>();
        for (File jsonFile : files) {
            if (jsonFile.isFile() && jsonFile.getName().endsWith(".json")) {
                jsonFiles.add(jsonFile.getName());
            }
        }

        // Create a new session for the user
        session.invalidate();
        session = request.getSession(true);
        UserSelection userSelection = new UserSelection();
        userSelection.setAnswer(2, "No"); // Just for test
        // We charge the attribute to the session
        session.setAttribute("userSelection", userSelection);
        
        model.addAttribute("jsonFiles", jsonFiles);
        return "home";
    }
	
}


