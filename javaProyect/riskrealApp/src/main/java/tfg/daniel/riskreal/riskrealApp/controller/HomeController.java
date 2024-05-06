
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.services.PropertiesService;


import java.util.ArrayList;

@Controller
public class HomeController {
	
	
	@Autowired
	private PropertiesService propertiesService;
	

		
    @GetMapping("/")
    public String home(Model model,  HttpSession session) throws IOException {
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
        //session.invalidate();
        
        model.addAttribute("jsonFiles", jsonFiles);
        return "home";
    }
    

	
}


