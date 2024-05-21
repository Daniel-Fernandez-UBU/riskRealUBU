package tfg.daniel.riskreal.riskrealApp.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.opencsv.CSVWriter;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;



@Controller
@PropertySource("classpath:custom.properties")
public class CSVController {
	
	@Value("${csv.score.path}")
	private String csvPath;
	
	@GetMapping("/csv/generate")
    public ResponseEntity<String> generateCSV(){
		
		File file = new File(csvPath);
		
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error al crear el archivo CSV", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
		
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvPath))) {

            String[] header = {"Nombre", "Edad", "Ciudad"};
            csvWriter.writeNext(header);

            String[] record1 = {"Juan", "30", "Madrid"};
            csvWriter.writeNext(record1);

            String[] record2 = {"Ana", "25", "Barcelona"};
            csvWriter.writeNext(record2);

            String[] record3 = {"Luis", "28", "Valencia"};
            csvWriter.writeNext(record3);

            return new ResponseEntity<>("CSV creado exitosamente en: " + csvPath, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al crear el CSV", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@GetMapping("/csv/download")
	public ResponseEntity<Resource> downloadCSV() {
        File file = new File(csvPath);
        if (file.exists()) {
        	Resource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=score.csv");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

}
