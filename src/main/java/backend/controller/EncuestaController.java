package backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.model.Encuesta;
import backend.service.EncuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;



@RestController 
@RequestMapping("/api/resultados")
@RequiredArgsConstructor 
public class EncuestaController {

    private final EncuestaService encuestaService;

    
    @GetMapping("/totalResultados")
    public ResponseEntity<List<Encuesta>> listarEncuestados() {
        return ResponseEntity.ok(encuestaService.obtenerTodasEncuestas());
    }

    @GetMapping("/ultimosResultados")
    public ResponseEntity<List<Encuesta>> ultimosResultados() {
        return ResponseEntity.ok(encuestaService.ultimasRespuestas());
    }

    @GetMapping("/total_sin_pozo")
    public ResponseEntity<Long> totalSinPozos() {
        return ResponseEntity.ok(encuestaService.totalSinPozo());
    } 
    
    @GetMapping("/total_con_peces")
    public ResponseEntity<Long> totalConPeces() {
        return ResponseEntity.ok(encuestaService.totalConPeces());
    }

    @GetMapping("/total_quiero_peces")
    public ResponseEntity<Long> totalQuieroPeces() {
        return ResponseEntity.ok(encuestaService.totalQuieroPeces());
    }

    @GetMapping("/total_quiero_larvicidas")
    public ResponseEntity<Long> totalQuieroLarvicidas() {
        return ResponseEntity.ok(encuestaService.totalQuieroLarvicidas());
    }
    
}