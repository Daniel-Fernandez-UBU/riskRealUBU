package tfg.daniel.riskreal.riskrealApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	@GetMapping("/error")
	public String mostrarError() {
		return "error"; 
	}
}