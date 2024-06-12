package tfg.daniel.riskreal.riskrealApp.controller;


import java.util.Date;
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

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;
import tfg.daniel.riskreal.riskrealApp.services.CSVService;
import tfg.daniel.riskreal.riskrealApp.services.JsonService;


/**
 * Class GuestController.
 *  
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
@Controller
public class GuestController {
	
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
	 * Method setGuestData.
	 * @param model the model
	 * @param formFile the file
	 * @param session the current session
	 * @return the view
	 */
	@PostMapping("/anonymousData")
	public String setGuestData(Model model, @RequestParam("archivo") String formFile, HttpSession session) {
		
    	User user = new User();
    	
    	model.addAttribute("user", user);
		       
        // We put the attribute to the session
        session.setAttribute("file", formFile);
        
        System.out.println(formFile);
				
		return "guest/anonymousdata";
	}
	
	/**
	 * Method getGuestDescrption.
	 * @param model the model
	 * @param gender the gender
	 * @param age the age
	 * @param rol the rol
	 * @param session the current session
	 * @return the view
	 */
	@PostMapping("/anonymousDescription")
	public String getGuestDescription(Model model, HttpSession session, @RequestParam("gender") String gender,
			@RequestParam("age") String age, @RequestParam("rol") String rol) {
		
		String file = (String) session.getAttribute("file");
		file = jsonPathLang + "/" + file;
		
		// We get full quiz from json file
		Quiz quiz = jsonService.getJsonQuiz(file, true);
		
		System.out.println(quiz.toString());
		
		// New UserSelection object
        UserSelection userSelection = new UserSelection();
                
        // We put the attribute to the session
        session.setAttribute("userSelection", userSelection);
		
		session.setAttribute("quiz", quiz);
		session.setAttribute("age", age);
		session.setAttribute("rol", rol);
		session.setAttribute("gender", gender);
		session.setAttribute("preguntaActual", 1);
		
		model.addAttribute("quiz", quiz);
				
		return "guest/anonymousdescription";
	}
	
	/**
	 * Method startGuestQuiz.
	 * @param session the current session
	 * @param gender the gender
	 * @param age the age
	 * @param rol the rol
	 * @return the view
	 */
	@PostMapping("/anonymousQuiz")
	public String startGuestQuiz(HttpSession session, @RequestParam("gender") String gender,
			@RequestParam("age") String age, @RequestParam("rol") String rol) {
			
		String file = (String) session.getAttribute("file");
		file = jsonPathLang + "/" + file;
		
		// We get full quiz from json file
		Quiz quiz = jsonService.getJsonQuiz(file, true);
		
		// New UserSelection object
        UserSelection userSelection = new UserSelection();
                
        // We put the attribute to the session
        session.setAttribute("userSelection", userSelection);
		
		// We put the quiz in the session
		session.setAttribute("quiz", quiz);
		// We put the quiz in the session
		session.setAttribute("age", age);
		// We put the quiz in the session
		session.setAttribute("rol", rol);
		// We put the quiz in the session
		session.setAttribute("gender", gender);
		
		session.setAttribute("preguntaActual", 1);
		return "redirect:/anonymousStartQuiz";
	}
	
	/**
	 * The method showGuestQuiz.
	 * @param model the model
	 * @param session the current session
	 * @return the view.
	 */
	@RequestMapping(value = "/anonymousStartQuiz", method = { RequestMethod.GET, RequestMethod.POST })
	public String showGuestQuiz(Model model, HttpSession session) {
		
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
		
		// Just for test - to format the session creation time
		Date creationTime = new Date(session.getCreationTime());
		// Just for test -  show of the session id
		System.out.println("Guest - Fecha de creación de la sesión: " + creationTime.toString());
		System.out.println("Guest - Id de sesión: " + session.getId());		
		
		
		return "guest/anonymousquiz";
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
	@PostMapping("/anonymousShowResults")
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
			jsonService.saveScore(userSelection, quiz, questionInt, text);
		}
		
		System.out.println(estado);
		
		if (estado.equalsIgnoreCase("Next")) {
			
			session.setAttribute("preguntaActual", questionInt+1);
					
			return "redirect:/anonymousStartQuiz";
		}
		
		if (estado.equalsIgnoreCase("Prev")) {
			
			session.setAttribute("preguntaActual", questionInt-1);
					
			return "redirect:/anonymousStartQuiz";
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
		model.addAttribute("max", quiz.getMaxvalue());
		
		// Download CSV
		csvService.downloadCSV();
		
		return "guest/anonymousresults";
	}
	
}
