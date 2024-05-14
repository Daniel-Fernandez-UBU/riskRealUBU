package tfg.daniel.riskreal.riskrealApp.model;

import java.util.List;



/**
 * Clase Preguntas.
 * 
 * Class to store quiz questions as an object
 * and a list of possible Answers
 * 
 */

public class Questions {
	
	private int id;
	private String description;
	private List<String> image;
	private List<Answers> answers;
	private int skill;
	
	//Getters and setters
	public int getSkill() {
		return skill;
	}
	public void setSkill(int skill) {
		this.skill = skill;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public List<Answers> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answers> answers) {
		this.answers = answers;
	}

}
