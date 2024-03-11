package tfg.daniel.riskreal.riskrealApp.model;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

/**
 * Clase Respuestas.
 * 
 * Clase para cada posible respuesta.
 * 
 */

public class Respuestas {
	
	private String text;
	private int value = 0; //Valor por defecto
	private String image;
	
	private String imagePath = "image/";
	
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
	public File getImage() {
		return getImageFile(this.image);
	}
	public void setImage(String image) {
		this.image = imagePath + image;
	}

	//Metodo para convertir la imagen en File
	private File getImageFile(String image) {
		File imageFile = null;
		ClassPathResource staticDataResource = new ClassPathResource(image);
		try {
			imageFile = staticDataResource.getFile();
		} catch (IOException e) {
			System.out.println("Imagen: " + image + " no encontrada");
			e.printStackTrace();
		}
		
		return imageFile;
	}
	

}
