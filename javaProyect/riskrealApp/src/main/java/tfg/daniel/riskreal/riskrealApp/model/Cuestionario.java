package tfg.daniel.riskreal.riskrealApp.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Clase Cuestionario.
 * 
 * Clase para almacenar cada cuestionario como objeto
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cuestionario {
	
	private String title;
	private String image;
	private String languaje;
	private List<Preguntas> preguntas;
		
	//Getters and Setters
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
	public List<Preguntas> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(List<Preguntas> preguntas) {
		this.preguntas = preguntas;
	}


}
