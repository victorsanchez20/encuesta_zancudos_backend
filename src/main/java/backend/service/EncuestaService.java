package backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import backend.model.Encuesta;
import backend.repository.EncuestaRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor  
public class EncuestaService {

    private final EncuestaRepository encuestaRepository;


    public Encuesta responderEncuesta(Encuesta encuesta) {
        return encuestaRepository.save(encuesta);
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

    public Long totalSinPeces() {
        return encuestaRepository.total_SIN_PECES();
    }

    public Long totalQuieroPeces() {
        return encuestaRepository.total_QUIERO_PECES();
    }

    public Long totalQuieroLarvicidas() {
        return encuestaRepository.total_QUIERO_LARVICIDAS();
    }
}
