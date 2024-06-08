package tfg.daniel.riskreal.riskrealApp.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.Answers;
import tfg.daniel.riskreal.riskrealApp.model.Questions;
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
			
			String schemaContent = readFileContent(schemaFile);
			String jsonContent = readFileContent(quizFile);
			
			JSONObject jsonSchema = new JSONObject(new JSONTokener(schemaContent));
			JSONObject jsonQuiz = new JSONObject(new JSONTokener(jsonContent));
			
	        // Validate JSON
	        Schema schema = SchemaLoader.load(jsonSchema);
	        schema.validate(jsonQuiz);
	        
	        return true;
			
		}  catch (org.everit.json.schema.ValidationException | IOException e) {
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
		    
			// Get the images normalized
			quiz.setImage(normalizeImages(quiz.getImage()));
			quiz.setQuestions(normalizeQuestions(quiz.getQuestions()));

		} catch (IOException e) {
			System.err.println("JSON Quiz generator error: " + e.getMessage());
		}
		
		return quiz;
		
	}
	
	/**
	 * Method normalizeImages.
	 * @param imageList
	 * @return image list normalized
	 */
	private List<String> normalizeImages(List<String> imageList){
		List<String> normImages = new ArrayList<>();
		
    	for (String image : imageList) {
    		normImages.add(customConfig.getQuizImagePath() + image);
    	}
	
		return normImages;
		
	}
	
	/**
	 * Method normalizeQuestions.
	 * @param questionsList
	 * @return questions normalized
	 */
	private List<Questions> normalizeQuestions(List<Questions> questionsList){
		List<Questions> normalized = new ArrayList<>();
		
    	for (Questions quest : questionsList) {
    		quest.setImage(normalizeImages(quest.getImage())); 
    		quest.setAnswers(normalizeAnswers(quest.getAnswers()));
    		normalized.add(quest);
    	}
		return normalized;
		
	}
	
	/**
	 * Method normalizeAnswers.
	 * @param answersList
	 * @return Answers normalized
	 */
	private List<Answers> normalizeAnswers(List<Answers> answersList){
		List<Answers> normalized = new ArrayList<>();
		
    	for (Answers ans : answersList) {
    		ans.setImage(customConfig.getQuizImagePath() + ans.getImage()); 
    		normalized.add(ans);
    	}
		return normalized;
		
	}
	
	
	/**
	 * Method getJsonFiles.
	 * 
	 * Method that from a file, get all the json files in the path
	 * @param file
	 * @return List<String> jsonFiles
	 * 
	 */
	public List<String> getJsonFiles(File file){
		List<String> jsonFiles = new ArrayList<>();
    	File[] files = file.listFiles();
        for (File jsonFile : files) {
            if (jsonFile.isFile() && jsonFile.getName().endsWith(".json")) {
                jsonFiles.add(jsonFile.getName());
            }
        }
        return jsonFiles;
	}
	
    private static String readFileContent(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer, "UTF-8");
    }
	

}
