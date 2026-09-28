package backend.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;

import backend.enums.EstadoPozo;
import backend.enums.UnidadTiempo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@AllArgsConstructor 
@NoArgsConstructor 
@Getter @Setter 
public class Encuesta {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado-pozo", nullable = false)
    private EstadoPozo estadoPozo;

    @Column(name = "cantidad-tiempo")
    private long cantidadTiempo;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad-tiempo")
    private UnidadTiempo unidadTiempo;

    @OneToOne 
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @CreatedDate 
    @Column(name = "fecha_registro", nullable = false)
    private Instant fechaRegistro;
}