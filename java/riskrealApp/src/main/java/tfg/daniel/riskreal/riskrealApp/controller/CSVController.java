package tfg.daniel.riskreal.riskrealApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.PostConstruct;

import org.springframework.core.io.Resource;

import tfg.daniel.riskreal.riskrealApp.config.CustomConfig;
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
	
    /** The csv service. */
    @Autowired
    private CSVService csvService; 
    
	/** The custom config. */
	@Autowired
	private CustomConfig customConfig;
	
	/** The csv path. */
	@Value("${csv.score.path}")
	private String csvPath;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
	    this.csvPath = customConfig.getCsvScorePath();
	}
	
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
