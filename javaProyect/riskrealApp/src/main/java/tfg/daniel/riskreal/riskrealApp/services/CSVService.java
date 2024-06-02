package tfg.daniel.riskreal.riskrealApp.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
import tfg.daniel.riskreal.riskrealApp.model.User;
import tfg.daniel.riskreal.riskrealApp.model.UserSelection;

/**
 * Class CSVService.
 * 
 * This class have the methods related with the csv files, the creation and the download.
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Service
public class CSVService {

	/** Injection CustomConfig class. */
	@Autowired
	private CustomConfig customConfig;

	/** Class attribute csvPath. */
	private String csvPath = customConfig.getCsvScorePath();
	
	/** Class constant reportSize */
	private final int reportSize = 3;
	
	/**
	 * Method generateCSV.
	 * 
	 * This method save the score information in a csv, and create it if the file not exists.
	 * 
	 * @return OK or ERROR - if there is any problem with the csv file.
	 */
    public ResponseEntity<String> generateCSV(User user, UserSelection userSelection){
		File file = new File(csvPath);
		int score = 0;
		
        if (!file.exists()) {
        	System.out.println("File not exists.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error creating csv.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        int size = userSelection.getAnswers().size();
        int indexRecord = reportSize;
        
        String[] record = new String[size + reportSize + 1];
        
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

            return new ResponseEntity<>("CSV successfully created in: " + csvPath, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while creating csv.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	/**
	 * Method downloadCSV.
	 * 
	 * Method that download the csv score file from the app to the user file system.
	 * 
	 * @return OK or NotFound - if the file exist or not.
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
