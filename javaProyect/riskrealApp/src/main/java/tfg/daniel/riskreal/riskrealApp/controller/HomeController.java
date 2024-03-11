
package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import tfg.daniel.riskreal.riskrealApp.model.Cuestionario;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String inicio() {
		
		
		// create Object Mapper
		ObjectMapper mapper = new ObjectMapper();

		// read JSON file and map/convert to java POJO
		try {
			Cuestionario quest = new Cuestionario();
			ClassPathResource staticDataResource = new ClassPathResource("schema_cuestionario_v1.json");
			File json = staticDataResource.getFile();
		    mapper.readValue(json, Cuestionario.class);
		    System.out.println(quest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return "home"; 
	}
}


