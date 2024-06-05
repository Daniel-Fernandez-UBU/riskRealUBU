package tfg.daniel.riskreal.riskrealApp.services;

import java.io.File;
import java.io.IOException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.Quiz;

/**
 * Class jsonService.
 * 
 * Sevice class for validate json schema or whatever related with the json files.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Service
public class JsonService {
	
	/** Inyect CustomConfig dependencies */
	@Autowired
	private CustomConfig customConfig;
	
	/** The schema file. */
	private File schemaFile;
	
	/**
	 * Inits the class.
	 */
	@PostConstruct
	public void init() {
	    this.schemaFile = customConfig.getFileSchema();
	}
	
	/**
	 * Method checkQuizSchema.
	 * 
	 * Method that check if the quiz json is valid or not
	 * @param quizFile
	 * @return true or false
	 */
	public boolean checkQuizSchema(File quizFile) {
		
		try {
			
			JSONObject jsonSchema = new JSONObject(new JSONTokener(schemaFile.toURI().toString()));
			JSONObject jsonQuiz = new JSONObject(new JSONTokener(quizFile.toURI().toString()));
			
	        // Validate JSON
	        Schema schema = SchemaLoader.load(jsonSchema);
	        schema.validate(jsonQuiz);
	        
	        return true;
			
		}  catch (org.everit.json.schema.ValidationException e) {
	        System.err.println("JSON Validation Error: " + e.getMessage());
	    }
        
		return false;
	}
	
	/**
	 * Method for get the full Quiz from json file.
	 * @param String jsonQuiz - full json path
	 * @return
	 */
	public Quiz getJsonQuiz(String jsonQuiz) {
		
		// create Object Mapper
		ObjectMapper mapper = new ObjectMapper();
		
		Quiz quiz = null;

		// read JSON file and map/convert to java POJO
		try {
			
			File json = new File(jsonQuiz);
			quiz = mapper.readValue(json, Quiz.class);
		    

		} catch (IOException e) {
			System.err.println("JSON Quiz generator error: " + e.getMessage());
		}
		
		return quiz;
		
	}
	

}
