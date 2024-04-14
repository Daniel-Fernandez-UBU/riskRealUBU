package tfg.daniel.riskreal.riskrealApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tfg.daniel.riskreal.riskrealApp.model.EmailRequest;
import tfg.daniel.riskreal.riskrealApp.services.IEmailService;

@RestController
@RequestMapping
public class EmailController {
    
    @Autowired
    private IEmailService emailService;

    @PostMapping("/send-email")
    private ResponseEntity<String> sendEmail(@RequestBody EmailRequest email) {
        try {
            emailService.sendMail(email);
            return ResponseEntity.ok("Correo electrónico enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo electrónico.");
        }
    }
    
}
