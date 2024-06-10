package tfg.daniel.riskreal.riskrealApp.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import tfg.daniel.riskreal.riskrealApp.model.Profile;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.repository.ProfileRepository;
import tfg.daniel.riskreal.riskrealApp.repository.UserRepository;


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
	
	/** The resource loader. */
	@Autowired
	private ResourceLoader resourceLoader;
	
    /** The user repository. */
    @Autowired
    private UserRepository userRepository;
    
    /** The profile repository. */
    @Autowired
    private ProfileRepository profileRepository;
    
    /** The password encoder. */
    @Autowired
    private PasswordEncoder passwordEncoder;
			
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
	
	/** The admin default username. */
	@Value("${admin.username}")
	private String admin;
	
	/** The admin default password. */
	@Value("${admin.password}")
	private String pass;
	
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
        
        if (!userRepository.existsById(admin)) {
        	insertAdminUser();
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
     * Method insertAdminUser.
     * 
     * Insert the admin user in the database if this not exists.
     * 
     */
    private void insertAdminUser() {
    	
    	User adminUser = new User();
    	adminUser.setEmail(admin);
    	adminUser.setAge("admin");
    	adminUser.setCompany(99999);
    	adminUser.setFirstname("admin");
    	adminUser.setLastname("riskreal");
    	adminUser.setGender("admin");
    	adminUser.setPassword(passwordEncoder.encode(pass));
    	adminUser.setRol("admin");
    	adminUser.setStatus(2); // Force change password the first login
    	
    	// Insert user in the database
    	userRepository.save(adminUser); 
    	
    	Profile adminProfile = new Profile();
    	adminProfile.setUsername(admin);
    	adminProfile.setProfile("ADMIN");
    	
    	// Insert profile in the database
    	profileRepository.save(adminProfile); 
    	
    }
    
    
    /**
     * Getter getAdmin.
     * @return admin username
     */
	public String getAdmin() {
		return admin;
	}

	/**
	 * Getter getPass.
	 * @return admin pass
	 */
	public String getPass() {
		return pass;
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
