package backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.model.Administrador;
import backend.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AdministradorService {

    private final PasswordEncoder passwordEncoder;
    private final AdministradorRepository administradorRepository;

    public Administrador crearAdministrador(Administrador administrador) {

        if(administradorRepository.existsByUsuario(administrador.getUsuario())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        administrador.setEstado(true);

        administrador.setPassword(
            passwordEncoder.encode(administrador.getPassword())
        );
        return administradorRepository.save(administrador);
    }
}