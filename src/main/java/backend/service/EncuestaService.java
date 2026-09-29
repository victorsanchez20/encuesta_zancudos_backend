package backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.model.Encuesta;
import backend.model.Persona;
import backend.repository.EncuestaRepository;
import backend.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncuestaService {

    private final EncuestaRepository encuestaRepository;
    private final PersonaRepository personaRepository;


    @Transactional
    public Encuesta responderEncuesta(Encuesta encuesta) {

        encuesta.setPersona(resolverPersona(encuesta.getPersona()));
        encuesta.setFechaRegistro(Instant.now());

        return encuestaRepository.save(encuesta);
    }

    private Persona resolverPersona(Persona recibida) {

        if (recibida == null) {
            throw new IllegalArgumentException("La encuesta debe incluir los datos de la persona");
        }

        Persona persona = personaRepository
            .findByDocIdentidad(recibida.getDocIdentidad())
            .orElseGet(() -> {
                Persona nueva = new Persona();
                nueva.setDocIdentidad(recibida.getDocIdentidad());
                return nueva;
            });

        persona.setDireccion(recibida.getDireccion());
        persona.setAsociacion(recibida.getAsociacion());

        return persona;
    }

    public List<Encuesta> obtenerTodasEncuestas() {
        return encuestaRepository.findAll();
    }

    public List<Encuesta> ultimasRespuestas() {
        Pageable pageable = PageRequest.of(0, 7);
        return encuestaRepository.ultimasRespuestas(pageable);
    }

    public Long totalConPeces() {
        return encuestaRepository.total_CON_PECES();
    }

    public Long totalSinPozo() {
        return encuestaRepository.total_SIN_POZO();
    }

    public Long totalQuieroPeces() {
        return encuestaRepository.total_QUIERO_PECES();
    }

    public Long totalQuieroLarvicidas() {
        return encuestaRepository.total_QUIERO_LARVICIDAS();
    }
}
