package tfg.daniel.riskreal.riskrealApp;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import tfg.daniel.riskreal.riskrealApp.model.Cuestionario;

@SpringBootApplication
public class RiskrealAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskrealAppApplication.class, args);
		
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
		
		System.out.println(quest.getPreguntas().get(0).getDescription());
		
	}

}
