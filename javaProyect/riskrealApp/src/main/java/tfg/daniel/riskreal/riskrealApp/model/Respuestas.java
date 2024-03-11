package tfg.daniel.riskreal.riskrealApp.model;


/**
 * Clase Respuestas.
 * 
 * Clase para cada posible respuesta.
 * 
 */

public class Respuestas {
	
	private String text;
	private String image;
	private int value = 0; //Valor por defecto
	
	
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
		return "Respuestas [text=" + text + ", image=" + image + ", value=" + value + "]";
	}


}
