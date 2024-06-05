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


@Controller
public class jsonController {
	
	/** Injected CustomConfig class */
	@Autowired 
	private CustomConfig customConfig;
	
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
	
	@GetMapping("/json/view")
	public String jsonList(Model model) {
		
    	File file = new File(jsonPath);
        List<String> jsonFiles = new ArrayList<>();
        
        // We control that the path exists and that there are json files on it
        if (file.exists()) {
        	File[] files = file.listFiles();
            for (File jsonFile : files) {
                if (jsonFile.isFile() && jsonFile.getName().endsWith(".json")) {
                    jsonFiles.add(jsonFile.getName());
                }
            }
            if (!jsonFiles.isEmpty()) {
                model.addAttribute("jsonFiles", jsonFiles);
            }
        }

		return "loadQuiz";
	}
	
    @PostMapping("/json/upload")
    public String uploadJson(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            // Get the original file name
            byte[] bytes = file.getBytes();
            Path path = Paths.get(jsonPath + "/manual_" + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/json/view";
    }
	
	
    @PostMapping("/json/generateQuiz")
    public String saveJsonQuiz(Model model,  HttpSession session, @RequestParam("archivo") String archivo) {
    	    	
    	String[] langArray = langService.split(",");
    	
    	System.out.println("Idiomas disponibles: ");
    	for (String lang : langArray) {
    		System.out.println(lang);
    	}
    	
    	archivo =   jsonPath + "/" + archivo;
    	    	
    	System.out.println("Archivo que se pasa a getQuiz: " + archivo);
    	Quiz cuestionario = getQuiz(archivo);
    	
    	String lang = cuestionario.getlanguage();
    	
    	if (langService.contains(lang)){
    		System.out.println("Lenguaje encontrado en custom.properties: " + lang);
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	File json = new File(jsonPathLang + "/" + lang + "_quiz_" + cuestionario.getId() + ".json");
    	File jsonOld = new File(archivo);
    	   	
    	try {
    		if (!json.exists()) {
                json.createNewFile();
                System.out.println("Archivo creado: " + json.getAbsolutePath());
    			mapper.writeValue(json, cuestionario);
    			System.out.println("Json del cuestionario añadido: " + json.getAbsolutePath());
    			jsonOld.delete();
    		}

		} catch (IOException e) {
			e.printStackTrace();
		}
    	

        return "redirect:/json/view";
    }
    
	/**
	 * Method for get the full Quiz from json file.
	 * @param String jsonQuiz - full json path
	 * @return
	 */
	private Quiz getQuiz(String jsonQuiz) {
		// create Object Mapper
		ObjectMapper mapper = new ObjectMapper();
		
		Quiz quiz = null;

		// read JSON file and map/convert to java POJO
		try {
			
			//ClassPathResource staticDataResource = new ClassPathResource(jsonQuiz);
			File json = new File(jsonQuiz);
			quiz = mapper.readValue(json, Quiz.class);
		    

		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return quiz;
	}

}
