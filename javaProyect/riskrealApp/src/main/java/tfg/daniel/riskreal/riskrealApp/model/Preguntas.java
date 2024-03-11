package tfg.daniel.riskreal.riskrealApp.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

/**
 * Clase Preguntas.
 * 
 * Clase para almacenar las preguntas de los cuestionarios como objeto
 * y un listado de posibles Respuestas
 * 
 */
public class Preguntas {
	
	private int id;
	private String description;
	private String image;
	private List<Respuestas> respuestas;
	
	private String imagePath = "image/";
	
	//Getters and setters
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
	public File getImage() {
		return getImageFile(this.image);
	}
	public void setImage(String image) {
		this.image = imagePath + image;
	}
	public List<Respuestas> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(List<Respuestas> respuestas) {
		this.respuestas = respuestas;
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