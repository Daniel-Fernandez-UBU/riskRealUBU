package tfg.daniel.riskreal.riskrealApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.core.io.Resource;

import tfg.daniel.riskreal.riskrealApp.services.CSVService;



/**
 * Class CSV Controller.
 * 
 * Controller for download csv file with the scores of the questionnaires
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Controller
@PropertySource("classpath:custom.properties")
public class CSVController {
	
    @Autowired
    private CSVService csvService; 
	
	@Value("${csv.score.path}")
	private String csvPath;
	
	/**
	 * Method downloadCSV().
	 * 
	 * @return CSV with the scores.
	 */
    @GetMapping("/csv/download")
    public ResponseEntity<Resource> downloadCSV() {
        return csvService.downloadCSV();
    }

}
