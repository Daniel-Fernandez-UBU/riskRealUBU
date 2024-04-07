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
	private List<Questions> questions;
	

	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public List<Questions> getQuestions() {
		return questions;
	}
	public void setPreguntas(List<Questions> questions) {
		this.questions = questions;
	}



}
