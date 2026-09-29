package backend.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;

import backend.enums.EstadoPozo;
import backend.enums.UnidadTiempo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado-pozo", nullable = false)
    private EstadoPozo estadoPozo;

    @Column(name = "cantidad-tiempo")
    private Long cantidadTiempo;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad-tiempo")
    private UnidadTiempo unidadTiempo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Instant fechaRegistro;
}