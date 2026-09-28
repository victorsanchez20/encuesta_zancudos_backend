package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Administrador;
import java.util.Optional;



public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    boolean existsByUsuario(String usuario);

    Optional<Administrador> findByUsuario(String usuario);
}
