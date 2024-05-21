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
	private List<String> image;
	private String language;
	private String description;
	private int type;
	private List<Questions> questions;
	
	//Getters and Setters	
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
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
	public String getlanguage() {
		return language;
	}
	public void setlanguage(String language) {
		this.language = language;
	}

}
