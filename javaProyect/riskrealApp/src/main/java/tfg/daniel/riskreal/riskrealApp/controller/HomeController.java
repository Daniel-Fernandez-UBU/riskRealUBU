
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import tfg.daniel.riskreal.riskrealApp.model.Questions;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;

@Controller
public class HomeController {
	
	private int indice = 0;
	
	/**
	 * Página cuestionario.html
	 * @param model
	 * @return
	 */
	@GetMapping("/cuestionario")
	public String mostrarCuestionario(Model model) {
		
		// Obtenemos el objeto del json.
		Quiz cuestionario = getQuiz();
		
		// Lo cargamos en el modelo
		model.addAttribute("cuestionario", cuestionario);
		model.addAttribute("indice", getIndex());
		model.addAttribute("upIndex", updateIndex());
		model.addAttribute("dwIndex", removeIndex());
		
		return "cuestionario";
	}
 
	/**
	 * Página quiz.html
	 * @param model
	 * @return
	 */
	@GetMapping("/quiz")
	public String showQuiz(Model model) {
		
		// Obtenemos el objeto del json.
		Quiz cuestionario = getQuiz();
		
		// Lo cargamos en el modelo
		model.addAttribute("cuestionario", cuestionario);
		
		return "quiz";
	}
    
	
	/**
	 * Página quiz.html
	 * @param model
	 * @return
	 * 
	 */
	@GetMapping("/questions")
	public String showQuestion() {
	    return "questions";
	}
    
	
	/**
	 * Página inicial home.html
	 * @return
	 */
	@GetMapping("/")
	public String inicio() {
		
		return "home"; 
	}
	
	/**
	 * Método para obtener el cuestionario del json
	 * @return
	 */
	private Quiz getQuiz() {
		// create Object Mapper
		ObjectMapper mapper = new ObjectMapper();
		
		Quiz quiz = null;

		// read JSON file and map/convert to java POJO
		try {
			
			ClassPathResource staticDataResource = new ClassPathResource("schema_cuestionario_v1.json");
			File json = staticDataResource.getFile();
			quiz = mapper.readValue(json, Quiz.class);
		    

		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return quiz;
	}
	
	public int updateIndex() {
		this.indice += 1;
		return this.indice;
	}
	
	public int getIndex() {
		return this.indice;
	}
	
	public int removeIndex() {
		this.indice -= 1;
		return this.indice;
	}
}


