package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.core.io.Resource;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import tfg.daniel.riskreal.riskrealApp.model.Answers;
import tfg.daniel.riskreal.riskrealApp.model.Questions;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;
import tfg.daniel.riskreal.riskrealApp.services.CSVService;
import tfg.daniel.riskreal.riskrealApp.services.LangService;

@Controller
@SessionAttributes("preguntasRespondidas")
@PropertySource("classpath:custom.properties")
public class QuizController {
	
	
	@Autowired
	private LangService langService;
	
	@Value("${json.quiz.file.path}")
	private String jsonPath;
	
	@Value("${json.quiz.file.path.lang}")
	private String jsonPathLang;
	
	@Autowired
    private UserRepository userRepository; 
	
	@Autowired
	private CSVService csvService;
	
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
		
		
		// Get file
		archivo =   jsonPath + "/" + archivo;
		
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
					
			return "redirect:/quiz2";
		}
		
		if (estado.equalsIgnoreCase("Prev")) {
			
			session.setAttribute("preguntaActual", questionInt-1);
					
			return "redirect:/quiz2";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<User> userOpt = userRepository.findById(authentication.getName());
		
		User user = new User();
		
		if (userOpt.isPresent()) {
			user = userOpt.get();
		}
		
		// Generate CSV
		csvService.generateCSV(user, userSelection);
		
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
		
		// Download CSV
		csvService.downloadCSV();
		
		return "resultados";
	}
	
    
    @PostMapping("/loadQuiz")
    public String loadQuiz(Model model, @RequestParam("archivo") String archivo,  HttpSession session) {
    	
    	archivo =   jsonPath + "/" + archivo;
    	System.out.println(archivo);
    	// We get full quiz from json file

    	/**
    	// Añadimos el cuestionario con su Id, titulo
    	propertiesService.addProperties(base + cuestionario.getId() + ".id", String.valueOf(cuestionario.getId()), lang);
    	propertiesService.addProperties(base + cuestionario.getId() + ".title", cuestionario.getTitle(), lang);
    	
    	String baseQuestion = base +  String.valueOf(cuestionario.getId()) + ".question.";
    
    	for (Questions question : cuestionario.getQuestions()) {
    		// Add question id
    		propertiesService.addProperties(baseQuestion + question.getId() + ".id", String.valueOf(question.getId()), lang);
	    	// Add question text	
    		propertiesService.addProperties(baseQuestion + question.getId() + ".text", question.getDescription(), lang);
    		String baseAnswer = baseQuestion + String.valueOf(question.getId()) + ".answer.";
    		for (Answers ans : question.getAnswers()) {
    			// Add answer id
    			propertiesService.addProperties(baseAnswer + ans.getId() + ".id", String.valueOf(ans.getId()), lang);
    			// Add answer text
    			propertiesService.addProperties(baseAnswer + ans.getId() + ".text", ans.getText(), lang);
    			// Add answer value
    			propertiesService.addProperties(baseAnswer + ans.getValue() + ".value", String.valueOf(ans.getValue()), lang);
    		}
    	}
    	*/
    	
    	langService.showLangs();

    	System.out.println("He pasado por aquí");
        
        //model.addAttribute("jsonFiles", jsonFiles);
        return "redirect:/";
    }
    
    @PostMapping("/jsonQuiz")
    public String jsonQuiz(Model model,  HttpSession session, @RequestParam("archivo") String archivo) {
    	
    	langService.showLangs();
    	
    	String[] langArray = langService.getLangs().split(",");
    	
    	System.out.println("Idiomas disponibles: ");
    	for (String lang : langArray) {
    		System.out.println(lang);
    	}
    	
    	archivo =   jsonPath + "/" + archivo;
    	
    	String lang = "_es";
    	
    	System.out.println("Archivo que se pasa a getQuiz: " + archivo);
    	Quiz cuestionario = getQuiz(archivo);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	File json = new File(jsonPathLang + "_" + cuestionario.getId() + lang + ".json");
    	   	
    	try {
    		if (!json.exists()) {
                json.createNewFile();
                System.out.println("Archivo creado: " + json.getAbsolutePath());
    		}
			mapper.writeValue(json, cuestionario);
			System.out.println("Creado el json: " + json.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	

        return "redirect:/";
    }
    
    @PostMapping("/testQuiz")
    public String testQuiz(Model model,  HttpSession session) {
    	
    	String archivo =   jsonPathLang + "_es.properties";
    	
    	System.out.println(archivo);
    	// We get full quiz from json file
    	
    	File file = new File(archivo);
    	
    	Resource resource = new FileSystemResource(file);
    	
    	try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			
			System.out.println(props.entrySet());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	System.out.println("He pasado por aquí");
        
        //model.addAttribute("jsonFiles", jsonFiles);
        return "redirect:/";
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
	
	/**
	 * Method saveScore().
	 * 
	 * @param userSelection
	 * @param quiz
	 * @param questionInt
	 * @param text
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
