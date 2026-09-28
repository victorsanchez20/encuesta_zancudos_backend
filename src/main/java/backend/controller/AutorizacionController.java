package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.LoginRequest;
import backend.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/auth")
@RequiredArgsConstructor 
public class AutorizacionController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        
        boolean authenticated = authService.login(request);

        if(!authenticated) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Usuario o contraseña incorrectos");
        }
        return ResponseEntity.ok("Login existoso");
    }
    
}
