package tfg.daniel.riskreal.riskrealApp.model;


import java.util.List;



/**
 * Class Quiz.
 * 
 * Class for store each json quiz as object.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
public class Quiz {
	

	/** The id. */
	private int id;
	
	/** The title. */
	private String title;
	
	/** The image. */
	private List<String> image;
	
	/** The language. */
	private String language;
	
	/** The description. */
	private String description;
	
	/** The type. */
	private int type;
	
	/** The questions. */
	private List<Questions> questions;
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public List<String> getImage() {
		return image;
	}
	
	/**
	 * Gets the first image.
	 *
	 * @return the first image
	 */
	public String getFirstImage() {
		return image.get(0);
	}
	
	
	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(List<String> image) {
		this.image = image;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * Sets the questions.
	 *
	 * @param questions the new questions
	 */
	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}
	
	/**
	 * Gets the questions.
	 *
	 * @return the questions
	 */
	public List<Questions> getQuestions() {
		return questions;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + ", image=" + image + ", language=" + language + ", description="
				+ description + ", type=" + type + ", questions=" + questions + "]";
	}
	
	

}
