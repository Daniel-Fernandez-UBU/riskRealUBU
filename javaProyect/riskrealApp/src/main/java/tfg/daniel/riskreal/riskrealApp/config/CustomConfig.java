package tfg.daniel.riskreal.riskrealApp.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import jakarta.annotation.PostConstruct;


/**
 * Class CustomConfig.
 * 
 * Class that have all the information included in custom.properties.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */

@Configuration
@PropertySource("classpath:custom.properties")
public class CustomConfig {
	
	@Autowired
	private ResourceLoader resourceLoader;
		
	/** String email properties from email.properties file.  */
	@Value("${json.quiz.file.path}")
	private String jsonFilePath;
	
	/** The quiz file path. */
	@Value("${json.quiz.file.path.lang}")
	private String quizFilePath;
	
	/** The quiz image path. */
	@Value("${json.quiz.images.path}")
	private String quizImagePath;
	
	/** The csv score path. */
	@Value("${csv.score.path}")
	private String csvScorePath;
	
	/** The lang availables. */
	@Value("${languages.availables}")
	private String langAvailables;
	
	/** The json schema. */
	@Value("${json.schema}")
	private String jsonSchema;
	
	/** The file json schema. */
	private File fileSchema;
	
	/** The quiz test file. */
	@Value("${test.quiz}")
	private String testQuiz;
	
    /**
     * Method init
     * 
     * Create all the paths if they don`t exists.
     * 
     */
    @PostConstruct
    public void init() {
        createDirectoryIfNotExists(jsonFilePath);
        createDirectoryIfNotExists(quizFilePath);
        createDirectoryIfNotExists(quizImagePath);
        createDirectoryIfNotExists(csvScorePath.split("scores.csv")[0]);
        File schema = new File(jsonSchema);
        if (!schema.exists()) {
        	System.out.println("Using default quiz_schema.json from classpath");
        	Resource resource = resourceLoader.getResource("classpath:quiz_schema.json");
        	jsonSchema = resource.getFilename();
        	try {
        		fileSchema = resource.getFile();
			} catch (IOException e) {
				System.out.println("CustomConfig exception: quiz_schema.json nof found in classpath. " + e.toString());
			}
        } else {
        	fileSchema = new File(jsonSchema);
        }
    }

    /**
     * Method createDirectoryIfNotExists
     * 
     * This method check if exists the directoy and if not, it create it.
     *
     * @param path the directory path
     */
    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Directory created: " + path);
            } else {
                System.err.println("Failed to create directory: " + path);
            }
        }
    }
    
	/**
	 * Getter getTestQuiz.
	 *
	 * @return the test quiz prefix
	 */
	public String getTestQuiz() {
		return testQuiz;
	}

	/**
	 * Getter getJsonFilePath.
	 *
	 * @return the json file path
	 */
	public String getJsonFilePath() {
		return jsonFilePath;
	}
	
	/**
	 * Getter fileSchema
	 *
	 * @return the json schema file
	 */
	public File getFileSchema() {
		return fileSchema;
	}
	
	/**
	 * Getter getQuizImagePath.
	 *
	 * @return the quiz image path
	 */
	public String getQuizImagePath() {
		return quizImagePath;
	}

	/**
	 * Getter getQuizFilePath.
	 *
	 * @return the quiz file path
	 */
	public String getQuizFilePath() {
		return quizFilePath;
	}

	/**
	 * Getter getCsvScorePath.
	 *
	 * @return the csv score path
	 */
	public String getCsvScorePath() {
		return csvScorePath;
	}

	/**
	 * Getter getLangAvailables.
	 *
	 * @return the lang availables
	 */
	public String getLangAvailables() {
		return langAvailables;
	}
	
}
