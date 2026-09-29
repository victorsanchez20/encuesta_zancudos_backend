package backend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.model.Encuesta;

public interface EncuestaRepository extends JpaRepository<Encuesta, Long>{

    @Query("SELECT COUNT(*) FROM Encuesta WHERE estadoPozo = 'CON_PECES'")
    Long total_CON_PECES();

    @Query("SELECT COUNT(*) FROM Encuesta WHERE estadoPozo = 'QUIERO_PECES'")
    Long total_QUIERO_PECES();

    @Query("SELECT COUNT(*) FROM Encuesta WHERE estadoPozo = 'QUIERO_LARVICIDAS'")
    Long total_QUIERO_LARVICIDAS();

    @Query("SELECT COUNT(*) FROM Encuesta WHERE estadoPozo = 'SIN_POZO'")
    Long total_SIN_POZO();

    @Query("SELECT e FROM Encuesta e ORDER BY e.fechaRegistro DESC")
    List<Encuesta> ultimasRespuestas(Pageable pageable);
}
