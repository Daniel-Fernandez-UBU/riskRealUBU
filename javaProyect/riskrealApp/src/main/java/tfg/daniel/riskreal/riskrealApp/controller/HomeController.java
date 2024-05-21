
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


import java.util.ArrayList;

@Controller
@PropertySource("classpath:custom.properties")
public class HomeController {
	
	@Value("${json.quiz.file.path}")
	private String jsonPath;

		
    @GetMapping("/")
    public String home(Model model,  HttpSession session) throws IOException {
        // Obtener la lista de archivos JSON en la carpeta ClassPathResource
        //ClassPathResource resource = new ClassPathResource("");
        //File file = resource.getFile();
    	File file = new File(jsonPath);
        File[] files = file.listFiles();
        List<String> jsonFiles = new ArrayList<>();
        for (File jsonFile : files) {
            if (jsonFile.isFile() && jsonFile.getName().endsWith(".json")) {
                jsonFiles.add(jsonFile.getName());
            }
        }
                        
        // Create a new session for the user
        //session.invalidate();
        
        if (session.getAttribute("emailMessage") != null) {
            model.addAttribute("emailMessage", session.getAttribute("emailMessage"));
            // Eliminar el mensaje de la sesión después de agregarlo al modelo
            session.removeAttribute("emailMessage");
        }

        model.addAttribute("jsonFiles", jsonFiles);
        return "home";
    }
    

	
}


