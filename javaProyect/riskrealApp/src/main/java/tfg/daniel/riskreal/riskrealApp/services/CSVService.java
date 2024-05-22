package tfg.daniel.riskreal.riskrealApp.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;

@Service
@PropertySource("classpath:custom.properties")
public class CSVService {

	@Value("${csv.score.path}")
	private String csvPath;
	
	/**
	 * 
	 * @return
	 */
    public ResponseEntity<String> generateCSV(User user, UserSelection userSelection){
		File file = new File(csvPath);
		int score = 0;
		
        if (!file.exists()) {
        	System.out.println("El archivo no existe");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error al crear el archivo CSV", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        int userSize = 3;
        int size = userSelection.getAnswers().size();
        int indexRecord = userSize;
        
        String[] record = new String[size + userSize + 1];
        
        // Add user information to the record
        record[0] = user.getGender();
        record[1] = user.getAge();
        record[2] = user.getRol();
        
        // Add the answers values selected to the record
        for (Integer clave : userSelection.getAnswersValues().keySet()) {
        	record[indexRecord] = userSelection.getAnswersValues().get(clave).toString();
        	score += userSelection.getAnswersValues().get(clave);
        	indexRecord += 1;
        }
		
        // Add the score
        record[indexRecord] = String.valueOf(score);
        
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvPath, true))) {
        	
            csvWriter.writeNext(record);

            return new ResponseEntity<>("CSV creado exitosamente en: " + csvPath, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al crear el CSV", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	/**
	 * 
	 * @return
	 */
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
