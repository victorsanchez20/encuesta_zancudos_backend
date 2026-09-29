package backend.controller;

import backend.dto.AdministradorResponse;
import backend.model.Administrador;
import backend.service.AdministradorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController 
@RequestMapping("/api/administradores")
@RequiredArgsConstructor 
public class AdministradorController {

    private final AdministradorService administradorService;


    @PostMapping
    public ResponseEntity<AdministradorResponse> crearAdministrador(@RequestBody Administrador administrador) {    
        
        Administrador nuevoAdministrador = administradorService.crearAdministrador(administrador);

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(AdministradorResponse.from(nuevoAdministrador));
    }
}
