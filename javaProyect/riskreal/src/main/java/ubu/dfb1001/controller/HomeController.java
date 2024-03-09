package ubu.dfb1001.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
	
	@GetMapping("/") //Página inicial
	public String inicio() {
		return "RiskReal";
	}
}
