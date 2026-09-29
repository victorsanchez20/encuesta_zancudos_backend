package backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.enums.EstadoPozo;
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

        validar(encuesta);

        encuesta.setPersona(resolverPersona(encuesta.getPersona()));
        encuesta.setFechaRegistro(Instant.now());

        return encuestaRepository.save(encuesta);
    }

    /**
     * El formulario del frontend se puede saltarse, así que las reglas se
     * comprueban en el servidor. Cada fallo es un IllegalArgumentException y el
     * ApiExceptionHandler lo traduce a 400 en lugar de un 500 de la base de datos.
     */
    private void validar(Encuesta encuesta) {

        if (encuesta.getEstadoPozo() == null) {
            throw new IllegalArgumentException("El estado del pozo es obligatorio");
        }

        if (encuesta.getPersona() == null) {
            throw new IllegalArgumentException("La encuesta debe incluir los datos de la persona");
        }

        Persona persona = encuesta.getPersona();

        if (persona.getDocIdentidad() == null || persona.getDocIdentidad() <= 0) {
            throw new IllegalArgumentException("El documento de identidad es obligatorio");
        }

        if (persona.getDireccion() == null || persona.getDireccion().isBlank()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }

        if (persona.getAsociacion() == null || persona.getAsociacion().isBlank()) {
            throw new IllegalArgumentException("La asociación es obligatoria");
        }

        if (encuesta.getEstadoPozo() == EstadoPozo.CON_PECES) {
            if (encuesta.getCantidadTiempo() == null || encuesta.getCantidadTiempo() <= 0) {
                throw new IllegalArgumentException("Indica cuántos meses o años lleva con peces");
            }
            if (encuesta.getUnidadTiempo() == null) {
                throw new IllegalArgumentException("Indica la unidad de tiempo (día, mes o año)");
            }
        } else {
            // Solo tiene sentido el tiempo cuando ya hay peces instalados.
            encuesta.setCantidadTiempo(null);
            encuesta.setUnidadTiempo(null);
        }
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

    @Transactional(readOnly = true)
    public List<Encuesta> obtenerTodasEncuestas() {
        return encuestaRepository.findTodasConPersona();
    }

    @Transactional(readOnly = true)
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
