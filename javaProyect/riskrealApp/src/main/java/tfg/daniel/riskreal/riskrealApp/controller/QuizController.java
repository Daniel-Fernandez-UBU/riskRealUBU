package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/quiz", method = { RequestMethod.GET, RequestMethod.POST })
	public String mostrarCuestionario(Model model, HttpSession session) {
		
		Quiz cuestionario = (Quiz) session.getAttribute("quiz");
		if (cuestionario == null) {
			System.out.println("No se ha recibido el cuestionario en la session");
			return "json";
		}
				
		// Lo cargamos en el modelo
		model.addAttribute("cuestionario", cuestionario);
		
		return "quiz";
	}
	

	@PostMapping("/quiz/startQuiz")
	public String startQuiz(Model model, @RequestParam("archivo") String archivo, HttpSession session) {
		    // ... (Carga las preguntas del cuestionario)
		UserSelection userSelection = (UserSelection) session.getAttribute("userSelection");
		
		if (userSelection == null) {
			System.out.println("No había sesión creada, volvemos a la home");
			return "redirect:/";
		} 

		// Obtenemos el objeto del json.
		Quiz cuestionario = getQuiz(archivo);
		
		// We charge the quiz in the session
		session.setAttribute("quiz", cuestionario);
		// Lo cargamos en el modelo
		//model.addAttribute("cuestionario", cuestionario);

		//return "quiz";
		return "redirect:/quiz";
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
