package com.banquito.cbs.gestiontarjetas.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class InicioController {

    @GetMapping("/")
    public ResponseEntity<String> inicio() {
        return ResponseEntity.ok("Hola Mundo");
    }
}
