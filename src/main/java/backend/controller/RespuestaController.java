package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.model.Encuesta;
import backend.service.EncuestaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/encuestas")
@RequiredArgsConstructor 
public class RespuestaController {

    private final EncuestaService encuestaService;

    @PostMapping()
    public ResponseEntity<Encuesta> responderEncuesta(
        @RequestBody Encuesta encuesta) { 

        Encuesta nuevaEncuesta = encuestaService.responderEncuesta((encuesta));

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevaEncuesta);
    }
}
