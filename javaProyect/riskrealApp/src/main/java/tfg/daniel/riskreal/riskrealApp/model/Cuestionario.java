package tfg.daniel.riskreal.riskrealApp.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

/**
 * Clase Cuestionario.
 * 
 * Clase para almacenar cada cuestionario como objeto
 * 
 */

public class Cuestionario {
	
	private String title;
	private String image;
	private Idioma languaje;
	private List<Preguntas> preguntas;
	private String imagePath = "image/";
		
	//Getters and Setters
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public File getImage() {
		return getImageFile(this.image);
	}
	public void setImage(String image) {
		this.image = imagePath + image;
	}
	
	public Idioma getLanguaje() {
		return languaje;
	}
	public void setLanguaje(Idioma languaje) {
		this.languaje = languaje;
	}
	public List<Preguntas> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(List<Preguntas> preguntas) {
		this.preguntas = preguntas;
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
