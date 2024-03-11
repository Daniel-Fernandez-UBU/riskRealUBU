
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import tfg.daniel.riskreal.riskrealApp.model.Cuestionario;

@Controller
public class HomeController {
	
	/**
	 * Página cuestionario.html
	 * @param model
	 * @return
	 */
	@GetMapping("/cuestionario")
	public String mostrarCuestionario(Model model) {
		
		// Obtenemos el objeto del json.
		Cuestionario cuestionario = getCuestionario();
		
		// Lo cargamos en el modelo
		model.addAttribute("cuestionario", cuestionario);
		
		return "cuestionario";
	}
	
	/**
	 * Página inicial home.html
	 * @return
	 */
	@GetMapping("/")
	public String inicio() {
		
		return "home"; 
	}
	
	/**
	 * Método para obtener el cuestionario del json
	 * @return
	 */
	private Cuestionario getCuestionario() {
		// create Object Mapper
		ObjectMapper mapper = new ObjectMapper();
		
		Cuestionario quest = null;

		// read JSON file and map/convert to java POJO
		try {
			
			ClassPathResource staticDataResource = new ClassPathResource("schema_cuestionario_v1.json");
			File json = staticDataResource.getFile();
		    quest = mapper.readValue(json, Cuestionario.class);
		    

		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return quest;
	}
}


