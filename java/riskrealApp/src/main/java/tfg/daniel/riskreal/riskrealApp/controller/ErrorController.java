package tfg.daniel.riskreal.riskrealApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The Class ErrorController.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Controller
public class ErrorController {
	
	/**
	 * Method showError.
	 *
	 * @return the string
	 */
	@GetMapping("/error")
	public String showError() {
		return "error"; 
	}
}