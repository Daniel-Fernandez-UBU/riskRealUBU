package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;
import tfg.daniel.riskreal.riskrealApp.services.JsonService;


@Controller
public class jsonController {
	
	/** Injected CustomConfig class */
	@Autowired 
	private CustomConfig customConfig;
	
	/** Injected JsonService class */
	@Autowired
	private JsonService jsonService;
	
	/** Class attribute jsonPath. */
	private String jsonPath;
	
	/** Class attribute jsonPathLang. */
	private String jsonPathLang;
	
	/** Class attribute langService. */
	private String langService;
	
	/**
	 * Inits the class.
	 */
	@PostConstruct
	public void init() {
	    this.jsonPathLang = customConfig.getQuizFilePath();
	    this.jsonPath = customConfig.getJsonFilePath();
	    this.langService = customConfig.getLangAvailables();
	}
	
	@GetMapping("/jsonView")
	public String jsonList(Model model) {
		
    	File file = new File(jsonPath);
        List<String> jsonFiles = new ArrayList<>();
        
        // We control that the path exists and that there are json files on it
        if (file.exists()) {
        	jsonFiles = jsonService.getJsonFiles(file);
            if (!jsonFiles.isEmpty()) {
                model.addAttribute("jsonFiles", jsonFiles);
            }
        }

		return "/quiz/loadQuiz";
	}
	
    @PostMapping("/json/upload")
    public String uploadJson(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {

    	redirectAttributes.addFlashAttribute("filedeleted", "");
    	// We check that file is not empty
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "file.error");
            redirectAttributes.addFlashAttribute("type", "primary");
            return "redirect:/jsonView";
        }
        
        // We check that is a json file
        if (!file.getOriginalFilename().endsWith(".json")) {
        	redirectAttributes.addFlashAttribute("message", "json.error");
        	redirectAttributes.addFlashAttribute("type", "primary");
        	return "redirect:/jsonView";
        }


        try {
            // Get the original file name
            byte[] bytes = file.getBytes();
            Path path = Paths.get(jsonPath + "/manual_" + file.getOriginalFilename());
            Files.write(path, bytes);
            
            File jsonFile = new File(jsonPath + "/manual_" + file.getOriginalFilename());
            
            // If json don't have a good schema
            if (!jsonService.checkQuizSchema(jsonFile)) {
            	jsonFile.delete();
                redirectAttributes.addFlashAttribute("message", "schema.error");
                redirectAttributes.addFlashAttribute("filedeleted", file.getOriginalFilename());
                redirectAttributes.addFlashAttribute("type", "danger");
                return "redirect:/jsonView";
            }

            redirectAttributes.addFlashAttribute("message","file.uploaded");
            redirectAttributes.addFlashAttribute("type", "success");

        } catch (IOException e) {
            System.err.println("uploadJson exception: " + e.toString());
        }

        return "redirect:/jsonView";
    }
	
	
    @PostMapping("/json/generateQuiz")
    public String saveJsonQuiz(Model model,  HttpSession session, @RequestParam("archivo") String archivo
    		,RedirectAttributes redirectAttributes) {

    	archivo = jsonPath + "/" + archivo;
    	File jsonOld = new File(archivo);
    	redirectAttributes.addFlashAttribute("filedeleted", "");
    	
        // If json don't have a valid schema
        if (!jsonService.checkQuizSchema(jsonOld)) {
            redirectAttributes.addFlashAttribute("message", "schema.error");
            redirectAttributes.addFlashAttribute("filedeleted", archivo);
            redirectAttributes.addFlashAttribute("type", "danger");
            return "redirect:/json/view";
        }
    	   	
    	String[] langArray = langService.split(",");
    	
    	System.out.println("Idiomas disponibles: ");
    	for (String lang : langArray) {
    		System.out.println(lang);
    	}
    	   	    	
    	System.out.println("File sended to getQuiz: " + archivo);
    	Quiz cuestionario = jsonService.getJsonQuiz(archivo, false);
    	
    	String lang = cuestionario.getLanguage();
    	
    	File json = new File(jsonPathLang + "/quiz_" + cuestionario.getId() + "_" + lang + ".json");
    	
    	if (langService.contains(lang)){
    		System.out.println("Lenguaje encontrado en custom.properties: " + lang);
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();

    	try {
    		if (!json.exists()) {
                json.createNewFile();
                System.out.println("File created: " + json.getAbsolutePath());

    		} 
			mapper.writeValue(json, cuestionario);
			System.out.println("Json quiz added: " + json.getAbsolutePath());
			jsonOld.delete();

		} catch (IOException e) {
			System.out.println("saveJsonQuiz exception: " + e.toString());
		}
    	
        redirectAttributes.addFlashAttribute("message","generated");
        redirectAttributes.addFlashAttribute("type", "success");

        return "redirect:/jsonView";
    }
    

}
