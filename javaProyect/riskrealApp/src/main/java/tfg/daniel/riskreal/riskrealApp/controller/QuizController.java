package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
import tfg.daniel.riskreal.riskrealApp.model.Answers;
import tfg.daniel.riskreal.riskrealApp.model.Questions;
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
	@RequestMapping(value = "/quiz2", method = { RequestMethod.GET, RequestMethod.POST })
	public String mostrarCuestionario(Model model, HttpSession session) {
		
		UserSelection userSelection = (UserSelection) session.getAttribute("userSelection");
		Quiz cuestionario = (Quiz) session.getAttribute("quiz");
		if (cuestionario == null) {
			System.out.println("No se ha recibido el cuestionario en la session");
			return "home";
		}
		
		int preguntaActual = (Integer) session.getAttribute("preguntaActual");
		
		// To let the html access "userselecion"
		model.addAttribute("userselection", userSelection.getAnswers());
		
		// To let the html access "answersvalues"
		model.addAttribute("answersvalues", userSelection.getAnswersValues());
		
		// To let the html acces "cuestionario"
		model.addAttribute("cuestionario", cuestionario);
		
		// To let the html access "preguntaActual"
		model.addAttribute("preguntaActual", preguntaActual);
		
		// Just for test - to format the session creation time
		Date creationTime = new Date(session.getCreationTime());
		// Just for test -  show of the session id
		System.out.println("Fecha de creación de la sesión: " + creationTime.toString());
		System.out.println("Id de sesión: " + session.getId());		
		
		
		return "/quiz2";
	}
	

	@PostMapping("/quiz/startQuiz")
	public String startQuiz(Model model, @RequestParam("archivo") String archivo, HttpSession session) {
		
		// We get full quiz from json file
		Quiz cuestionario = getQuiz(archivo);
		
		// New UserSelection object
        UserSelection userSelection = new UserSelection();
                
        // We put the attribute to the session
        session.setAttribute("userSelection", userSelection);
		
		// We put the quiz in the session
		session.setAttribute("quiz", cuestionario);
		
		session.setAttribute("preguntaActual", 1);
		
		//return "quiz";
		return "redirect:/quiz2";
	}
	
	@PostMapping("/quiz/showResults")
	public String startQuiz(@RequestParam(value="accion") String estado, @RequestParam(value="pregunta") String question, @RequestParam(value="respuestaSeleccionada", required = false) String text, 
			HttpSession session, Model model) {
		
		// Parse the question id as int
		int questionInt = Integer.parseInt(question);
		UserSelection userSelection = (UserSelection) session.getAttribute("userSelection");
		Quiz cuestionario = (Quiz) session.getAttribute("quiz");
		
		if(text != null) {
			saveScore(userSelection, cuestionario, questionInt, text);
		}
		
		System.out.println(estado);
		
		if (estado.equalsIgnoreCase("Next")) {
			
			session.setAttribute("preguntaActual", questionInt+1);
					
			//return "quiz";
			return "redirect:/quiz2";
		}
		
		if (estado.equalsIgnoreCase("Prev")) {
			
			session.setAttribute("preguntaActual", questionInt-1);
					
			//return "quiz";
			return "redirect:/quiz2";
		}
							
		int score = 0;
		
		for (Integer clave : userSelection.getAnswersValues().keySet()) {
			score += userSelection.getAnswersValues().get(clave);
			System.out.println("El valor de la pregunta: " + 
					clave + 
					" es: " + 
					userSelection.getAnswersValues().get(clave));
		}
		
		System.out.println("El score final es: " + score);
		
		// To let the html acces "cuestionario"
		model.addAttribute("resultado", score);
		
		return "resultados";
	}
	
    
    @GetMapping("/loadQuiz")
    public String loadQuiz(Model model, @RequestParam("archivo") String archivo,  HttpSession session) {
    	
    	    		
    	// We get full quiz from json file
    	Quiz cuestionario = getQuiz(archivo);
    	
    	
    	

        
        //model.addAttribute("jsonFiles", jsonFiles);
        return "home";
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
	
	private void saveScore(UserSelection userSelection, Quiz cuestionario, int questionInt, String text) {
		int value = 0;
		
		for (Questions quest : cuestionario.getQuestions()) {
			if (quest.getId() == questionInt) {
				for (Answers ans : quest.getAnswers()) {
					if (ans.getText().equals(text)) {
						value = ans.getValue();
					}
				}
			}
		}
		
		userSelection.setAnswerValue(questionInt, value);
		userSelection.setAnswer(questionInt, text);
		
	}

}
