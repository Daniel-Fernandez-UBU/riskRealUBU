package tfg.daniel.riskreal.riskrealApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.Answers;
import tfg.daniel.riskreal.riskrealApp.model.Questions;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;
import tfg.daniel.riskreal.riskrealApp.services.CSVService;
import tfg.daniel.riskreal.riskrealApp.services.JsonService;


/**
 * Class QuizController.
 * 
 * Class that have all the methods related with the quiz.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
@Controller
@SessionAttributes("preguntasRespondidas")
public class QuizController {
	
	/** Injected CustomConfig class */
	@Autowired
	private CustomConfig customConfig;
	
	/** Injected JsonService class */
	@Autowired
	private JsonService jsonService;
		
	/** The json path lang. */
	private String jsonPathLang;
	
	/** The user repository. */
	@Autowired
    private UserRepository userRepository; 
	
	/** The csv service. */
	@Autowired
	private CSVService csvService;
	
	/**
	 * Inits the class.
	 */
	@PostConstruct
	public void init() {
	    this.jsonPathLang = customConfig.getQuizFilePath();
	}
	
	/**
	 * Method getQuizDescription.
	 * @param model
	 * @param formFile
	 * @param session
	 * @return the string
	 */
	@PostMapping("/quizDescription")
	public String getQuizDescription(Model model, @RequestParam("archivo") String formFile, HttpSession session) {
		
		String file;
		file = jsonPathLang + "/" + formFile;
		
		// We get full quiz from json file
		Quiz quiz = jsonService.getJsonQuiz(file, true);
		
		// New UserSelection object
        UserSelection userSelection = new UserSelection();
                
        // We put the attribute to the session
        session.setAttribute("userSelection", userSelection);
		
		// We put the quiz in the session
		session.setAttribute("quiz", quiz);
		
		session.setAttribute("preguntaActual", 1);
		
		model.addAttribute("quiz", quiz);
		
		return "/quiz/quizdescription";
	}
	
	/**
	 * Página cuestionario.html
	 *
	 * @param model the model
	 * @param session the session
	 * @return the string
	 */
	@RequestMapping(value = "/quizStarting", method = { RequestMethod.GET, RequestMethod.POST })
	public String mostrarCuestionario(Model model, HttpSession session) {
		
		UserSelection userSelection = (UserSelection) session.getAttribute("userSelection");
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		if (quiz == null) {
			System.out.println("No se ha recibido el cuestionario en la session");
			return "home";
		}
		
		int preguntaActual = (Integer) session.getAttribute("preguntaActual");
		
		// To let the html access "userselecion"
		model.addAttribute("userselection", userSelection.getAnswers());
		
		// To let the html access "answersvalues"
		model.addAttribute("answersvalues", userSelection.getAnswersValues());
		
		// To let the html acces "cuestionario"
		model.addAttribute("quiz", quiz);
		
		// To let the html access "preguntaActual"
		model.addAttribute("preguntaActual", preguntaActual);
				
		
		return "/quiz/quiz";
	}
	

	/**
	 * Start quiz.
	 *
	 * @return the string
	 */
	@PostMapping("/quizStartQuiz")
	public String startQuiz() {
		return "redirect:/quizStarting";
	}
	
	/**
	 * Start quiz.
	 *
	 * @param estado the estado
	 * @param question the question
	 * @param text the text
	 * @param session the session
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/quizShowResults")
	public String showResults(@RequestParam(value="accion") String estado, @RequestParam(value="pregunta") String question, @RequestParam(value="respuestaSeleccionada", required = false) String text, 
			HttpSession session, Model model) {
		
		// Parse the question id as int
		int questionInt = Integer.parseInt(question);
		UserSelection userSelection = (UserSelection) session.getAttribute("userSelection");
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		String gender = (String) session.getAttribute("gender");
		String age = (String) session.getAttribute("age");
		String rol = (String) session.getAttribute("rol");
		
		if(text != null) {
			saveScore(userSelection, quiz, questionInt, text);
		}
		
		if (estado.equalsIgnoreCase("Next")) {
			
			session.setAttribute("preguntaActual", questionInt+1);
					
			return "redirect:/quizStarting";
		}
		
		if (estado.equalsIgnoreCase("Prev")) {
			
			session.setAttribute("preguntaActual", questionInt-1);
					
			return "redirect:/quizStarting";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<User> userOpt = userRepository.findById(authentication.getName());
		
		User user = new User();
		
		if (userOpt.isPresent()) {
			user = userOpt.get();
		} else { // For not registered users
			user.setEmail("guest");
			user.setGender(gender);
			user.setRol(rol);
			user.setAge(age);
		}
		
		// Generate CSV
		csvService.generateCSV(String.valueOf(quiz.getId()), user, userSelection);
		
		int score = 0;
		
		for (Integer clave : userSelection.getAnswersValues().keySet()) {
			score += userSelection.getAnswersValues().get(clave);
		}
		
		// To let the html acces "cuestionario"
		model.addAttribute("resultado", score);
		
		// Download CSV
		csvService.downloadCSV();
		
		return "/quiz/results";
	}
	    
	
	/**
	 * Method saveScore().
	 *
	 * @param userSelection the user selection
	 * @param quiz the quiz
	 * @param questionInt the question int
	 * @param text the text
	 */
	private void saveScore(UserSelection userSelection, Quiz quiz, int questionInt, String text) {
		int value = 0;
		
		for (Questions quest : quiz.getQuestions()) {
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
