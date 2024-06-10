package tfg.daniel.riskreal.riskrealApp.model;

/**
 * Class Answers.
 * 
 * Class for each possible answer.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
public class Answers {
	
	/** The text. */
	private String text;
	
	/** The image. */
	private String image;
	
	/** The value. */
	private int value = 0; //Default value
	
	/** The id. */
	private int id;
	
	
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
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return this.image;
	}
	
	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Answers [id= " + id + "text=" + text + ", image=" + image + ", value=" + value + "]";
	}



}
