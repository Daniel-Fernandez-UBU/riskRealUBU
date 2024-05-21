package tfg.daniel.riskreal.riskrealApp.model;

/**
 * Class Answers.
 * 
 * Class for each possible answer.
 * 
 */


public class Answers {
	
	private String text;
	private String image;
	private int value = 0; //Default value
	private int id;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//Getters and setters
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Answers [id= " + id + "text=" + text + ", image=" + image + ", value=" + value + "]";
	}



}
