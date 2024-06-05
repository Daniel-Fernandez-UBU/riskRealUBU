package tfg.daniel.riskreal.riskrealApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


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

	/**
	 * Getter getJsonFilePath.
	 *
	 * @return the json file path
	 */
	public String getJsonFilePath() {
		return jsonFilePath;
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
