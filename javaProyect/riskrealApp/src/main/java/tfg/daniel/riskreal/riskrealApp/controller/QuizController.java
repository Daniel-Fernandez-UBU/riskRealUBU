package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;

@Controller
@SessionAttributes("preguntasRespondidas")
public class QuizController {
	
	/**
	 * Página cuestionario.html
	 * @param model
	 * @return
	 */
	@GetMapping("/quiz")
	public String mostrarCuestionario(Model model, @RequestParam("archivo") String archivo) {
		
		// Obtenemos el objeto del json.
		Quiz cuestionario = getQuiz(archivo);
		
		// Lo cargamos en el modelo
		model.addAttribute("cuestionario", cuestionario);
		
		return "quiz";
	}
	

	@GetMapping("/quiz/startSession")
	public String saveUser(Model model, @RequestParam("archivo") String archivo, HttpSession session) {
		    // ... (Carga las preguntas del cuestionario)
		UserSelection userSelection = (UserSelection) session.getAttribute("UserSelection");
		if (userSelection == null) {
			userSelection = new UserSelection();
			session.setAttribute("UserSelection", userSelection);
		} 
		// ... (Agrega lógica para actualizar preguntasRespondidas según la selección del usuario)
		model.addAttribute("userSelection", userSelection.getAnswers());
		// Obtenemos el objeto del json.
		Quiz cuestionario = getQuiz(archivo);
		
		// Lo cargamos en el modelo
		model.addAttribute("cuestionario", cuestionario);
		return "quiz";
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
			
			ClassPathResource staticDataResource = new ClassPathResource(jsonQuiz);
			File json = staticDataResource.getFile();
			quiz = mapper.readValue(json, Quiz.class);
		    

		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return quiz;
	}

}
