
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;

import java.util.ArrayList;

@Controller
public class HomeController {
	
	  
	
    @GetMapping("/")
    public String home(Model model, HttpSession session) throws IOException {
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
		UserSelection userSelection = (UserSelection) session.getAttribute("UserSelection");
		if (userSelection == null) {
			System.out.println("Creando sesion por primera vez");
			userSelection = new UserSelection();
			session.setAttribute("UserSelection", userSelection);
		} else {
			System.out.println("La sesión ya estaba creada");
			System.out.println("Id de sesión: " + session.getId());
		}
        model.addAttribute("jsonFiles", jsonFiles);
        return "home";
    }
	
}


