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
	
	@Value("${json.quiz.file.path.lang}")
	private String quizFilePath;
	
	@Value("${csv.score.path}")
	private String csvScorePath;
	
	@Value("${languages.availables}")
	private String langAvailables;

	/**
	 * Getter getJsonFilePath.
	 * @return
	 */
	public String getJsonFilePath() {
		return jsonFilePath;
	}

	/**
	 * Getter getQuizFilePath.
	 * @return
	 */
	public String getQuizFilePath() {
		return quizFilePath;
	}

	/**
	 * Getter getCsvScorePath.
	 * @return
	 */
	public String getCsvScorePath() {
		return csvScorePath;
	}

	/**
	 * Getter getLangAvailables.
	 * @return
	 */
	public String getLangAvailables() {
		return langAvailables;
	}
	
}
