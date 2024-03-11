package tfg.daniel.riskreal.riskrealApp.model;


import java.util.List;



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
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Respuestas> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(List<Respuestas> respuestas) {
		this.respuestas = respuestas;
	}
	
}