package backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@AllArgsConstructor 
@NoArgsConstructor  
@Getter @Setter 
public class Persona {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "docIdentidad", nullable = false, unique = true)
    private Long docIdentidad;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "asociacion", nullable = false)
    private String asociacion;
}   