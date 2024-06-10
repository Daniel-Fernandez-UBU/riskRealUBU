package tfg.daniel.riskreal.riskrealApp.model;

import java.util.List;


/**
 * Clase Preguntas.
 * 
 * Class to store quiz questions as an object and a list of possible Answers.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */

public class Questions {
	
	/** The id. */
	private int id;
	
	/** The description. */
	private String description;
	
	/** The image. */
	private List<String> image;
	
	/** The answers. */
	private List<Answers> answers;
	
	/** The skill. */
	private int skill;
	
	/**
	 * Gets the skill.
	 *
	 * @return the skill
	 */
	public int getSkill() {
		return skill;
	}
	
	/**
	 * Sets the skill.
	 *
	 * @param skill the new skill
	 */
	public void setSkill(int skill) {
		this.skill = skill;
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
	 * Gets the image.
	 *
	 * @return the image
	 */
	public List<String> getImage() {
		return image;
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
	 * Gets the answers.
	 *
	 * @return the answers
	 */
	public List<Answers> getAnswers() {
		return answers;
	}
	
	/**
	 * Sets the answers.
	 *
	 * @param answers the new answers
	 */
	public void setAnswers(List<Answers> answers) {
		this.answers = answers;
	}

}
