package tfg.daniel.riskreal.riskrealApp.model;


import java.util.List;


/**
 * Class Quiz.
 * 
 * Class for store each questionnaire as object
 * 
 */


public class Quiz {
	

	private int id;
	private String title;
	private String image;
	private String languaje;
	private String description;
	private int type;
	private List<Questions> questions;
	

	//Getters and Setters	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}
	public List<Questions> getQuestions() {
		return questions;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLanguaje() {
		return languaje;
	}
	public void setLanguaje(String languaje) {
		this.languaje = languaje;
	}

}
